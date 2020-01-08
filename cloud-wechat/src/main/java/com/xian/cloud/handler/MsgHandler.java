package com.xian.cloud.handler;

import com.xian.cloud.builder.TextBuilder;
import com.xian.cloud.thread.handler.TxAiHandler;
import com.xian.cloud.utils.SpringBeanContextUtil;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        TxAiHandler txAiHandler = SpringBeanContextUtil.getBean(TxAiHandler.class);
        //机器人
        String answer = txAiHandler.answer(wxMessage.getContent(), wxMessage.getMsgId().toString());

        return new TextBuilder().build(answer, wxMessage, weixinService);

    }

}
