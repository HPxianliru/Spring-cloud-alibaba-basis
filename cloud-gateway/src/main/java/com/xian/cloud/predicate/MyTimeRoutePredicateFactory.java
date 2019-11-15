package com.xian.cloud.predicate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/15 10:58
 */
@Slf4j
@Component
public class MyTimeRoutePredicateFactory extends AbstractRoutePredicateFactory<MyTimeConfig> {

    public MyTimeRoutePredicateFactory() {
        super(MyTimeConfig.class);
    }

    /**
     * 核心的业务判断
     * @param config
     * @return
     */
    @Override
    public Predicate<ServerWebExchange> apply(MyTimeConfig config) {
        return serverWebExchange -> {
            log.info("MyTimeRoutePredicateFactory : {}",config.isTime());
            return config.isTime();
        };
    }

    /**
     * 设置参数占位顺序
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("time");
    }
}
@Data
class MyTimeConfig{
    private boolean time;
}
