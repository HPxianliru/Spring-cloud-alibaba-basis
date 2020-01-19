package com.xian.register.service;

import com.xian.cloud.dto.DeptDTO;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;


/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/19 5:22 下午
 */
@Component
public class LoadBean implements BeanFactoryPostProcessor {

    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        this.defaultListableBeanFactory = (DefaultListableBeanFactory)configurableListableBeanFactory;

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition("com.xian.register.bean.DynamicCreateBean");
        //用于设置指定的类中需要引入的其他bean
        //beanDefinitionBuilder.addPropertyValue("otherBeanName","otherBeanName");
        this.defaultListableBeanFactory.registerBeanDefinition("dynamicCreateBean",beanDefinitionBuilder.getBeanDefinition());

        beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DeptDTO.class.getName());
        //用于设置指定的类中需要引入的其他bean
        //beanDefinitionBuilder.addPropertyValue("otherBeanName","otherBeanName");
        this.defaultListableBeanFactory.registerBeanDefinition("deptDTO",beanDefinitionBuilder.getBeanDefinition());
    }
}
