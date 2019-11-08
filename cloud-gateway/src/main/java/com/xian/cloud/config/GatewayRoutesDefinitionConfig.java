package com.xian.cloud.config;

import com.xian.cloud.repository.GatewayRoutesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <Description>
 *
 * @author xianliru@100tal.com
 * @version 1.0
 * @createDate 2019/11/08 17:12
 */
@Configuration
@Slf4j
public class GatewayRoutesDefinitionConfig {

    @Bean
    RouteDefinitionRepository routeDefinitionRepositor(){
        return new GatewayRoutesRepository();
    }
}
