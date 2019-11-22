package com.xian.cloud.config;

import com.netflix.loadbalancer.IRule;
import com.xian.cloud.filter.GatewayLoadBalancerClientFilter;
import com.xian.common.rule.GrayscaleLoadBalancerRule;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/22 19:12
 */
@Configuration
public class RibbonCongifuration {



    @Bean
    LoadBalancerClientFilter userLoadBalancerClientFilter(LoadBalancerClient client, LoadBalancerProperties properties){
        return new GatewayLoadBalancerClientFilter( client, properties);
    }

    @Bean
    IRule grayscaleLoadBalancerRule(){
        return new GrayscaleLoadBalancerRule();
    }
}
