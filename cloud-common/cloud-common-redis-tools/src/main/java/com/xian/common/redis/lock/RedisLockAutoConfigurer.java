package com.xian.common.redis.lock;


import com.xian.common.redis.limit.config.RedisLimitProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * redis 分布式lock自动配置
 *
 * @author xlr
 * @date 2018/5/21 14:11
 */
@Configuration
@EnableConfigurationProperties(RedisLimitProperties.class)
@ConditionalOnProperty(name = "cloud.redis.lock.enabled", havingValue = "true")
@Slf4j
public class RedisLockAutoConfigurer implements WebMvcConfigurer {

    @Bean
    public RedisDistributedLock redisDistributedLock(RedisTemplate<String, Object> redisTemplate){
        log.info("redis 分布式lock自动配置 加载完成");
        return new RedisDistributedLock(redisTemplate);
    }
}
