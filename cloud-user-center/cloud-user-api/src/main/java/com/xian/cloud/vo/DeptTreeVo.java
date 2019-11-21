package com.xian.cloud.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @Classname DeptTreeVo
 * @Description 构建部门树vo
 * @Author Created by xlr
 * @Date 2019-11-09 15:15
 * @Version 1.0
 */
@Setter
@Getter
@ToString
public class DeptTreeVo {

    private int id;
    private String label;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DeptTreeVo> children;

}
