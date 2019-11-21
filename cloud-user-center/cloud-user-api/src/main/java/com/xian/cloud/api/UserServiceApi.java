package com.xian.cloud.api;

import com.xian.cloud.dto.UserDTO;
import com.xian.cloud.entity.UserEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/21 15:02
 */
public interface UserServiceApi {

    /**
     * 通过用户名查找用户个人信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @RequestMapping(value = "find/user/detail/{username}",method = RequestMethod.GET)
    UserEntity findByUserInfoName(@PathVariable("username") String username);

    /**
     * 根据用户id查询权限
     * @param userId
     * @return
     */
    @RequestMapping(value = "find/perms/detail/{id}",method = RequestMethod.GET)
    Set<String> findPermsByUserId(@PathVariable("id") Integer userId);

    /**
     * 通过用户id查询角色集合
     * @param userId
     * @return
     */
    @RequestMapping(value = "find/role/detail/{id}",method = RequestMethod.GET)
    Set<String> findRoleIdByUserId(@PathVariable("id")Integer userId);

    /**
     * 通过用户去查找用户(id/用户名/手机号)
     * @param UserEntity
     * @return
     */
    @RequestMapping(value = "find/user",method = RequestMethod.POST)
    UserEntity findSecurityUserByUser(@RequestBody UserEntity UserEntity);


    @RequestMapping(value = "user/register",method = RequestMethod.POST)
    boolean register(@RequestBody UserDTO userDTO);

}
