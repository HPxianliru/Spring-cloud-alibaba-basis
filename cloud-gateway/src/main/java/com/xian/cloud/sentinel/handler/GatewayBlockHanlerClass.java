package com.xian.cloud.sentinel.handler;

import com.xian.cloud.model.RestResult;
import com.xian.cloud.model.RestResultBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: xlr
 * @Date: Created in 8:37 PM 2019/11/16
 */
@Slf4j
public class GatewayBlockHanlerClass {


    public RestResult addApiDefinition(String apiName, String pattern,Throwable throwable){

        return RestResultBuilder.builder().failure().message( "请求太多，请稍后再次尝试" ).build();
    }
}
