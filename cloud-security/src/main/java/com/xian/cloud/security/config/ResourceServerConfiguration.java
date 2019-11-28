package com.xian.cloud.security.config;

import com.xian.cloud.security.UserDetailsServiceImpl;
import com.xian.cloud.security.code.img.ImageCodeFilter;
import com.xian.cloud.security.code.sms.SmsCodeAuthenticationSecurityConfig;
import com.xian.cloud.security.code.sms.SmsCodeFilter;
import com.xian.cloud.security.filter.JwtAuthenticationTokenFilter;
import com.xian.cloud.security.handle.AuthenticationEntryPointImpl;
import com.xian.cloud.security.handle.CloudAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: xlr
 * @Date: Created in 10:33 PM 2019/11/26
 */
@EnableResourceServer
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{

    private static final String DEMO_RESOURCE_ID = "order";

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    @Autowired
    private CloudAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private ImageCodeFilter imageCodeFilter;

    @Autowired
    private SmsCodeFilter smsCodeFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // 注入短信登录的相关配置
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler( accessDeniedHandler );
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {


        imageCodeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                // 短信登录配置
                .apply(smsCodeAuthenticationSecurityConfig).and()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 图标 要允许匿名访问
                .antMatchers("/login/**","/oauth/**", "/mobile/login/**", "/favicon.ico","/socialSignUp","/bind","/register/**","/actuator/**").anonymous()
                .antMatchers( HttpMethod.GET,"/*.html","/**/*.html","/**/*.css","/**/*.js")
                .permitAll()
                .antMatchers("/captcha.jpg").anonymous()
                .antMatchers("/sendCode/**")
                .permitAll()
                // 访问/user 需要拥有admin权限
                //  .antMatchers("/user").hasAuthority("ROLE_ADMIN")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        // 添加JWT filter 用户名登录
        httpSecurity
                // 添加图形证码校验过滤器
//                .addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 添加JWT验证过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 添加短信验证码过滤器
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class);
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
