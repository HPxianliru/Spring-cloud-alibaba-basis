package com.xian.common.config.fallback;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * FallBackFactory 基础类在使用时。可以捕获异常进行处理。
 *
 * T 参数是指fegin的接口类继承 BaseFallbackFactory 必须指定要触发的fallback的接口类
 *  
 *
 * @Author: xlr
 * @Date: Created in 8:44 PM 2019/11/16
 */
@Slf4j
public abstract class BaseFallbackFactory<T> implements FallbackFactory<T> {


//    @Override
//    public T create(Throwable cause) {
//
//        log.warn( " BaseFallbackFactory :{} ",cause );
//        // 做你想做的
//
//        return new T (){
//            //里面书写具体的熔断返回的内容
//        };
//    }
}
