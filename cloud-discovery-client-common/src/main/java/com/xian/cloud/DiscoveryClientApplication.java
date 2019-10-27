package com.xian.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: xlr
 * @Date: Created in 3:03 PM 2019/10/27
 */
@EnableDiscoveryClient
@SpringBootApplication
public class DiscoveryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryClientApplication.class, args);
    }
}
