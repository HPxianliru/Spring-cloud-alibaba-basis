package com.xian.dianping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/21 16:28
 */

@EnableDiscoveryClient
@SpringBootApplication
public class DingpingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingpingApplication.class, args);
    }

}
