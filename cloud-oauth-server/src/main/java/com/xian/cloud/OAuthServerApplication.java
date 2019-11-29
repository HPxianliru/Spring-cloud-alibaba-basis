package com.xian.cloud;

import com.xian.cloud.fegin.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: xlr
 * @Date: Created in 8:56 PM 2019/11/28
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackageClasses = {UserService.class})
public class OAuthServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(OAuthServerApplication.class, args);
    }
}
