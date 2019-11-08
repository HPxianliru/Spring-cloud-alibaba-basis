package com.xian.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 *
 * @Author: xlr
 * @Date: Created in 10:14 PM 2019/10/1
 */
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {


    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 针对redis数据失效事件，进行数据处理
     * @param message
     */
    @Override
    public void doHandleMessage(Message message) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        log.error( "RedisKeyExpirationListener onMessage 失效:{}",expiredKey );
        //TODO do what you want to do !!!
    }
}
