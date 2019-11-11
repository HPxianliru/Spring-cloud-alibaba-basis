package com.xian.cloud.common.config;

import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulErrorFilter;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPostFilter;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPreFilter;
import com.netflix.zuul.ZuulFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/10/31 15:22
 */
@Configuration
public class ZuulSentinelConfig {

    @Bean
    @Primary
    public SentinelZuulPreFilter sentinelZuulPreFilter() {
        return new SentinelZuulPreFilter(1000);
    }

    @Bean
    @Primary
    public SentinelZuulPostFilter sentinelZuulPostFilter() {
        return new SentinelZuulPostFilter(1000);
    }

    @Bean
    @Primary
    public SentinelZuulErrorFilter sentinelZuulErrorFilter() {
        return new SentinelZuulErrorFilter(-1);
    }


}
