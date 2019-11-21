package com.xian.cloud.security.util;

import com.alibaba.fastjson.JSON;
import com.xian.common.security.PreSecurityUser;
import com.xian.common.exception.ValidateException;
import com.xian.common.model.RestResult;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Classname SecurityUtil
 * @Description 安全服务工具类
 * @Author xlr
 * @Date 2019-11-08 10:12
 * @Version 1.0
 */
@UtilityClass
public class SecurityUtil {

    public void writeJavaScript(RestResult r, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSON.toJSONString(r));
        printWriter.flush();
    }

    /**
     * 获取Authentication
     */
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * @Author xlr
     * @Description 获取用户
     * @Date 11:29 2019-11-10
     **/
    public PreSecurityUser getUser(){
        try {
            return (PreSecurityUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new ValidateException("登录状态过期");
        }
    }
}
