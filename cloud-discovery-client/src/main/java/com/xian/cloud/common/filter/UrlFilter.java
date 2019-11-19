package com.xian.cloud.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/10/29 13:57
 */
@WebFilter(filterName = "test",urlPatterns = "/*")
@Slf4j
public class UrlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("UrlFilter init.......");
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
        log.info("过滤器：请求地址"+requestURI);
        log.info("uuid:{}",header);
        log.info("abc uuid:{}",abc);
        log.info("authorization :{}",authorization);
        log.info("tom :{}",tom);
        log.info("mike :{}",mike);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info(" 过滤器被销毁");
    }
}
