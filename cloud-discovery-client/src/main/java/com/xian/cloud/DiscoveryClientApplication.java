package com.xian.cloud;

import com.netflix.loadbalancer.IRule;
import com.xian.common.rule.GrayscaleLoadBalancerRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @Author: xlr
 * @Date: Created in 3:03 PM 2019/10/27
 */
@EnableDiscoveryClient
@SpringBootApplication
@ServletComponentScan
@EnableFeignClients
public class DiscoveryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryClientApplication.class, args);
    }


    @Bean
    IRule rule(){
        return new GrayscaleLoadBalancerRule();
    }
}
