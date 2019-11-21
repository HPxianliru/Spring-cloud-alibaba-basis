package com.xian.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.cloud.entity.UserRoleEntity;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author xlr
 * @since 2019-04-21
 */
public interface UserRoleService extends IService<UserRoleEntity> {


    /**
     * 根据用户id查询用户角色关系
     * @param userId
     * @return
     */
    List<UserRoleEntity> selectUserRoleListByUserId(Integer userId);
}
