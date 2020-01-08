package com.xian.cloud.thread.model;

import lombok.Data;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/08 14:43
 */
@Data
public class ResponseAi<T> {

    //0表示成功，非0表示出错
    private Integer ret;

    //返回信息；ret非0时表示出错时错误原因
    private String msg;

    //返回数据；ret为0时有意义
    private T data;

    private String session;

    //UTF-8编码，非空
    private String answer;
}
