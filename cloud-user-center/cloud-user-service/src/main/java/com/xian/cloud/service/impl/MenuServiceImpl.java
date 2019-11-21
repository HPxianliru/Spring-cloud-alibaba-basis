package com.xian.cloud.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.cloud.dto.MenuDTO;
import com.xian.cloud.entity.MenuEntity;
import com.xian.cloud.dao.MenuMapper;
import com.xian.cloud.service.MenuService;
import com.xian.cloud.service.RoleMenuService;
import com.xian.cloud.utils.PreUtil;
import com.xian.common.constant.MenuConstant;
import com.xian.common.exception.ValidateException;
import com.xian.common.model.RestResult;
import com.xian.common.model.RestResultBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author xlr
 * @since 2019-04-21
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public boolean save(MenuEntity sysMenu) {
        // 菜单校验
        verifyForm(sysMenu);
        return super.save(sysMenu);
    }

    @Override
    public boolean updateMenuById(MenuDTO entity) {
        MenuEntity sysMenu = new MenuEntity();
        BeanUtils.copyProperties(entity, sysMenu);
        // 菜单校验
        verifyForm(sysMenu);
        return this.updateById(sysMenu);
    }

    @Override
    public List<MenuEntity> selectMenuTree(Integer uid) {

        LambdaQueryWrapper<MenuEntity> sysMenuLambdaQueryWrapper = Wrappers.<MenuEntity>query().lambda();
        sysMenuLambdaQueryWrapper.select(MenuEntity::getMenuId, MenuEntity::getName, MenuEntity::getPerms, MenuEntity::getPath, MenuEntity::getParentId, MenuEntity::getComponent, MenuEntity::getIsFrame, MenuEntity::getIcon, MenuEntity::getSort, MenuEntity::getType, MenuEntity::getDelFlag);
        // 所有人有权限看到 只是没有权限操作而已 暂定这样
        if (uid != 0) {
            List<Integer> menuIdList = roleMenuService.getMenuIdByUserId(uid);
            sysMenuLambdaQueryWrapper.in(MenuEntity::getMenuId, menuIdList);
        }
        List<MenuEntity> sysMenus = new ArrayList<>();
        List<MenuEntity> menus = baseMapper.selectList(sysMenuLambdaQueryWrapper);
        menus.forEach(menu -> {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setLevel(0);
                if (PreUtil.exists(sysMenus, menu)) {
                    sysMenus.add(menu);
                }
            }
        });
        sysMenus.sort((o1, o2) -> o1.getSort().compareTo(o2.getSort()));
        PreUtil.findChildren(sysMenus, menus, 0);
        return sysMenus;
    }

    @Override
    public MenuEntity getMenuById(Integer parentId) {
        return baseMapper.selectOne(Wrappers.<MenuEntity>lambdaQuery().select(MenuEntity::getType).eq(MenuEntity::getMenuId, parentId));
    }

    @Override
    public List<String> findPermsByUserId(Integer userId) {
        return baseMapper.findPermsByUserId(userId);
    }

    @Override
    public RestResult<Boolean> removeMenuById(Serializable id) {
        List<Integer> idList =
                this.list(Wrappers.<MenuEntity>query().lambda().eq(MenuEntity::getParentId, id)).stream().map(MenuEntity::getMenuId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(idList)) {
            return RestResultBuilder.builder().failure().message("菜单含有下级不能删除").build();
        }
        return RestResultBuilder.builder().success(this.removeById(id)).build();
    }

    /**
     * 验证菜单参数是否正确
     */
    private void verifyForm(MenuEntity menu) {
        //上级菜单类型
        int parentType = MenuConstant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            MenuEntity parentMenu = getMenuById(menu.getParentId());
            parentType = parentMenu.getType();
        }
        //目录、菜单
        if (menu.getType() == MenuConstant.MenuType.CATALOG.getValue() ||
                menu.getType() == MenuConstant.MenuType.MENU.getValue()) {
            if (parentType != MenuConstant.MenuType.CATALOG.getValue()) {
                throw new ValidateException("上级菜单只能为目录类型");
            }
            return;
        }
        //按钮
        if (menu.getType() == MenuConstant.MenuType.BUTTON.getValue()) {
            if (parentType != MenuConstant.MenuType.MENU.getValue()) {
                throw new ValidateException("上级菜单只能为菜单类型");
            }
        }
    }
}
