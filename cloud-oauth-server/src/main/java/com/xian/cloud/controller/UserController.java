package com.xian.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {


    @RequestMapping("/user")
    public Principal user(Principal user) {

        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("UserController.user:{}",obj);

        return user;
    }
}
