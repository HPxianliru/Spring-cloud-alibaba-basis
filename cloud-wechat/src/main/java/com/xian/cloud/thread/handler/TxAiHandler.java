package com.xian.cloud.thread.handler;

import com.xian.cloud.fegin.TxAiFegin;
import com.xian.cloud.thread.model.BodyAi;
import com.xian.cloud.thread.model.RequestAi;
import com.xian.cloud.thread.model.ResponseAi;
import com.xian.cloud.utils.MD5;
import com.xian.cloud.utils.SortUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/08 16:39
 */
@Service
public class TxAiHandler {

    @Autowired
    private TxAiFegin txAiFegin;

    @Value("${tx.appId}")
    private Integer appId;

    @Value("${tx.appKey}")
    private String appKey;


    public String answer(String question,String mesId){

        RequestAi ra = new RequestAi();
        ra.setAppId(appId);
        ra.setNonceStr("fa577ce340859f9fe");
        ra.setQuestion(question);
        ra.setSession(mesId);
        ra.setTimeStamp(MD5.getSecondTimestamp(new Date()));
        //ra.setAppKey("WKuGKFDhhfv3T4D2");

        Map<String,Object> map = new HashMap<>();
        map.put("app_id",ra.getAppId());
        map.put("time_stamp",ra.getTimeStamp());
        map.put("nonce_str",ra.getNonceStr());
        map.put("session",ra.getSession());
        map.put("question",ra.getQuestion());
        //map.put("sign",ra.getSign());
        String s = SortUtils.formatUrlParam(map, "utf-8", false);
        s = s+"&app_key="+appKey;
        String md5 = MD5.getMD5String(s);

        ra.setSign(md5.toUpperCase());

        ResponseAi<BodyAi> response = txAiFegin.post(ra.getAppId(),ra.getTimeStamp(),ra.getNonceStr(),ra.getSign(),ra.getSession(),ra.getQuestion());

        return response.getData().getAnswer();
    }
}
