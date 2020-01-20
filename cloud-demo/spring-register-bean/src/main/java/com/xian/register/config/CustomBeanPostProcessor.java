package com.xian.register.config;

import com.xian.cloud.dto.DeptDTO;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/20 4:40 下午
 */
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof DeptDTO){
            // 如果当前的bean是DeptDTO,则打印日志
            System.out.println("postProcessBeforeInitialization bean : " + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof DeptDTO){
            System.out.println("postProcessAfterInitialization bean : " + beanName);
        }
        return bean;
    }
}
