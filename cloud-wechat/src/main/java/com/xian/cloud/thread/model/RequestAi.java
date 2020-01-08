package com.xian.cloud.thread.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/08 14:19
 */
@Data
public class RequestAi {

    //应用标识（AppId）
    @JsonProperty(value = "app_id")
    private Integer appId;

    //请求时间戳（秒级）
    @JsonProperty(value = "time_stamp")
    private Integer timeStamp;

    //随机字符串
    @JsonProperty(value = "nonce_str")
    private String nonceStr;

    //签名信息，详见接口鉴权
    private String sign;

    //会话标识（应用内唯一）
    private String session;

    //用户输入的聊天内容
    private String question;

//    @JsonProperty(value = "app_key")
//    private String appKey;
}
