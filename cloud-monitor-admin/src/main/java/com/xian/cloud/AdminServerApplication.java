package com.xian.cloud;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <Description>
 *
 * @author xianliru@100tal.com
 * @version 1.0
 * @createDate 2019/11/05 09:56
 */
@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
@Slf4j
public class AdminServerApplication {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(AdminServerApplication.class, args);
        log.info("AdminServer启动!");
    }
}
