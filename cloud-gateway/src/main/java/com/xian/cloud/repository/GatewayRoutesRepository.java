package com.xian.cloud.repository;

import com.alibaba.fastjson.JSON;
import com.xian.cloud.model.GatewayRoutesEntity;
import com.xian.cloud.service.GatewayRoutesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/08 16:59
 */
@Slf4j
public class GatewayRoutesRepository  implements RouteDefinitionRepository {

    @Autowired
    private GatewayRoutesService gatewayRoutesService;

    @Autowired
    private GatewayProperties gatewayProperties;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        try {
            Map<String, RouteDefinition> routes = new LinkedHashMap<String, RouteDefinition>();
            List<RouteDefinition> yamlRoutes = gatewayProperties.getRoutes();

            for (RouteDefinition yamlRoute : yamlRoutes) {
                log.warn( "yamlRoute : {}", yamlRoute);
                routes.put( yamlRoute.getId(),yamlRoute );
            }
            List<GatewayRoutesEntity> gatewayDefineList = gatewayRoutesService.findAll();
            for (GatewayRoutesEntity gatewayDefine: gatewayDefineList) {
                if(gatewayDefine.getValid().equals(1)){
                    RouteDefinition definition = new RouteDefinition();
                    definition.setId(gatewayDefine.getServiceId());
                    definition.setUri(new URI(gatewayDefine.getUri()));
                    List<PredicateDefinition> predicateDefinitions = gatewayRoutesService.getPredicateDefinition(gatewayDefine.getPredicates());
                    if (predicateDefinitions != null) {
                        definition.setPredicates(predicateDefinitions);
                    }
                    List<FilterDefinition> filterDefinitions = gatewayRoutesService.getFilterDefinition(gatewayDefine.getFilters());
                    if (filterDefinitions != null) {
                        definition.setFilters(filterDefinitions);
                    }
                    routes.put(definition.getId(), definition);
                }
            }
            return Flux.fromIterable(routes.values());
        } catch (Exception e) {
            e.printStackTrace();
            return Flux.empty();
        }
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            try {
                GatewayRoutesEntity gatewayDefine = new GatewayRoutesEntity();
                gatewayDefine.setServiceId( r.getId());
                gatewayDefine.setUri(r.getUri().toString());
                gatewayDefine.setPredicates( JSON.toJSONString(r.getPredicates()));
                gatewayDefine.setFilters(JSON.toJSONString(r.getFilters()));
                gatewayDefine.setValid(1);
                gatewayRoutesService.save(gatewayDefine);
                return Mono.empty();

            } catch (Exception e) {
                e.printStackTrace();
                return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition save error: "+ r.getId())));
            }

        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            try {
                gatewayRoutesService.deleteById(id);
                return Mono.empty();

            } catch (Exception e) {
                e.printStackTrace();
                return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition delete error: " + routeId)));
            }
        });
    }
}