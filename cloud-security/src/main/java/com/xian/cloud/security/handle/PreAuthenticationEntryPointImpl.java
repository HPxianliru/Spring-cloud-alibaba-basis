package com.xian.cloud.security.handle;

import cn.hutool.http.Status;
import com.xian.cloud.security.util.SecurityUtil;
import com.xian.common.model.RestResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回401
 * @author xlr
 */
@Slf4j
@Component
public class PreAuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.error("请求访问: " + request.getRequestURI() + " 接口， 经jwt认证失败，无法访问系统资源.");
        SecurityUtil.writeJavaScript(RestResultBuilder.builder().failure().code(Status.HTTP_UNAUTHORIZED).message("请求访问:" + request.getRequestURI() + "接口,经jwt 认证失败,无法访问系统资源").build(),response);
    }
}
