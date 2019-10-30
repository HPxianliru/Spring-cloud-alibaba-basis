package com.xian.cloud.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.xian.cloud.enums.FilterTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <Description>
 *
 * @author xianliru@100tal.com
 * @version 1.0
 * @createDate 2019/10/29 16:07
 */
@Component
@Slf4j
public class UpdateParamsFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterTypeEnum.PRE.getType();
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {


        // 获取到request
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        // 获取请求参数name
        String name = "";
        try {

            // 请求方法
            String method = request.getMethod();
            log.info(String.format("%s >>> %s", method, request.getRequestURL().toString()));
            // 获取请求的输入流
            InputStream in = request.getInputStream();
            String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            // 如果body为空初始化为空json
            if (StringUtils.isBlank(body)) {
                body = "{}";
            }
            log.info("body" + body);
            // 转化成json
            JSONObject json = JSONObject.parseObject(body);
            // 关键步骤，一定要get一下,下面才能取到值requestQueryParams
            if ("GET".equals(method)) {
                request.getParameterMap();
                Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
                if (requestQueryParams == null) {
                    requestQueryParams = new HashMap<>();
                }
                //TODO  写业务代码
                List<String> arrayList = new ArrayList<>();
                arrayList.add("hello mike");
                requestQueryParams.put("mike",arrayList);
                ctx.setRequestQueryParams(requestQueryParams);
            }
            //post和put需重写HttpServletRequestWrapper
            if ("POST".equals(method) || "PUT".equals(method)) {
                //TODO 在这里修改或者添加内容
                json.put("tom","hello tom");
                String newBody = json.toString();
                final byte[] reqBodyBytes = newBody.getBytes();
                // 重写上下文的HttpServletRequestWrapper
                ctx.setRequest(new HttpServletRequestWrapper(request) {
                    @Override
                    public ServletInputStream getInputStream() throws IOException {
                        return new ServletInputStreamWrapper(reqBodyBytes);
                    }
                    @Override
                    public int getContentLength() {
                        return reqBodyBytes.length;
                    }
                    @Override
                    public long getContentLengthLong() {
                        return reqBodyBytes.length;
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
