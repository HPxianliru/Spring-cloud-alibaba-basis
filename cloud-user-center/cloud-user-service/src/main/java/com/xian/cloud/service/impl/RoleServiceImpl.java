package com.xian.cloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.cloud.dto.RoleDTO;
import com.xian.cloud.entity.MenuEntity;
import com.xian.cloud.entity.RoleDeptEntity;
import com.xian.cloud.entity.RoleEntity;
import com.xian.cloud.entity.RoleMenuEntity;
import com.xian.cloud.dao.RoleMapper;
import com.xian.cloud.service.RoleDeptService;
import com.xian.cloud.service.RoleMenuService;
import com.xian.cloud.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private RoleDeptService roleDeptService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRoleMenu(RoleDTO roleDto) {
        RoleEntity sysRole = new RoleEntity();
        BeanUtils.copyProperties(roleDto, sysRole);
        // 根据数据权限范围查询部门ids
        List<Integer> ids = null;

        StringJoiner dsScope = new StringJoiner(",");
        ids.forEach(integer -> {
            dsScope.add(Integer.toString(integer));
        });
        sysRole.setDsScope(dsScope.toString());
        baseMapper.insertRole(sysRole);
        Integer roleId = sysRole.getRoleId();
        //维护角色菜单
        List<RoleMenuEntity> roleMenus = roleDto.getRoleMenus();
        if (CollectionUtil.isNotEmpty(roleMenus)) {
            List<RoleMenuEntity> rms = roleMenus.stream().map(sysRoleMenu -> {
                RoleMenuEntity roleMenu = new RoleMenuEntity();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(sysRoleMenu.getMenuId());
                return roleMenu;
            }).collect(Collectors.toList());
            roleMenuService.saveBatch(rms);
        }
        // 维护角色部门权限
        // 根据数据权限范围查询部门ids
        if (CollectionUtil.isNotEmpty(ids)) {
            List<RoleDeptEntity> roleDepts = ids.stream().map(integer -> {
                RoleDeptEntity sysRoleDept = new RoleDeptEntity();
                sysRoleDept.setDeptId(integer);
                sysRoleDept.setRoleId(roleId);
                return sysRoleDept;
            }).collect(Collectors.toList());

            roleDeptService.saveBatch(roleDepts);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRoleMenu(RoleDTO roleDto) {
        RoleEntity sysRole = new RoleEntity();
        BeanUtils.copyProperties(roleDto, sysRole);

        List<RoleMenuEntity> roleMenus = roleDto.getRoleMenus();
        roleMenuService.remove(Wrappers.<RoleMenuEntity>query().lambda().eq(RoleMenuEntity::getRoleId, sysRole.getRoleId()));
        roleDeptService.remove(Wrappers.<RoleDeptEntity>query().lambda().eq(RoleDeptEntity::getRoleId, sysRole.getRoleId()));

        if (CollectionUtil.isNotEmpty(roleMenus)) {
            roleMenuService.saveBatch(roleMenus);
        }
        // 根据数据权限范围查询部门ids
        List<Integer> ids = new ArrayList<>();

        StringJoiner dsScope = new StringJoiner(",");
        ids.forEach(integer -> {
            dsScope.add(Integer.toString(integer));
        });
        if (CollectionUtil.isNotEmpty(ids)) {
            List<RoleDeptEntity> roleDepts = ids.stream().map(integer -> {
                RoleDeptEntity sysRoleDept = new RoleDeptEntity();
                sysRoleDept.setDeptId(integer);
                sysRoleDept.setRoleId(roleDto.getRoleId());
                return sysRoleDept;
            }).collect(Collectors.toList());
            roleDeptService.saveBatch(roleDepts);
        }
        sysRole.setDsScope(dsScope.toString());
        baseMapper.updateById(sysRole);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        roleMenuService.remove(Wrappers.<RoleMenuEntity>query().lambda().eq(RoleMenuEntity::getRoleId, id));
        roleDeptService.remove(Wrappers.<RoleDeptEntity>query().lambda().eq(RoleDeptEntity::getRoleId, id));
        return super.removeById(id);
    }

    @Override
    public List<RoleEntity> selectRoleList(String roleName) {
        LambdaQueryWrapper<RoleEntity> sysRoleLambdaQueryWrapper = Wrappers.<RoleEntity>lambdaQuery();
        if (StrUtil.isNotEmpty(roleName)){
            sysRoleLambdaQueryWrapper.like(RoleEntity::getRoleName,roleName);
        }
        List<RoleEntity> sysRoles = baseMapper.selectList(sysRoleLambdaQueryWrapper);
        return sysRoles.stream().peek(sysRole ->
                sysRole.setRoleDepts(roleDeptService.getRoleDeptIds(sysRole.getRoleId()).stream().map(RoleDeptEntity::getDeptId).collect(Collectors.toList()))
        ).collect(Collectors.toList());
    }


    @Override
    public List<MenuEntity> findMenuListByRoleId(int roleId) {
        return baseMapper.findMenuListByRoleId(roleId);
    }

    @Override
    public List<RoleEntity> findRolesByUserId(Integer userId) {
        return baseMapper.listRolesByUserId(userId);
    }

}
