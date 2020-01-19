package com.xian.register;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/19 5:30 下午
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static  ApplicationContext appContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.appContext = applicationContext;
    }


    public static <T> T getBean(String beanName){
       return (T) appContext.getBean(beanName);
    }

    public static <T> T getBean(Class beanName){
        return (T) appContext.getBean(beanName);
    }
}
