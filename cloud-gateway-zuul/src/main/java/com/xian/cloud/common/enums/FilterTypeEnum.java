package com.xian.cloud.common.enums;

import lombok.Getter;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/10/29 13:01
 */
@Getter
public enum FilterTypeEnum {

    PRE("pre","前置过滤"),
    ROUTE("route","路由请求时被调用"),
    POST("post","后置过滤器"),
    ERROR("error","后置错误处理");

    private String type;

    private String desc;

    FilterTypeEnum(String type,String desc){
        this.type = type;
        this.desc = desc;
    }

}
