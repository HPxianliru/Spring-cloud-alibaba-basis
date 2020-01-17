package com.xian.cloud.controller;

import com.google.gson.Gson;
import com.xian.cloud.fegin.JDCouponFegin;
import com.xian.cloud.thread.model.JDCouponResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/08 15:11
 */
@RestController
@RequestMapping("tx")
public class TxAiController {


    @Autowired
    private JDCouponFegin jdCouponFegin;

    Gson gson = new Gson();

    @RequestMapping(value = "coupon",method = RequestMethod.GET)
    public String  coupon(@RequestParam("key") String key){
        JDCouponResponse coupon = jdCouponFegin.coupon(key,1,5,"139f8772",String.valueOf(System.currentTimeMillis()),"d80f24bf8117418e87a1c089db173e78");

        return gson.toJson(coupon);
    }

}
