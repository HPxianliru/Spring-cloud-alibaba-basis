package com.xian.cloud.fegin;

import com.xian.cloud.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

/**
 * <Description>
 *
 * @author xianliru@100tal.com
 * @version 1.0
 * @createDate 2019/11/06 11:47
 */
@Component
public class FeginConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @FeignClient(value = "server", url = "http://127.0.0.1:9012")
    public interface ServerService {

        @RequestMapping(path = "server/log/update", method = RequestMethod.POST)
        boolean update(@RequestBody UserEntity entity);
    }
}
