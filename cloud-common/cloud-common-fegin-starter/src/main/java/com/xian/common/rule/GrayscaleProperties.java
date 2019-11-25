package com.xian.common.rule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @Author: xlr
 * @Date: Created in 3:02 AM 2019/11/23
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GrayscaleProperties implements Serializable {

    private String version;

    private String serverName;

    private String serverGroup;

    private String active;

    private double weight = 1.0D;


}
