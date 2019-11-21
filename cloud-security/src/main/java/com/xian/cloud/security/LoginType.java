package com.xian.cloud.security;


import lombok.Getter;

/**
 * @Classname PreAuthenticationSuccessHandler
 * @Description 登录类型 现在有用户名 短信 社交
 * @Author xianliru@163.com
 * @Date 2019-11-08 13:50
 * @Version 1.0
 */
@Getter
public enum LoginType {
    normal, sms, social;
}
