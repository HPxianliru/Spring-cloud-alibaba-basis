package com.xian.dianping.request;

import javax.validation.constraints.NotBlank;

/**
 * Created by hzllb on 2019/7/13.
 */
public class LoginReq {
    @NotBlank(message = "手机号不能为空")
    private String telphone;
    @NotBlank(message = "密码不能为空")
    private String password;

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
