package com.xian.cloud.handle;

import com.xian.cloud.util.SecurityUtil;
import com.xian.common.model.RestResultBuilder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author MrBird
 */
@Component
public class CloudAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        SecurityUtil.writeJavaScript(
                RestResultBuilder
                .builder()
                .failure()
                .message("没有权限访问该资源")
                .build(),
                response);
    }
}
