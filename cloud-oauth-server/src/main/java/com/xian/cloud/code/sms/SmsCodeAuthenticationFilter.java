package com.xian.cloud.code.sms;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Classname SmsCodeAuthenticationFilter
 * @Description
 * 1.认证请求的方法必须为POST
 * 2.从request中获取手机号
 * 3.封装成自己的Authenticaiton的实现类SmsCodeAuthenticationToken（未认证）
 * 4.调用 AuthenticationManager 的 authenticate 方法进行验证（即SmsCodeAuthenticationProvider）
 * @Author xianliru@163.com
 * @Date 2019-11-08 11:46
 * @Version 1.0
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * request中必须含有mobile参数
     */
    private static final String PHONE_KEY = "phone";
    /**
     * post请求
     */
    private boolean postOnly = true;

    private Gson gson = new Gson();


    /**
     * 处理的手机验证码登录请求处理url
     */
    SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/mobile/login", HttpMethod.POST.toString()));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        //判断是不是post请求
        if (postOnly && !request.getMethod().equals(HttpMethod.POST.toString())) {
            throw new AuthenticationServiceException("认证方法不支持: " + request.getMethod());
        }
        //从请求中获取手机号码
        String mobile = obtainMobile(request);
        Map parameterMap = getParameterMap(request);
        String s = charReader(request);
        Map map = gson.fromJson(s, Map.class);
        if (StringUtils.isBlank(mobile)){
            mobile = (String) map.get(PHONE_KEY);
        }
        if (mobile == null) {
            mobile = "";
        }
        mobile = mobile.trim();
        //创建SmsCodeAuthenticationToken(未认证)
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
        //设置用户信息
        setDetails(request, authRequest);
        //返回Authentication实例
        return this.getAuthenticationManager().authenticate(authRequest);
    }
    private void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    /**
     * 获取手机号
     * @param request
     * @return
     */
    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(PHONE_KEY);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    String charReader(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();

        String str, body = "";
        while((str = br.readLine()) != null){
            body += str;
        }
        return body;
    }
}
