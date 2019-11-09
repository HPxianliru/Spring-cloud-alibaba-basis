package com.xian.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * <Description>
 *
 * @author xianliru@100tal.com
 * @version 1.0
 * @createDate 2019/11/09 17:32
 */
@Slf4j
public class RequestHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(final RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                //TODO 注意！！！ 不要全量都赋值到header，只选择我们需要的参数传递到下游服务。 在有些场景header 会导致异常出现。
                if("Authorization".equals(name)){
                    template.header(name, values);
                }
            }
            log.debug("feign interceptor header:{}", template);
        }
    }
}
