package com.xian.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xlr
 * @Date: Created in 2:57 PM 2019/10/27
 */
@RestController
@RequestMapping("server")
@Slf4j
public class DiscoverCotroller {


    /**
     * 对外提供的服务 HTTP接口
     * @param name
     * @return
     */
    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        log.info("invoked name = " + name);
        return "hello " + name;
    }


}