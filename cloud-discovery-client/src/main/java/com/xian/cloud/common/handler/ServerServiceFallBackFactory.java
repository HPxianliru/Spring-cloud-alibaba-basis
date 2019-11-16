package com.xian.cloud.common.handler;

import com.xian.cloud.entity.UserEntity;
import com.xian.cloud.fegin.FeginConfig;
import com.xian.common.config.fallback.BaseFallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: xlr
 * @Date: Created in 8:59 PM 2019/11/16
 */
@Slf4j
@Component
public class ServerServiceFallBackFactory extends BaseFallbackFactory<FeginConfig.ServerService> {


    @Override
    public FeginConfig.ServerService create(Throwable throwable) {

        log.warn( "fallBack exception : {}",throwable );
        return new FeginConfig.ServerService() {
            @Override
            public boolean update(UserEntity entity) {
                log.warn( "熔断返回 错误" );
                return false;
            }

            @Override
            public String hello(String name) {
                return "服务不可用";
            }
        };
    }
}
