package com.xian.common.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果RestResult.
 * @Author: xlr
 * @Date: Created in 11:29 PM 2019/9/11
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api("返回实体类")
public class RestResult<T extends Object> implements Serializable {

    /**
     * 返回编码
     */
    @JSONField(ordinal = 1)
    @ApiModelProperty(value = "code状态码 1正常 其他全部错误",name="code",required = true,dataType="Integer")
    private int code;

    /**
     * 返回消息
     */
    @JSONField(ordinal = 2)
    @ApiModelProperty(value = "返回提示信息",name="msg",required = true,dataType="string")
    private String message;

    /**
     * 返回数据
     */
    @JSONField(ordinal = 3)
    @ApiModelProperty(value = "返回的json数据",name="data",required = true)

    private T data;

    public RestResult() {

    }

    public RestResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return StringUtils.trimToEmpty(message);
    }

    public String getMessage() {
        return StringUtils.trimToEmpty(message);
    }

    public void setMessage(String message){
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public RestResult<T> put(String key, Object value) {
        Map map = new HashMap();
        map.put(key, value);
        this.setData((T)map);
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
