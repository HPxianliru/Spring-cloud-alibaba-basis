package com.xian.cloud.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.xian.cloud.event.DynamicRouteServiceImpl;
import com.xian.cloud.event.RefreshRouteService;
import com.xian.cloud.model.RestResult;
import com.xian.cloud.model.RestResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/08 17:18
 */
@RestController
@RequestMapping("/gateway")
@Slf4j
public class GatewayRoutesController {

    @Autowired
    private RefreshRouteService refreshRouteService;

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    @GetMapping("/refreshRoutes")
    @SentinelResource("refreshRoutes")
    public RestResult refreshRoutes(){
        refreshRouteService.refreshRoutes();
        return RestResultBuilder.builder().success().build();
    }

    /**
     *
     * @param definition
     * @return
     */
    @RequestMapping(value = "routes/add",method = RequestMethod.POST)
    public RestResult add(@RequestBody RouteDefinition definition){
        boolean flag = dynamicRouteService.add(definition);
        if(flag){
            return RestResultBuilder.builder().success().build();
        }
        return RestResultBuilder.builder().failure().build();
    }


    /**
     *
     * @param definition
     * @return
     */
    @RequestMapping(value = "routes/update",method = RequestMethod.POST)
    public RestResult update(@RequestBody RouteDefinition definition){
        boolean flag = dynamicRouteService.add(definition);
        if(flag){
            return RestResultBuilder.builder().success().build();
        }
        return RestResultBuilder.builder().failure().build();
    }

    /**
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "routes/del",method = RequestMethod.POST)
    public RestResult update(@RequestParam("serviceId") String serviceId){
        boolean flag = dynamicRouteService.del(serviceId);
        if(flag){
            return RestResultBuilder.builder().success().build();
        }
        return RestResultBuilder.builder().failure().build();
    }


    /**
     *
     * @param apiName
     * @return
     */
    @GetMapping(value = "definition/add")
    @SentinelResource(value = "definition:add")
    public RestResult addApiDefinition(@RequestParam("apiName") String apiName,@RequestParam("pattern")String pattern){
        Set<ApiDefinition> definitions = new HashSet<>();
        log.info("definition/add apiName:{},pattern:{}",apiName,pattern);
        ApiDefinition api3 = new ApiDefinition(apiName)
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern(pattern));
                }});
        definitions.add(api3);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
        return RestResultBuilder.builder().success().build();
    }




    /**
     *
     * @param resource
     * @return
     */
    @GetMapping(value = "flow/rule/add")
    public RestResult addApiDefinition(@RequestParam("resource") String resource){
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(new GatewayFlowRule(resource)
                .setCount(2)
                .setIntervalSec(2)
                .setBurst(2)
                .setParamItem(new GatewayParamFlowItem()
                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_CLIENT_IP)
                )
        );
        GatewayRuleManager.loadRules(rules);
        return RestResultBuilder.builder().success().build();
    }

}
