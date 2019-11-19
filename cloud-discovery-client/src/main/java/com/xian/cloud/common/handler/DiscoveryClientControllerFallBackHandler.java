package com.xian.cloud.common.handler;

import com.xian.cloud.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * 仅针对降级功能生效（DegradeException）
 * @Author: xlr
 * @Date: Created in 9:13 PM 2019/11/16
 */
@Slf4j
public class DiscoveryClientControllerFallBackHandler {

    public static String defaultMessage(Throwable t){
        log.warn( "DiscoveryClientControllerFallBackHandler defaultMessage Throwable : {}",t );

        return "defaultMessage 服务降级，请稍后尝试";
    }


    public static String saveTx(UserEntity entity,Throwable t) {

        log.warn( "DiscoveryClientControllerFallBackHandler saveTx Throwable : {}",t );

        return "saveTx 服务降级，请稍后尝试";
    }
}
