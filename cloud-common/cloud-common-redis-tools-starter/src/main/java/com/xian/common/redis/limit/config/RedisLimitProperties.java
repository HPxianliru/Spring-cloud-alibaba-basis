package com.xian.common.redis.limit.config;

import com.xian.common.redis.constant.RedisToolsConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 限流配置
 *
 * @author xlr
 * @date 2018/8/15 9:10
 */
@Data
@ConfigurationProperties("taroco.redis.limit")
public class RedisLimitProperties {

    private Boolean enabled;
    /**
     * 具体限流value
     */
    private int value;
    /**
     * redis部署类型 1单机 2集群
     */
    private int type = RedisToolsConstant.SINGLE;
}
