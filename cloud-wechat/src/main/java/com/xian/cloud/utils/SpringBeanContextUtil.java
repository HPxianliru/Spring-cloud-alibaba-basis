package com.xian.cloud.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/08 16:54
 */
@Component
public class SpringBeanContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanContextUtil.applicationContext = applicationContext;
    }

    public static  <T> T getBean(String bean){
       return (T) applicationContext.getBean(bean);
    }

    public static  <T> T getBean(Class clazz){
        return (T) applicationContext.getBean(clazz);
    }
}
