package com.xian.cloud.security.handle;


import com.xian.cloud.security.exception.ValidateCodeException;
import com.xian.cloud.security.util.SecurityUtil;
import com.xian.common.model.RestResultBuilder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname FebsAuthenticationFailureHandler
 * @Description 登录失败处理器
 * @Author xianliru@163.com
 * @Date 2019-11-07 23:45
 * @Version 1.0
 */
@Component
public class PreAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String message;

        if (exception instanceof ValidateCodeException) {
            message = exception.getMessage();
        } else {
            message = "认证失败，请联系网站管理员！";
        }
        response.setContentType("application/json;charset=utf-8");
        SecurityUtil.writeJavaScript(RestResultBuilder.builder().failure().message(message).build(), response);
    }
}


