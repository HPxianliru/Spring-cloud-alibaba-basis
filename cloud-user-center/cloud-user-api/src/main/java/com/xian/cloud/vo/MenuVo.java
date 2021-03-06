package com.xian.cloud.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @Classname menuVo
 * @Description TODO
 * @Author xlr
 * @Date 2019-11-05 16:38
 * @Version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVo {

    private String name;
    private String path;
    private String redirect;
    private String component;
    private Boolean alwaysShow;
    private MenuMetaVo meta;
    private List<MenuVo> children;

}
