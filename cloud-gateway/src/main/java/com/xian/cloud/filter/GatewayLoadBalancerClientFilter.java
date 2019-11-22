package com.xian.cloud.filter;

import com.xian.common.rule.GrayscaleConstant;
import com.xian.common.rule.GrayscaleEntity;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 *  重写choose 方法主要为了封装 GrayscaleEntity 对象 进行传值给IRule 实现类的key值
 * @Author: xlr
 * @Date: Created in 9:56 PM 2019/11/22
 */
public class GatewayLoadBalancerClientFilter extends LoadBalancerClientFilter {

    public GatewayLoadBalancerClientFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {

        if (this.loadBalancer instanceof RibbonLoadBalancerClient) {
            RibbonLoadBalancerClient client = (RibbonLoadBalancerClient) this.loadBalancer;
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String version = headers.getFirst( GrayscaleConstant.GRAYSCALE_VERSION );
            String serviceId = ((URI) exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR)).getHost();
            GrayscaleEntity build = GrayscaleEntity.builder().version( version ).serverName( serviceId ).build();
            //这里使用服务ID 和 version 做为选择服务实例的key
            //TODO 这里也可以根据实际业务情况做自己的对象封装
            return client.choose(serviceId,build);
        }
        return super.choose(exchange);
    }

}
