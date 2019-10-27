package com.xian.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: xlr
 * @Date: Created in 2:44 PM 2019/10/27
 */
@EnableDiscoveryClient
@SpringBootApplication
public class DiscoveryServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
