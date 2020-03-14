package com.xian.dianping.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserModel implements Serializable {

    private Integer id;


    private Date createdAt;


    private Date updatedAt;


    private String telphone;


    private String password;


    private String nickName;


    private Integer gender;

}

