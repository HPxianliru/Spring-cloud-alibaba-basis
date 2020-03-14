package com.xian.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/02/02 2:11 下午
 */
@SpringBootApplication(scanBasePackages = "com.xian.es.data")
public class EsApplication {

    public static void main(String[] args) {

        SpringApplication.run(EsApplication.class, args);
    }
}
