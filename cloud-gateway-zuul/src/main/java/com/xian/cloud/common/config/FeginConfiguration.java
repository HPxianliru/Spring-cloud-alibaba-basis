package com.xian.cloud.common.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/16 13:20
 */
@Configuration
public class FeginConfiguration {

    /**
     *  开启 Sentinel RestTemplate 支持
     * @return
     */
    @Bean
    @LoadBalanced
    @SentinelRestTemplate
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
