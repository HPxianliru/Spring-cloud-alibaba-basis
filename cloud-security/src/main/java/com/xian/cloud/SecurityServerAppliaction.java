package com.xian.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/20 14:32
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class SecurityServerAppliaction {


    public static void main(String[] args) {
        SpringApplication.run(SecurityServerAppliaction.class, args);
    }

}
