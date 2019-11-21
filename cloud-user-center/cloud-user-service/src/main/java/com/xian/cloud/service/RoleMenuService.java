package com.xian.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.cloud.entity.RoleMenuEntity;

import java.util.List;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
public interface RoleMenuService extends IService<RoleMenuEntity> {

    List<Integer> getMenuIdByUserId(Integer userId);


}
