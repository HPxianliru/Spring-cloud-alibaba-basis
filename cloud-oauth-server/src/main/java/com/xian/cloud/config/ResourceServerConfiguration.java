package com.xian.cloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


/**
 * @Author: xlr
 * @Date: Created in 8:59 PM 2019/11/28
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                // Since we want the protected resources to be accessible in the UI as well we need
                // session creation to be allowed (it's disabled by default in 2.0.6)
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.IF_REQUIRED)
//                    .and()
//                    .requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                // 对于登录login 图标 要允许匿名访问
                .antMatchers("/index/**","/oauth/**", "/mobile/login/**", "/favicon.ico","/socialSignUp","/bind","/register/**","/actuator/**").anonymous()
                .antMatchers( HttpMethod.GET,"/*.html","/**/*.html","/**/*.css","/**/*.js")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/product/**").access("#oauth2.hasScope('select') and hasPermission('delete')")
                .antMatchers("/order/**").authenticated();//配置order访问控制，必须认证过后才可以访问
        // @formatter:on
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 设置UserDetailsService
                .userDetailsService(userDetailsService)
                // 使用BCrypt进行密码的hash
                .passwordEncoder(passwordEncoder);
    }
}