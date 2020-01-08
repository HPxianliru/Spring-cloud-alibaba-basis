package com.xian.cloud.fegin;

import com.xian.cloud.thread.TxAi;
import com.xian.cloud.thread.model.BodyAi;
import com.xian.cloud.thread.model.ResponseAi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/08 15:06
 */
@FeignClient(value = "txaiFegin",url = TxAi.AI_URL)
public interface TxAiFegin {


    @RequestMapping(value = "fcgi-bin/nlp/nlp_textchat",method = RequestMethod.POST,headers = {"Content-Type: application/x-www-form-urlencoded","Accept: application/x-www-form-urlencoded"})
    ResponseAi<BodyAi> post(@RequestParam("app_id") Integer app_id,
                            @RequestParam("time_stamp") Integer time_stamp,
                            @RequestParam("nonce_str") String nonce_str,
                            @RequestParam("sign") String sign,
                            @RequestParam("session") String session,
                            @RequestParam("question") String question);
}
