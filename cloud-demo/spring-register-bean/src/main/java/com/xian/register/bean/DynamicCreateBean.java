package com.xian.register.bean;

import com.xian.cloud.dto.DeptDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/19 5:22 下午
 */
@Slf4j
public class DynamicCreateBean {


    public void printMethod(DeptDTO d){

        log.info("DynamicCreateBean Success:{}",d);
    }

}
