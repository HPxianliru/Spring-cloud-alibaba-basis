package com.xian.cloud;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @Author: xlr
 * @Date: Created in 3:03 PM 2019/10/27
 */
@EnableDiscoveryClient
@SpringBootApplication
@ServletComponentScan
@EnableFeignClients
public class DiscoveryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryClientApplication.class, args);
    }

}
