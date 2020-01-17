package com.xian.cloud.fegin;


import com.xian.cloud.thread.TxAi;
import com.xian.cloud.thread.model.JDCouponResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "couponFegin",url = TxAi.JD_COUPON)
public interface JDCouponFegin {

    @RequestMapping(value = "fapi/shop/goods/search",method = RequestMethod.POST,headers = {"Content-Type: application/x-www-form-urlencoded","Accept: application/json, text/plain, */*"})
    JDCouponResponse coupon(@RequestParam("keyword") String keyword,
                            @RequestParam("pageIndex") Integer pageIndex,
                            @RequestParam("pageSize") Integer pageSize,
                            @RequestParam("sign") String sign,
                            @RequestParam("timestamp") String timestamp,
                            @RequestParam("token") String token);
}
