package com.xian.common.rule;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @Author: xlr
 * @Date: Created in 1:03 PM 2019/11/24
 */
@Slf4j
@Data
public abstract class GrayscalAbstractLoadBalancerRule extends AbstractLoadBalancerRule {

    /**
     * asc 正序 反之desc 倒叙
     */
    private boolean asc = true;

    /**
     * 筛选想要的值
     * @param instances
     * @param version
     * @return
     */
    protected List <Instance> buildVersion(List<Instance> instances,String version){
        //进行按版本分组排序
        Map<String,List<Instance>> versionMap = getInstanceByScreen(instances);

        if(versionMap.isEmpty()){
            log.warn("no instance in service {}", version);
        }
        //如果version 未传值使用最低版本服务
        if(StringUtils.isBlank( version )){
            if(isAsc()){
                version = getFirst( versionMap.keySet() );
            }else {
                version = getLast( versionMap.keySet() );
            }
        }

        List <Instance> instanceList = versionMap.get( version );

        return instanceList;
    }

    /**
     * 根据version 组装一个map key value  对应 version List<Instance>
     * @param instances
     * @return
     */
    protected Map<String,List<Instance>> getInstanceByScreen(List<Instance> instances){

        Map<String,List<Instance>> versionMap = new HashMap<>( instances.size() );
        instances.stream().forEach( instance -> {
            String version = instance.getMetadata().get( GrayscaleConstant.GRAYSCALE_VERSION );
            List <Instance> versions = versionMap.get( version );
            if(versions == null){
                versions = new ArrayList<>(  );
            }
            versions.add( instance );
            versionMap.put( version,versions );
        } );
        return versionMap;
    }

    /**
     * 获取第一个值
     * @param keys
     * @return
     */
    protected String getFirst(Set<String> keys){
        List <String> list = sortVersion( keys );
        return list.get( 0 );
    }

    /**
     * 获取最后一个值
     * @param keys
     * @return
     */
    protected String getLast(Set <String> keys){
        List <String> list = sortVersion( keys );
        return list.get( list.size()-1 );
    }

    /**
     * 根据版本排序
     * @param keys
     * @return
     */
    protected List<String > sortVersion(Set <String> keys){
        List<String > list = new ArrayList <>( keys );
        Collections.sort(list);
        return list;
    }
}
