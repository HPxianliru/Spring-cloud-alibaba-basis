package com.xian.cloud.fegin;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.xian.cloud.common.handler.ServerServiceFallBackFactory;
import com.xian.cloud.entity.UserEntity;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/06 11:47
 */
@Component
public class FeginConfig {

    /**
     * Sentinel 支持 RestTemplate 只需要加一个注解
     * @return
     */
    @Bean
    @LoadBalanced
    @SentinelRestTemplate
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @FeignClient(value = "cloud-discovery-server",fallbackFactory = ServerServiceFallBackFactory.class)
    public interface ServerService {

        @RequestMapping(path = "server/log/update", method = RequestMethod.POST)
        boolean update(@RequestBody UserEntity entity);

        @GetMapping("/server/hello")
        String hello(@RequestParam("name") String name);
    }
}
