package com.xian.cloud.common.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xian.cloud.common.enums.FilterTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/10/29 12:57
 */
@Component
@Slf4j
public class BannedAccessFilter extends ZuulFilter {

    /**
     * 路由器的类型指定
     * @return
     */
    @Override
    public String filterType() {
        return FilterTypeEnum.PRE.getType();
    }

    /**
     * 执行顺序 数值越小。执行顺序越靠前
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 返回一个boolean值来判断该过滤器是否要执行。我们可以通过此方法来指定过滤器的有效范围。
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String requestURI = request.getRequestURI();
        if(StringUtils.isNotBlank(requestURI) && (requestURI.contains("client") ||
            requestURI.contains("server"))){
            return true;
        }
        return false;
    }

    /**
     * 核心业务处理
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse servletResponse = context.getResponse();
        String uuid = UUID.randomUUID().toString();
        context.addZuulRequestHeader("X-Foo", uuid);
        context.addZuulRequestHeader("X-ABC",uuid);
        log.info("X-Foo:{}",uuid);
        return null;
    }
}
