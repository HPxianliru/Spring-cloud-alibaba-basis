package com.xian.cloud.notifier;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.*;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/12 17:45
 */
@Component
@Slf4j
public class ServerNotifier extends AbstractEventNotifier {

    public ServerNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                log.info("Instance状态更新 {} ({}) is {}", instance.getRegistration().getName(), event.getInstance(),
                        ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());
                String status = ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus();
                if("UP".equals(status)){
                    log.info("服务上线");
                }else if("OFFLINE".equals(status)){
                    log.info("服务下线");
                }

            } else if(event instanceof InstanceRegisteredEvent){
                log.info("Instance注册 {} ({}) {}", instance.getRegistration().getName(), event.getInstance(),
                        event.getType());
            }else if(event instanceof InstanceDeregisteredEvent){
                log.info("Instance注销");
            }else if(event instanceof InstanceEndpointsDetectedEvent){
                log.info("Instance端点检测");
            }else if(event instanceof InstanceInfoChangedEvent){
                log.info("Instance信息修改");
            }
        });
    }
}
