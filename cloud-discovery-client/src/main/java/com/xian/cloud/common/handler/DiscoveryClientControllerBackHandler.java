package com.xian.cloud.common.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

/**
 *  对应处理 BlockException 的函数名称 服务限流
 * @Author: xlr
 * @Date: Created in 9:08 PM 2019/11/16
 */
@Slf4j
public class DiscoveryClientControllerBackHandler {


    public static String defaultMessage(BlockException e){
        log.warn( "DiscoveryClientControllerBackHandler  save BlockException : {}",e );
        return "defaultMessage 服务限流，请稍后尝试";
    }
}
