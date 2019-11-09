package com.xian.common.oss;

import com.aliyun.oss.OSSClient;
import com.xian.common.oss.service.OSSClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <Description>
 *
 * @author xianliru@100tal.com
 * @version 1.0
 * @createDate 2019/11/09 17:49
 */
@Configuration
@ConditionalOnProperty(name = "cloud.oss.enabled", havingValue = "true")
@Slf4j
public class OSSAutoConfigure {

    @Bean
    OSSClientRepository ossClientRepository(OSSClient ossClient){
        log.info("OSSClient 加载完毕");
        return new OSSClientRepository(ossClient);
    }
}
