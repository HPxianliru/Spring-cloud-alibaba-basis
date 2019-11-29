package com.xian.cloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/30 00:44
 */
@RestController
@RequestMapping("oauth")
public class UserController {


    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
