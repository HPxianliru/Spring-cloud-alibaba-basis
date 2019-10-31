package com.xian.cloud.common.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * <Description>
 *
 * @author xianliru@100tal.com
 * @version 1.0
 * @createDate 2019/10/31 16:24
 */
@Configuration
public class GatewayRuleConfig {

//    private static final int URL_MATCH_STRATEGY_EXACT = 0;
//    private static final int URL_MATCH_STRATEGY_PREFIX = 1;
//    private static final int URL_MATCH_STRATEGY_REGEX = 2;
//
//    @PostConstruct
//    public void doInit() {
//        // Prepare some gateway rules and API definitions (only for demo).
//        // It's recommended to leverage dynamic data source or the Sentinel dashboard to push the rules.
//        initCustomizedApis();
//        initGatewayRules();
//    }
//
//    private void initCustomizedApis() {
//        Set<ApiDefinition> definitions = new HashSet<>();
//        ApiDefinition api1 = new ApiDefinition("discovery-server")
//                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
//                    //add(new ApiPathPredicateItem().setPattern("/ahas"));
//                    add(new ApiPathPredicateItem().setPattern("/server/**")
//                            .setMatchStrategy(URL_MATCH_STRATEGY_PREFIX));
//                }});
//        //ApiDefinition api2 = new ApiDefinition("another_customized_api")
//        //        .setPredicateItems(new HashSet<ApiPredicateItem>() {{
//        //            add(new ApiPathPredicateItem().setPattern("/**")
//        //                    .setMatchStrategy(URL_MATCH_STRATEGY_PREFIX));
//        //        }});
//        definitions.add(api1);
//        //definitions.add(api2);
//        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
//    }
//
//    private void initGatewayRules() {
//        Set<GatewayFlowRule> rules = new HashSet<>();
//        rules.add(new GatewayFlowRule("/server/**")
//                .setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME)
//                .setCount(1)
//                .setIntervalSec(1)
//        );
//        rules.add(new GatewayFlowRule("/client/**")
//                .setCount(2)
//                .setIntervalSec(2)
//                //应对突发请求时额外允许的请求数目。
//                .setBurst(2)
//                .setParamItem(new GatewayParamFlowItem()
//                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_CLIENT_IP)
//                )
//        );
//        rules.add(new GatewayFlowRule("/**")
//                .setCount(10)
//                //统计时间窗口，单位是秒，默认是 1 秒。
//                .setIntervalSec(1)
//                //流量整形的控制效果，同限流规则的 controlBehavior 字段，目前支持快速失败和匀速排队两种模式，默认是快速失败。
//                .setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER)
//                //匀速排队模式下的最长排队时间，单位是毫秒，仅在匀速排队模式下生效。
//                .setMaxQueueingTimeoutMs(600)
//                //参数限流配置
//                .setParamItem(new GatewayParamFlowItem()
//                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_HEADER)
//                        .setFieldName("X-Sentinel-Flag")
//                )
//        );
//        rules.add(new GatewayFlowRule("another-route-httpbin")
//                .setCount(1)
//                .setIntervalSec(1)
//                .setParamItem(new GatewayParamFlowItem()
//                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM)
//                        .setFieldName("pa")
//                )
//        );
//
//        rules.add(new GatewayFlowRule("some_customized_api")
//                .setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME)
//                .setCount(5)
//                .setIntervalSec(1)
//                .setParamItem(new GatewayParamFlowItem()
//                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM)
//                        .setFieldName("pn")
//                )
//        );
//        GatewayRuleManager.loadRules(rules);
//
//        //监听zookeeper，使用zookeeper的规则
//        //ReadableDataSource<String, Set<GatewayFlowRule>> flowRuleDataSource = new ZookeeperDataSource<>(null, null,
//        //        source -> JSON.parseObject(source, new TypeReference<Set<GatewayFlowRule>>() {
//        //        }));
//        //GatewayRuleManager.register2Property(flowRuleDataSource.getProperty());
//    }
}
