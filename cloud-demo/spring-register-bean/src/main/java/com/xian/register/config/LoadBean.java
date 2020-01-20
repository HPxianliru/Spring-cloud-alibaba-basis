package com.xian.register.config;

import com.xian.cloud.dto.DeptDTO;
import com.xian.register.enums.RabbitEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;


/**
 * <Description>
 *
 * //设置Bean的构造函数传入的参数值
 * public BeanDefinitionBuilder addConstructorArgValue(Object value)
 * //设置构造函数引用其他的bean
 * public BeanDefinitionBuilder addConstructorArgReference(String beanName)
 * //设置这个bean的 init方法和destory方法
 * public BeanDefinitionBuilder setInitMethodName(String methodName)
 * public BeanDefinitionBuilder setDestroyMethodName(String methodName)
 * //设置单例/多例
 * public BeanDefinitionBuilder setScope(String scope)
 * //设置是否是个抽象的BeanDefinition，如果为true，表明这个BeanDefinition只是用来给子BeanDefinition去继承的，Spring不会去尝试初始化这个Bean。
 * public BeanDefinitionBuilder setAbstract(boolean flag)
 * //是否懒加载，默认是false
 * public BeanDefinitionBuilder setLazyInit(boolean lazy)
 * //自动注入依赖的模式，默认不注入
 * public BeanDefinitionBuilder setAutowireMode(int autowireMode)
 * //检测依赖。
 * public BeanDefinitionBuilder setDependencyCheck(int dependencyCheck)
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/19 5:22 下午
 */
@Component
@Slf4j
public class LoadBean implements BeanFactoryPostProcessor {

    private DefaultListableBeanFactory defaultListableBeanFactory;

    /**
     * 这个接口的作用是在Spring上下文的注册Bean定义的逻辑都跑完后，但是所有的Bean都还没真正实例化之前调用
     * @param configurableListableBeanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        this.defaultListableBeanFactory = (DefaultListableBeanFactory)configurableListableBeanFactory;
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition("com.xian.register.bean.DynamicCreateBean");
        log.info("DynamicCreateBean init...");
        //用于设置指定的类中需要引入的其他bean
        //beanDefinitionBuilder.addPropertyValue("otherBeanName","otherBeanName");
        this.defaultListableBeanFactory.registerBeanDefinition("dynamicCreateBean",beanDefinitionBuilder.getBeanDefinition());

        beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DeptDTO.class.getName()).addPropertyValue("name","测试");
        //用于设置指定的类中需要引入的其他bean
        //beanDefinitionBuilder.addPropertyValue("otherBeanName","otherBeanName");
        this.defaultListableBeanFactory.registerBeanDefinition("deptDTO",beanDefinitionBuilder.getBeanDefinition());
        log.info("deptDTO init...");
        rabbitReceiverConfig();
    }

    /**
     * spring beanDefinition添加 queue 和 exchange 到spring的描述类
     */
    public void rabbitReceiverConfig(){
        RabbitEnums[] values = RabbitEnums.values();

        for (RabbitEnums value : values) {
            log.info("{} init...{}",value.getQueueName(),value.getDesc());
            //创建队列
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(Queue.class.getName()).addConstructorArgValue(value.getQueueName());
            this.defaultListableBeanFactory.registerBeanDefinition(value.getQueueName(),beanDefinitionBuilder.getBeanDefinition());

            //设置 交换机
            String className = "";
            if(value.isTopic()){
                className = TopicExchange.class.getName();
            }else {
                className = DirectExchange.class.getName();
            }
            beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(className).addConstructorArgValue(value.getExchangeName());
            this.defaultListableBeanFactory.registerBeanDefinition(value.getExchangeName(),beanDefinitionBuilder.getBeanDefinition());
        }
    }
}
