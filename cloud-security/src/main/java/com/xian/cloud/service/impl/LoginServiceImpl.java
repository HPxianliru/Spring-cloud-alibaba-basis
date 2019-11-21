package com.xian.cloud.service.impl;

import com.xian.cloud.dto.UserDTO;
import com.xian.common.security.PreSecurityUser;
import com.xian.cloud.security.util.JwtUtil;
import com.xian.cloud.service.LoginService;
import com.xian.cloud.user.fegin.UserService;
import com.xian.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/21 17:21
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Override
    public String login(String username, String password) {
        //用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername()去验证用户名和密码，
            // 如果正确，则存储该用户名密码到security 的 context中
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new BusinessException("用户名或密码错误", 402);
            } else if (e instanceof DisabledException) {
                throw new BusinessException("账户被禁用", 402);
            } else if (e instanceof AccountExpiredException) {
                throw new BusinessException("账户过期无法验证", 402);
            } else {
                throw new BusinessException("账户被锁定,无法登录", 402);
            }
        }
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        PreSecurityUser userDetail = (PreSecurityUser) authentication.getPrincipal();
        return JwtUtil.generateToken(userDetail);
    }


    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    public boolean register(UserDTO userDTO) {
       return userService.register(userDTO);
    }
}
