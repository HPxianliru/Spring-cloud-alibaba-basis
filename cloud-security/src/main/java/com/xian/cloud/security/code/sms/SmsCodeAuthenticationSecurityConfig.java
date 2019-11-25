package com.xian.cloud.security.code.sms;


import com.xian.cloud.security.UserDetailsServiceImpl;
import com.xian.cloud.security.handle.CloudAuthenticationFailureHandler;
import com.xian.cloud.security.handle.CloudAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @Classname SmsCodeAuthenticationSecurityConfig
 * @Description TODO
 * @Author xianliru@163.com
 * @Date 2019-11-08 12:08
 * @Version 1.0
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CloudAuthenticationFailureHandler preAuthenticationFailureHandler;

    @Autowired
    private CloudAuthenticationSuccessHandler preAuthenticationSuccessHandler;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        //自定义SmsCodeAuthenticationFilter过滤器
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(preAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(preAuthenticationFailureHandler);
        //设置自定义SmsCodeAuthenticationProvider的认证器userDetailsService
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailService(userDetailsService);
        //在UsernamePasswordAuthenticationFilter过滤前执行
        http.authenticationProvider(smsCodeAuthenticationProvider).addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
