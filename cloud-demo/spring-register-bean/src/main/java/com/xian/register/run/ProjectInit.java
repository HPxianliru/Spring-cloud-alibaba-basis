package com.xian.register.run;

import com.xian.register.SpringUtils;
import com.xian.register.enums.RabbitEnums;
import com.xian.register.service.SendMesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/20 11:08 上午
 */
@Component
@Slf4j
public class ProjectInit implements CommandLineRunner {

    @Autowired
    SendMesService sendMesService;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
        bindRabbitMq();
        for (int i = 0; i < 1 ; i++) {
            Thread.sleep(1000);
            sendMesService.sendDirectMessage();
            Thread.sleep(1000);
            sendMesService.sendTopicMessage1();
            Thread.sleep(1000);
            sendMesService.sendTopicMessage2();
        }

    }

    /**
     * Rabbit binding 交换机队列绑定。
     */
    public void bindRabbitMq(){
        RabbitEnums[] values = RabbitEnums.values();

        for (RabbitEnums value : values) {
            log.info("{} binding {}",value.getQueueName(),value.getExchangeName());
            Queue bean = SpringUtils.getBean(value.getQueueName());

            Exchange exchange  = SpringUtils.getBean(value.getExchangeName());

            // set up the queue, exchange, binding on the broker
            RabbitAdmin admin = new RabbitAdmin(connectionFactory);
            //queue
            admin.declareQueue(bean);

            admin.declareExchange(exchange);
            BindingBuilder.GenericArgumentsConfigurer with = BindingBuilder.bind(bean).to(exchange).with(value.getKey());
            //binding
            admin.declareBinding(with.noargs());
        }
    }
}
