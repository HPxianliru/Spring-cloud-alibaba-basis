package com.xian.cloud.event;

import com.xian.cloud.router.DiscoveryRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.client.discovery.event.HeartbeatMonitor;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;


/**
 * <Description> 路由刷新事件发布，与事件监听者
 *
 * @author xianliru@100tal.com
 * @version 1.0
 * @createDate 2019/10/30 15:27
 */
@Service
public class RefreshRouteService implements ApplicationListener<ApplicationEvent> {


    @Autowired
    private ZuulHandlerMapping zuulHandlerMapping;

    private HeartbeatMonitor heartbeatMonitor = new HeartbeatMonitor();

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    private DiscoveryRouteLocator dynamicRouteLocator;

    /**
     * 动态路由实现 调用refreshRoute() 发布刷新路由事件
     */
    public void refreshRoute() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(dynamicRouteLocator);
        publisher.publishEvent(routesRefreshedEvent);
    }

    /**
     * 事件监听者。监控检测事件刷新
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ContextRefreshedEvent || event instanceof RefreshScopeRefreshedEvent || event instanceof RoutesRefreshedEvent){
            //主动手动刷新。上下文刷新，配置属性刷新
            zuulHandlerMapping.setDirty(true);
        }else if(event instanceof HeartbeatEvent){
            //心跳触发，将本地映射关系。关联到远程服务上
            HeartbeatEvent heartbeatEvent = (HeartbeatEvent)event;
            if(heartbeatMonitor.update(heartbeatEvent.getValue())){
                zuulHandlerMapping.setDirty(true);
            }
        }
    }
}
