package com.xian.cloud.controller;

import com.xian.cloud.core.UserService;
import com.xian.cloud.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @Author: xlr
 * @Date: Created in 2:57 PM 2019/10/27
 */
@RestController
@RequestMapping("server")
@Slf4j
@RefreshScope
public class DiscoverCotroller {


    @Value( "${nacos.yaml.age}" )
    private String age;

    @Autowired
    private UserService userService;

    /**
     * 对外提供的服务 HTTP接口
     * @param name
     * @return
     */
    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        log.info("invoked name = " + name+ " age = " + age);
        return "hello " + name + " age = " + age;
    }

    @PostMapping("/test")
    public String tom(HttpServletRequest request) throws Exception {
        InputStream in = request.getInputStream();
        String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
        return "hello this is method POST";
    }

    @PostMapping("/log/update")
    public boolean update(@RequestBody UserEntity entity){
        entity.setPassWord("server");
        return userService.updateById(entity);
    }

}
