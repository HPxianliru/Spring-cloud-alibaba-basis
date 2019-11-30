package com.xian.cloud.controller;

import com.xian.cloud.code.img.CaptchaUtil;
import com.xian.cloud.code.sms.SmsCodeService;
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
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/30 21:50
 */
@RestController
@RequestMapping("public")
public class LoginController {




    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsCodeService smsCodeService;

    @GetMapping("login")
    public RestResult login(String name,String password,String code){


        return null;
    }

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


}
