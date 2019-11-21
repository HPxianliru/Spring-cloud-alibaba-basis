package com.xian.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.cloud.dto.MenuDTO;
import com.xian.cloud.entity.MenuEntity;
import com.xian.common.model.RestResult;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author xlr
 * @since 2019-04-21
 */
public interface MenuService extends IService<MenuEntity> {

    /**
     * 更新菜单信息
     * @param entity
     * @return
     */
    boolean updateMenuById(MenuDTO entity);

    /**
     * 删除菜单信息
     * @param id
     * @return
     */
    RestResult removeMenuById(Serializable id);

    /**
     * 根据用户id查找菜单树
     * @return
     */
    List<MenuEntity> selectMenuTree(Integer uid);

    /**
     * @Author xlr
     * @Description 根据父id查询菜单
     * @Date 18:43 2019-11-12
     **/
    MenuEntity getMenuById(Integer parentId);

    /**
     * @Description 根据用户id查询权限
     **/
    List<String> findPermsByUserId(Integer userId);
}
