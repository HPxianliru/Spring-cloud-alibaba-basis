package com.xian.cloud.common.provider;

import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.BlockResponse;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.ZuulBlockFallbackProvider;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * <Description>
 *
 * @author xianliru@100tal.com
 * @version 1.0
 * @createDate 2019/10/31 15:25
 */

public class BlockFallbackProvider implements ZuulBlockFallbackProvider {

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public BlockResponse fallbackResponse(String s, Throwable throwable) {
        RecordLog.info(String.format("[Sentinel DefaultBlockFallbackProvider] Run fallback route: %s", s));
        if (throwable instanceof BlockException) {
            return new BlockResponse(429, "the route is blocked", s);
        } else {
            return new BlockResponse(500, "System Error", s);
        }
    }
}
