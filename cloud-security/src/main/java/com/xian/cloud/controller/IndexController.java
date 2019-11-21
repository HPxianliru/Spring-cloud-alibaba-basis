package com.xian.cloud.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.xian.cloud.dto.UserDTO;
import com.xian.cloud.security.code.img.CaptchaUtil;
import com.xian.cloud.security.code.sms.SmsCodeService;
import com.xian.cloud.security.exception.ValidateCodeException;
import com.xian.cloud.service.LoginService;
import com.xian.cloud.user.fegin.UserService;
import com.xian.common.constant.PreConstant;
import com.xian.common.model.RestResult;
import com.xian.common.model.RestResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Classname IndexController
 * @Description 主页模块
 * @Author xlr
 * @Date 2019-11-07 12:38
 * @Version 1.0
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private LoginService loginService;

    /**
     * 生成验证码
     *
     * @param response
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        // 生成图片验证码
        BufferedImage image = CaptchaUtil.createImage();
        // 生成文字验证码
        String randomText = CaptchaUtil.drawRandomText(image);
        // 保存到验证码到 redis 有效期两分钟
        String t = request.getParameter("t");
        redisTemplate.opsForValue().set(PreConstant.PRE_IMAGE_KEY + t, randomText.toLowerCase(), 2, TimeUnit.MINUTES);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpeg", out);
    }


    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @PostMapping("/sendCode/{phone}")
    public RestResult sendSmsCode(@PathVariable("phone") String phone) {
        Map<String, Object> map = smsCodeService.sendCode(phone);
        // 获取状态码 00000 成功 00141 一小时内发送给单个手机次数超过限制 00142 一天内发送给单个手机次数超过限制
        String respCode = map.get("respCode").toString();
        String code = map.get("code").toString();
        if ("00141".equals(respCode) || "00142".equals(respCode)) {
            return RestResultBuilder.builder().failure().message("发送次数过多,稍后再试").build();
        }
        // 保存到验证码到 redis 有效期两分钟
        redisTemplate.opsForValue().set(phone, code, 2, TimeUnit.MINUTES);
        return RestResultBuilder.builder().success().build();
    }


    @PostMapping("/register")
    public RestResult register(@RequestBody UserDTO userDTO) {
        Object redisCode = redisTemplate.opsForValue().get(userDTO.getPhone());
        if (ObjectUtil.isNull(redisCode)) {
            throw new ValidateCodeException("验证码已失效");
        }
        if (!userDTO.getSmsCode().toLowerCase().equals(redisCode)) {
            throw new ValidateCodeException("短信验证码错误");
        }
        return RestResultBuilder.builder().success(userService.register(userDTO)).build();
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login")
    public RestResult login(String username, String password, HttpServletRequest request) {
        // 社交快速登录
        String token = request.getParameter("token");
        if (StrUtil.isNotEmpty(token)) {
            return RestResultBuilder.builder().success(token).build();
        }
        token = loginService.login(username, password);

        return RestResultBuilder.builder().success(token).build();

    }

    /**
     * @Description 使用jwt前后分离 只需要前端清除token即可 暂时返回success
     * @Date 08:13 2019-06-22
     **/
    @RequestMapping("/logout")
    public String logout() {
        return "success";
    }
}
