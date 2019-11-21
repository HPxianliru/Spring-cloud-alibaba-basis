package com.xian.common.utils.data;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by shihy on 17/8/11.
 */
public class TransferUtil {
    public static <T> T transfer(Object text, Class<T> clazz) {
        return JSONObject.parseObject(JSONObject.toJSONString(text), clazz);
    }
}
