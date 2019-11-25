package com.xian.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xlr
 * @Date: Created in 下午9:19 2019/11/24
 */
public class ThreadLocalUtils {

     private static ThreadLocal<Map<String,Object>> requestThreadLocal = new InheritableThreadLocal<>();


    public static void set( String key,Object object){
        Map<String, Object> map = requestThreadLocal.get();
        if(map == null){
            map = new HashMap<>();
        }
        map.put(key,object);
        requestThreadLocal.set(map);
    }

    /**
     * 根据KEY 值获取 obj
     *
     * @param key
     * @return
     */
    public static Object getKey(String key){
        if(requestThreadLocal.get() == null ){
            return null;
        }
       return requestThreadLocal.get().get(key);
    }

    /**
     * 清空操作
     */
    public static void remove(){
        requestThreadLocal.remove();
    }

}
