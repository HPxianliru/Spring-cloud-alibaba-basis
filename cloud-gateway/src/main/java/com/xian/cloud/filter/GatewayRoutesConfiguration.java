package com.xian.cloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/08 09:45
 */
@Configuration
@Slf4j
public class GatewayRoutesConfiguration {

    /**
     * java 配置 server 服务路由
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        log.info("ServerGatewayFilter filtet........");
        return builder.routes()
                .route(r ->
                        r.path("/server/**")
                                .filters(
                                        f -> f.stripPrefix(1)
                                                .filters(new ServerGatewayFilter())
                                )
                                .uri("lb://cloud-discovery-server")
                )
                .build();
    }
}
