package com.xian.cloud.handle;


import com.xian.cloud.util.JwtUtil;
import com.xian.cloud.util.SecurityUtil;
import com.xian.common.enums.LoginType;
import com.xian.common.model.RestResultBuilder;
import com.xian.common.security.PreSecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname PreAuthenticationSuccessHandler
 * @Description 登录成功处理器
 * @Author xianliru@163.com
 * @Date 2019-11-08 13:50
 * @Version 1.0
 */
@Component
public class CloudAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof PreSecurityUser) {
            PreSecurityUser userDetail = (PreSecurityUser) authentication.getPrincipal();
            //存储认证信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //生成token
            String s = JwtUtil.generateToken(userDetail);
            // 是短信登录返回token
            if (LoginType.sms.equals(userDetail.getLoginType())) {
                SecurityUtil.writeJavaScript(RestResultBuilder.builder().success(s).build(), response);
            }
        }
    }
}

