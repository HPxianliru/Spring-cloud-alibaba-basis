package com.xian.dianping.service;


import com.xian.common.exception.BusinessException;
import com.xian.dianping.entity.UserModel;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hzllb on 2019/7/7.
 */
public interface UserService {

    UserModel getUser(Integer id);

    UserModel register(UserModel registerUser) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;

    UserModel login(String telphone, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, BusinessException;

    Integer countAllUser();
}
