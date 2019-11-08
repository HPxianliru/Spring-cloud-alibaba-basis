package com.xian.cloud.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <Description>
 *
 * @author xianliru@100tal.com
 * @version 1.0
 * @createDate 2019/10/29 13:57
 */
@WebFilter(filterName = "test",urlPatterns = "/*")
@Slf4j
public class UrlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.warn("UrlFilter init.......");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String requestURI = req.getRequestURI();
        String header = req.getHeader("X-Foo");
        String abc = req.getHeader("X-ABC");
        String authorization = req.getHeader("Authorization");
        String tom = req.getParameter("tom");
        String mike = req.getParameter("mike");
        log.warn("过滤器：请求地址"+requestURI);
        log.warn("uuid:{}",header);
        log.warn("abc uuid:{}",abc);
        log.warn("authorization :{}",authorization);
        log.warn("tom :{}",tom);
        log.warn("mike :{}",mike);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.warn(" 过滤器被销毁");

    }
}
