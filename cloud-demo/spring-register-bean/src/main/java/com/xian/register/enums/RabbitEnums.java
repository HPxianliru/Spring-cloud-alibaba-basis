package com.xian.register.enums;

import lombok.Getter;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/20 11:36 上午
 */
@Getter
public enum  RabbitEnums {

    MAN("topic.man","topicExchange","bindingExchangeMessage",true,"topic.man","man 测试"),
    WOMAN("topic.woman","topicExchange","bindingExchangeMessage2",true,"topic.#","woman"),
    TEST("testDirectQueue","testDirectExchange","bindingDirect",false,"testDirectRouting","test");

    private String queueName;
    private String exchangeName;
    private String binding;
    private String desc;
    private String key;
    private boolean topic;

    RabbitEnums(String queueName,String exchangeName,String binding,boolean topic,String key,String desc){
        this.desc =desc;
        this.binding = binding;
        this.exchangeName =exchangeName;
        this.queueName = queueName;
        this.topic = topic;
        this.key = key;
    }


}
