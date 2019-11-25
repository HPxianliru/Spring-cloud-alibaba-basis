package com.xian.common.redis.limit.config;


import com.xian.common.redis.limit.RedisLimit;
import com.xian.common.redis.limit.intercept.SpringMvcIntercept;
import com.xian.common.redis.util.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * redis 请求限流自动配置
 *
 * @author xlr
 * @date 2018/5/21 14:11
 */
@Configuration
@EnableConfigurationProperties(RedisLimitProperties.class)
@ConditionalOnProperty(name = "cloud.redis.limit.enabled", havingValue = "true")
@Slf4j
public class RedisLimitAutoConfigurer implements WebMvcConfigurer {

    @Autowired
    private RedisLimitProperties limitProperties;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public RedisLimit redisLimit() {
        log.info("redis 请求限流自动配置 加载完成");
        return new RedisLimit.Builder(jedisConnectionFactory, limitProperties.getType())
                .limit(limitProperties.getValue())
                .build();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SpringMvcIntercept(redisLimit()));
    }

    @Bean
    @ConditionalOnMissingBean(RedisRepository.class)
    @ConditionalOnBean(StringRedisTemplate.class)
    public RedisRepository redisUtils(RedisTemplate redisTemplate) {
        RedisRepository redisUtils =   new RedisRepository(redisTemplate);
        return redisUtils;
    }
}
