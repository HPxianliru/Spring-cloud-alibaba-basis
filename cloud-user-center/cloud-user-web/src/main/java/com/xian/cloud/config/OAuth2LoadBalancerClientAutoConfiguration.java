package com.xian.cloud.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.cloud.client.loadbalancer.RetryLoadBalancerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/30 12:57
 */
@Configuration
@ConditionalOnClass(OAuth2RestTemplate.class)
@ConditionalOnProperty(value = "security.oauth2.resource.loadBalanced", matchIfMissing = false)
@AutoConfigureAfter(OAuth2AutoConfiguration.class)
public class OAuth2LoadBalancerClientAutoConfiguration {


    @Configuration
    @ConditionalOnBean(LoadBalancerInterceptor.class)
    protected static class UserInfoLoadBalancerConfig {
        @Bean
        public UserInfoRestTemplateCustomizer loadBalancedUserInfoRestTemplateCustomizer(
                final LoadBalancerInterceptor loadBalancerInterceptor) {
            return new UserInfoRestTemplateCustomizer() {
                @Override
                public void customize(OAuth2RestTemplate restTemplate) {
                    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(
                            restTemplate.getInterceptors());
                    interceptors.add(loadBalancerInterceptor);
                    restTemplate.setInterceptors(interceptors);
                }
            };
        }
    }

    @Configuration
    @ConditionalOnBean(RetryLoadBalancerInterceptor.class)
    protected static class UserInfoRetryLoadBalancerConfig {
        @Bean
        public UserInfoRestTemplateCustomizer retryLoadBalancedUserInfoRestTemplateCustomizer(
                final RetryLoadBalancerInterceptor loadBalancerInterceptor) {
            return new UserInfoRestTemplateCustomizer() {
                @Override
                public void customize(OAuth2RestTemplate restTemplate) {
                    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(
                            restTemplate.getInterceptors());
                    interceptors.add(loadBalancerInterceptor);
                    restTemplate.setInterceptors(interceptors);
                }
            };
        }
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
