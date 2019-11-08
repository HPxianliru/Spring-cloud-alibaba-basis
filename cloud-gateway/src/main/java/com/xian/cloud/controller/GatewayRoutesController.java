package com.xian.cloud.controller;

import com.xian.cloud.event.RefreshRouteService;
import com.xian.cloud.model.RestResult;
import com.xian.cloud.model.RestResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <Description>
 *
 * @author xianliru@100tal.com
 * @version 1.0
 * @createDate 2019/11/08 17:18
 */
@RestController
@RequestMapping("/gateway")
public class GatewayRoutesController {

    @Autowired
    private RefreshRouteService refreshRouteService;

    @GetMapping("/refreshRoutes")
    public RestResult refreshRoutes(){
        refreshRouteService.refreshRoutes();
        return RestResultBuilder.builder().success().build();
    }
}
