package com.xian.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/12/25 11:10
 */
@SpringBootApplication
@EnableFeignClients(basePackages={"com.xian.cloud.fegin"})
public class WechatApplication {


    public static void main(String[] args) {

        SpringApplication.run(WechatApplication.class,args);
    }
}
