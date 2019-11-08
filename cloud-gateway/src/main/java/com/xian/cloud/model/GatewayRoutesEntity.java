package com.xian.cloud.model;

import lombok.Data;

/**
 * @Author: xlr
 * @Date: Created in 5:10 PM 2019/9/29
 */
@Data
public class GatewayRoutesEntity {

    private Long id;

    private String serviceId;

    private String uri;

    private String predicates;

    private String filters;

    //有效 1有效 0 无效
    private Integer valid;
}
