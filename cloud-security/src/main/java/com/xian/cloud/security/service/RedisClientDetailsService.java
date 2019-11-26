package com.xian.cloud.security.service;

import com.alibaba.fastjson.JSONObject;
import com.xian.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Yuuki
 */
@Slf4j
@Service
public class RedisClientDetailsService extends JdbcClientDetailsService {
    /**
     * 缓存 client的 redis key，这里是 hash结构存储
     */
    private static final String CACHE_CLIENT_KEY = "client_details";

    @Autowired
    private RedisService redisService;

    public RedisClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails clientDetails = null;
        String value = (String) redisService.hget(CACHE_CLIENT_KEY, clientId);
        if (StringUtils.isBlank(value)) {
            clientDetails = cacheAndGetClient(clientId);
        } else {
            clientDetails = JSONObject.parseObject(value, BaseClientDetails.class);
        }

        return clientDetails;
    }

    /**
     * 缓存 client并返回 client
     *
     * @param clientId clientId
     */
    public ClientDetails cacheAndGetClient(String clientId) {
        ClientDetails clientDetails = null;
        clientDetails = super.loadClientByClientId(clientId);
        if (clientDetails != null) {
            redisService.hset(CACHE_CLIENT_KEY, clientId, JSONObject.toJSONString(clientDetails));
        }
        return clientDetails;
    }

    /**
     * 删除 redis缓存
     *
     * @param clientId clientId
     */
    public void removeRedisCache(String clientId) {
        redisService.hdel(CACHE_CLIENT_KEY, clientId);
    }

    /**
     * 将 oauth_client_details全表刷入 redis
     */
    public void loadAllClientToCache() {
        if (redisService.hasKey(CACHE_CLIENT_KEY)) {
            return;
        }
        log.info("将oauth_client_details全表刷入redis");

        List<ClientDetails> list = super.listClientDetails();
        if (CollectionUtils.isEmpty(list)) {
            log.error("oauth_client_details表数据为空，请检查");
            return;
        }
        list.forEach(client -> redisService.hset(CACHE_CLIENT_KEY, client.getClientId(), JSONObject.toJSONString(client)));
    }
}
