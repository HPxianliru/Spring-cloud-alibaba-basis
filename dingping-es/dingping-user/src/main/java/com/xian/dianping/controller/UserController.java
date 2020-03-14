package com.xian.dianping.controller;


import com.xian.common.exception.BusinessException;
import com.xian.common.model.RestResult;
import com.xian.common.model.RestResultBuilder;
import com.xian.dianping.common.CommonUtil;
import com.xian.dianping.common.EmBusinessError;
import com.xian.dianping.entity.UserModel;
import com.xian.dianping.request.LoginReq;
import com.xian.dianping.request.RegisterReq;
import com.xian.dianping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hzllb on 2019/7/7.
 */
@Controller("/user")
@RequestMapping("/user")
public class UserController {

    public static final String CURRENT_USER_SESSION = "currentUserSession";

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserService userService;


    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "test";
    }

    @RequestMapping("/index")
    public ModelAndView index(){
        String userName = "imooc";
        ModelAndView modelAndView = new ModelAndView("/index.html");
        modelAndView.addObject("name",userName);
        return modelAndView;
    }

    @RequestMapping("/get")
    @ResponseBody
    public RestResult<UserModel> getUser(@RequestParam(name="id") Integer id) throws BusinessException {
        UserModel userModel = userService.getUser(id);
        if(userModel == null){
            //return CommonRes.create(new CommonError(EmBusinessError.NO_OBJECT_FOUND),"fail");
            throw new BusinessException();
        }else{
            return RestResultBuilder.builder().success().data(userModel).build();
        }

    }


    @RequestMapping("/register")
    @ResponseBody
    public RestResult<UserModel> register(@Valid @RequestBody RegisterReq registerReq, BindingResult bindingResult) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if(bindingResult.hasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR+"", CommonUtil.processErrorString(bindingResult));
        }

        UserModel registerUser = new UserModel();
        registerUser.setTelphone(registerReq.getTelphone());
        registerUser.setPassword(registerReq.getPassword());
        registerUser.setNickName(registerReq.getNickName());
        registerUser.setGender(registerReq.getGender());

        UserModel resUserModel = userService.register(registerUser);

        return RestResultBuilder.builder().success().data(resUserModel).build();
    }

    @RequestMapping("/login")
    @ResponseBody
    public RestResult<UserModel> login(@RequestBody @Valid LoginReq loginReq, BindingResult bindingResult) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if(bindingResult.hasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR+"", CommonUtil.processErrorString(bindingResult));
        }
        UserModel userModel = userService.login(loginReq.getTelphone(),loginReq.getPassword());
        httpServletRequest.getSession().setAttribute(CURRENT_USER_SESSION,userModel);

        return RestResultBuilder.builder().success().data(userModel).build();

    }

    @RequestMapping("/logout")
    @ResponseBody
    public RestResult<UserModel> logout() throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        httpServletRequest.getSession().invalidate();
        return RestResultBuilder.builder().success().data(null).build();
    }

    //获取当前用户信息
    @RequestMapping("/getcurrentuser")
    @ResponseBody
    public RestResult<UserModel> getCurrentUser(){
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute(CURRENT_USER_SESSION);

        return RestResultBuilder.builder().success().data(userModel).build();
    }
}
