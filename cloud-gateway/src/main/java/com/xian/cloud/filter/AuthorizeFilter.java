package com.xian.cloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/04 18:06
 */
@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter, Ordered {


    private static final String AUTHORIZE_TOKEN = "Authorization";
    private static final String AUTHORIZE_UID = "uid";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        ServerHttpRequest.Builder mutate = request.mutate();

        String token = headers.getFirst( AUTHORIZE_TOKEN );
        String uid = headers.getFirst( AUTHORIZE_UID );
        String method = request.getMethodValue();

        log.info( "AuthorizeFilter token 全局过滤器 token:{},uid:{}",token,uid );
        if (token == null) {
            token = request.getQueryParams().getFirst( AUTHORIZE_TOKEN );
        }
        if(StringUtils.isNotBlank(token)){

        }

        return chain.filter( exchange );
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
