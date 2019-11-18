package com.xian.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xian.cloud.common.handler.DiscoveryClientControllerBackHandler;
import com.xian.cloud.common.handler.DiscoveryClientControllerFallBackHandler;
import com.xian.cloud.core.UserService;
import com.xian.cloud.core.impl.UserServiceImpl;
import com.xian.cloud.entity.UserEntity;
import com.xian.cloud.fegin.FeginConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: xlr
 * @Date: Created in 3:04 PM 2019/10/27
 */
@RequestMapping("client")
@RestController
@Slf4j
public class DiscoveryClientController {

    //服务提供者 项目名称 spring.application.name
    public static final String CLOUD_DISCOVERY_SERVER = "cloud-discovery-server";

    @Autowired
    private UserService userService;

    @Autowired
    private FeginConfig.ServerService serverService;

    /**
     * 在Spring Cloud Commons中提供了大量的与服务治理相关的抽象接口，包括DiscoveryClient、LoadBalancerClient等。
     * 从LoadBalancerClient接口的命名中，是一个负载均衡客户端的抽象定义
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @SentinelResource(
            value = "client:test",
            blockHandler = "defaultMessage",
            fallback = "defaultMessage",
            blockHandlerClass = DiscoveryClientControllerBackHandler.class,
            fallbackClass = DiscoveryClientControllerFallBackHandler.class
    )
    public String test() {
        ServiceInstance serviceInstance = loadBalancerClient.choose(CLOUD_DISCOVERY_SERVER);
        log.info( "ServiceInstance :{}",serviceInstance );
        String url = serviceInstance.getUri() + "/server/hello?name=" + "tom";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return "调用 " + url + ", 返回 : " + result;
        
    }

    @SentinelResource(
            value = "client:fegin:test",
            blockHandler = "defaultMessage",
            fallback = "defaultMessage",
            blockHandlerClass = DiscoveryClientControllerBackHandler.class,
            fallbackClass = DiscoveryClientControllerFallBackHandler.class
    )
    @RequestMapping(value = "fegin/test",method = RequestMethod.GET)
    public String feginTest() {
        String result = serverService.hello( "fegin" );
        return  " 返回 : " + result;
    }
    

    @GetMapping("/log/save")
    @SentinelResource(
            value = "client/log/save",
            blockHandler = "defaultMessage",
            fallback = "defaultMessage",
            blockHandlerClass = DiscoveryClientControllerBackHandler.class,
            fallbackClass = DiscoveryClientControllerFallBackHandler.class
    )
    public String save(){
        UserEntity entity = new UserEntity();
        entity.setUsername("tom");
        entity.setPassWord("1232131");
        entity.setEmail("222@qq.com");
        userService.saveTx(entity);
        return "success";
    }

    @GetMapping("/log/update")
    @Transactional
    public String update(@RequestParam("id")  Long Id){

        UserEntity entity = userService.getById(Id);
        entity.setEmail("client");
        boolean save = userService.updateById(entity);
        if(save){
            boolean update = serverService.update(entity);
        }
        throw new RuntimeException("执行失败");
    }


    @GetMapping("user/service/save")
    public String userServiceSaveTx(){
        UserEntity entity = new UserEntity();
        String result = userService.saveTx( entity );
        return result;
    }

}
