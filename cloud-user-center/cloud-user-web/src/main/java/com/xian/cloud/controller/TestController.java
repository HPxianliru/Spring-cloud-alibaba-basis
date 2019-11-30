package com.xian.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/30 12:48
 */
@RestController
@RequestMapping("test")
public class TestController {


    @GetMapping("hello")
    public String hello(){

        return "hello";
    }
}
