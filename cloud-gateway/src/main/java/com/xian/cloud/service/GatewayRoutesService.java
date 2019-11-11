package com.xian.cloud.service;

import com.xian.cloud.model.GatewayRoutesEntity;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

import java.util.List;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/08 16:08
 */
public interface GatewayRoutesService {

    List<GatewayRoutesEntity> findAll() throws Exception;

    String loadRouteDefinition() throws Exception;

    GatewayRoutesEntity save(GatewayRoutesEntity gatewayDefine) throws Exception;

    void deleteById(String id) throws Exception;

    boolean existsById(String id)throws Exception;

    List<PredicateDefinition> getPredicateDefinition(String predicates) ;

    List<FilterDefinition> getFilterDefinition(String filters) ;

}
