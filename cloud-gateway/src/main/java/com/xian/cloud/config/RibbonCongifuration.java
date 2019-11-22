package com.xian.cloud.config;

import com.netflix.loadbalancer.IRule;
import com.xian.common.rule.GrayscaleLoadBalancerRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
    @Primary
    IRule iRule(){
        IRule rule =  new GrayscaleLoadBalancerRule();

        return rule;
    }
}
