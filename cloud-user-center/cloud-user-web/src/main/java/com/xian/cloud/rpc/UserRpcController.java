package com.xian.cloud.rpc;

import com.xian.cloud.api.UserServiceApi;
import com.xian.cloud.dto.UserDTO;
import com.xian.cloud.entity.UserEntity;
import com.xian.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/21 17:06
 */
@RestController
public class UserRpcController implements UserServiceApi {

    @Autowired
    private UserService userService;
    /**
     * 通过用户名查找用户个人信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public UserEntity findByUserInfoName(String username) {
        return userService.findByUserInfoName(username);
    }

    /**
     * 根据用户id查询权限
     *
     * @param userId
     * @return
     */
    @Override
    public Set<String> findPermsByUserId(Integer userId) {
        return userService.findPermsByUserId(userId);
    }

    /**
     * 通过用户id查询角色集合
     *
     * @param userId
     * @return
     */
    @Override
    public Set<String> findRoleIdByUserId(Integer userId) {
        return userService.findPermsByUserId(userId);
    }

    /**
     * 通过用户去查找用户(id/用户名/手机号)
     *
     * @param UserEntity
     * @return
     */
    @Override
    public UserEntity findSecurityUserByUser(@RequestBody UserEntity UserEntity) {
        return userService.findSecurityUserByUser(UserEntity);
    }

    @Override
    public boolean register(UserDTO userDTO) {
        return userService.register(userDTO);
    }
}
