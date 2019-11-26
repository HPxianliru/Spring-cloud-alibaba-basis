package com.xian.cloud.config;

import com.xian.cloud.handler.CloudAccessDeniedHandler;
import com.xian.cloud.handler.CloudAuthExceptionEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/26 14:26
 */

public class CloudResourceServerConfigure extends ResourceServerConfigurerAdapter {

    String[] anonUrls = {"/login/**", "/mobile/login/**", "/favicon.ico","/socialSignUp","/bind","/register/**","/actuator/**"};

    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public CloudAccessDeniedHandler accessDeniedHandler() {
        return new CloudAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public CloudAuthExceptionEntryPoint authenticationEntryPoint() {
        return new CloudAuthExceptionEntryPoint();
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(anonUrls).permitAll()
                .antMatchers("/**").authenticated()
                .and().httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler());
    }
}
