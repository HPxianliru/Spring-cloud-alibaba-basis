package com.xian.cloud.controller;

import com.xian.cloud.event.RefreshRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <Description> 手动刷新对外接口
 *
 * @author xianliru@100tal.com
 * @version 1.0
 * @createDate 2019/10/30 20:23
 */
@RestController
public class RefreshController {

    @Autowired
    private RefreshRouteService refreshRouteService;

    @GetMapping("/refresh")
    public String refresh() {
        refreshRouteService.refreshRoute();
        return "refresh";
    }

}
