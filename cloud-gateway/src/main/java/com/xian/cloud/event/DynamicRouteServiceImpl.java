package com.xian.cloud.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/09 10:40
 */
@Slf4j
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    @Qualifier("routeDefinitionRepositor")
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;


    /**
     * 添加路由实体类
     * @param definition
     * @return
     */
    public boolean add(RouteDefinition definition){
        routeDefinitionWriter.save((Mono<RouteDefinition>) Mono.just(definition).subscribe());
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }

    /**
     *
     * @param definition 路由实体类
     * @return
     */
    public boolean update(RouteDefinition definition){
        try {
            routeDefinitionWriter.delete(Mono.just(definition.getId()));
        }catch (Exception e){
            log.error("update 失败。没有找到对应的路由ID :{}",definition.getId());
        }

        routeDefinitionWriter.save((Mono<RouteDefinition>) (Mono.just(definition)).subscribe());
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }

    /**
     * serviceId
     * @param id
     * @return
     */
    public boolean del(String id){
        routeDefinitionWriter.delete(Mono.just(id));
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
