package com.xian.cloud.controller;

import com.xian.cloud.event.DynamicRouteServiceImpl;
import com.xian.cloud.event.RefreshRouteService;
import com.xian.cloud.model.RestResult;
import com.xian.cloud.model.RestResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.*;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/08 17:18
 */
@RestController
@RequestMapping("/gateway")
public class GatewayRoutesController {

    @Autowired
    private RefreshRouteService refreshRouteService;

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    @GetMapping("/refreshRoutes")
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



}
