package com.xian.cloud.runner;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/12 11:18
 */
@Component
@Slf4j
public class FlowRuleRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            List<FlowRule> rules = FlowRuleManager.getRules();
            if(rules == null ){
                rules = new ArrayList<>();
            }
            FlowRule flowRule = new FlowRule("/refreshRoutes");
            flowRule.setCount(5).setClusterMode(false).setControlBehavior(0);
            flowRule.setLimitApp("default");
            rules.add(flowRule);
            log.info("FlowRuleRunner loadRules reules:{}  ",rules);
            FlowRuleManager.loadRules(rules);
        }catch (Exception e){
            log.error("FlowRuleRunner 加载异常 :{}",e.getMessage());
        }
    }
}
