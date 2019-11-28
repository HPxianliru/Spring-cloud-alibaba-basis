package com.xian.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xlr
 * @Date: Created in 10:24 PM 2019/11/28
 */

@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "password")
    public String password(@RequestParam("password") String password){

        return passwordEncoder.encode( password );
    }

    @GetMapping("vaild")
    public boolean vaildate(@RequestParam("password") String password){
        return passwordEncoder.matches( "$2a$10$.WPow3X6iSC24vR9KDwXHew66tDgokxp73Oydh.KDqPsEI4zny6Ti" ,password);
    }
}
