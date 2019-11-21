package com.xian.cloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.cloud.dto.UserDTO;
import com.xian.cloud.entity.UserEntity;
import com.xian.cloud.entity.UserRoleEntity;
import com.xian.cloud.dao.UserMapper;
import com.xian.cloud.service.*;
import com.xian.cloud.utils.PreUtil;
import com.xian.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private DeptService deptService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private JobService jobService;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public IPage<UserEntity> getUsersWithRolePage(Page page, UserDTO userDTO) {

        if (ObjectUtil.isNotNull(userDTO) && userDTO.getDeptId() != 0) {
            userDTO.setDeptList(deptService.selectDeptIds(userDTO.getDeptId()));
        }
        return baseMapper.getUserVosPage(page, userDTO);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertUser(UserDTO userDto) {
        UserEntity sysUser = new UserEntity();
        BeanUtils.copyProperties(userDto, sysUser);
        // 默认密码 123456
        sysUser.setPassword(PreUtil.encode("123456"));
        baseMapper.insertUser(sysUser);
        List<UserRoleEntity> userRoles = userDto.getRoleList().stream().map(item -> {
            UserRoleEntity sysUserRole = new UserRoleEntity();
            sysUserRole.setRoleId(item);
            sysUserRole.setUserId(sysUser.getUserId());
            return sysUserRole;
        }).collect(Collectors.toList());

        return userRoleService.saveBatch(userRoles);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUser(UserDTO userDto) {
        UserEntity sysUser = new UserEntity();
        BeanUtils.copyProperties(userDto, sysUser);
        baseMapper.updateById(sysUser);
        userRoleService.remove(Wrappers.<UserRoleEntity>lambdaQuery().eq(UserRoleEntity::getUserId, sysUser.getUserId()));
        List<UserRoleEntity> userRoles = userDto.getRoleList().stream().map(item -> {
            UserRoleEntity sysUserRole = new UserRoleEntity();
            sysUserRole.setRoleId(item);
            sysUserRole.setUserId(sysUser.getUserId());
            return sysUserRole;
        }).collect(Collectors.toList());

        return userRoleService.saveBatch(userRoles);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeUser(Integer userId) {
        baseMapper.deleteById(userId);
        return userRoleService.remove(Wrappers.<UserRoleEntity>lambdaQuery().eq(UserRoleEntity::getUserId, userId));
    }

    @Override
    public boolean restPass(Integer userId) {
        return baseMapper.updateById(new UserEntity().setPassword("123456").setUserId(userId)) > 0;
    }

    @Override
    public UserEntity findByUserInfoName(String username) {
        UserEntity sysUser = baseMapper.selectOne(Wrappers.<UserEntity>lambdaQuery()
                .select(UserEntity::getUserId, UserEntity::getUsername, UserEntity::getPhone, UserEntity::getEmail, UserEntity::getPassword, UserEntity::getDeptId, UserEntity::getJobId, UserEntity::getAvatar)
                .eq(UserEntity::getUsername, username));
        // 获取部门
        sysUser.setDeptName(deptService.selectDeptNameByDeptId(sysUser.getDeptId()));
        // 获取岗位
        sysUser.setJobName(jobService.selectJobNameByJobId(sysUser.getJobId()));
        return sysUser;
    }

    @Override
    public Set<String> findPermsByUserId(Integer userId) {
        return menuService.findPermsByUserId(userId).stream().filter(StringUtils::isNotEmpty).collect(Collectors.toSet());
    }

    @Override
    public Set<String> findRoleIdByUserId(Integer userId) {
        return userRoleService
                .selectUserRoleListByUserId(userId)
                .stream()
                .map(sysUserRole -> "ROLE_" + sysUserRole.getRoleId())
                .collect(Collectors.toSet());
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean register(UserDTO userDTO) {
        // 查询用户名是否存在
        UserEntity byUserInfoName = findSecurityUser(userDTO.getUsername());
        if (ObjectUtil.isNotNull(byUserInfoName)) {
            throw new BusinessException("账户名已被注册");
        }
        UserEntity securityUser = findSecurityUser(userDTO.getPhone());
        if (ObjectUtil.isNotNull(securityUser)) {
            throw new BusinessException("手机号已被注册");
        }
        userDTO.setDeptId(6);
        userDTO.setJobId(4);
        userDTO.setLockFlag("0");
        UserEntity sysUser = new UserEntity();
        // 对象拷贝
        BeanUtil.copyProperties(userDTO, sysUser);
        // 加密后的密码
        sysUser.setPassword(PreUtil.encode(userDTO.getPassword()));
        baseMapper.insertUser(sysUser);
        UserRoleEntity sysUserRole = new UserRoleEntity();
        sysUserRole.setRoleId(14);
        sysUserRole.setUserId(sysUser.getUserId());
        return userRoleService.save(sysUserRole);
    }

    @Override
    public boolean updateUserInfo(UserEntity sysUser) {
        return baseMapper.updateById(sysUser) > 0;
    }

    @Override
    public UserEntity findSecurityUserByUser(UserEntity sysUser) {
        LambdaQueryWrapper<UserEntity> select = Wrappers.<UserEntity>lambdaQuery()
                .select(UserEntity::getUserId, UserEntity::getUsername, UserEntity::getPassword);
        if (StrUtil.isNotEmpty(sysUser.getUsername())) {
            select.eq(UserEntity::getUsername, sysUser.getUsername());
        } else if (StrUtil.isNotEmpty(sysUser.getPhone())) {
            select.eq(UserEntity::getPhone, sysUser.getPhone());
        } else if (ObjectUtil.isNotNull(sysUser.getUserId()) && sysUser.getUserId() != 0) {
            select.eq(UserEntity::getUserId, sysUser.getUserId());
        }


        return baseMapper.selectOne(select);
    }


    private UserEntity findSecurityUser(String userIdOrUserNameOrPhone) {
        LambdaQueryWrapper<UserEntity> select = Wrappers.<UserEntity>lambdaQuery()
                .select(UserEntity::getUserId, UserEntity::getUsername, UserEntity::getPassword)
                .eq(UserEntity::getUsername, userIdOrUserNameOrPhone)
                .or()
                .eq(UserEntity::getPhone, userIdOrUserNameOrPhone)
                .or()
                .eq(UserEntity::getUserId, userIdOrUserNameOrPhone);
        return baseMapper.selectOne(select);
    }
}
