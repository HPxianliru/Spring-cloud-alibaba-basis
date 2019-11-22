package com.xian.common.rule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/22 19:15
 */
@Slf4j
public class GrayscaleLoadBalancerRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        //留空
    }

    /**
     * gateway 特殊性。需要设置key值内容知道你要转发的服务名称。
     * @param key
     * @return
     */
    @Override
    public Server choose(Object key) {

        try {
            String clusterName = this.nacosDiscoveryProperties.getClusterName();
            NamingService namingService = this.nacosDiscoveryProperties.namingServiceInstance();
            List<Instance> instances = namingService.selectInstances(key.toString(), true);
            if (CollectionUtils.isEmpty(instances)) {
                log.warn("no instance in service {}", key);
                return null;
            } else {
                List<Instance> instancesToChoose = instances;
                if (StringUtils.isNotBlank(clusterName)) {
                    List<Instance> sameClusterInstances = (List)instances.stream().filter((instancex) -> {
                        return Objects.equals(clusterName, instancex.getClusterName());
                    }).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(sameClusterInstances)) {
                        instancesToChoose = sameClusterInstances;
                    } else {
                        log.warn("A cross-cluster call occurs，name = {}, clusterName = {}, instance = {}", new Object[]{key.toString(), clusterName, instances});
                    }
                }

                Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToChoose);
                return new NacosServer(instance);
            }
        } catch (Exception var9) {
            log.warn("NacosRule error", var9);
            return null;
        }
    }
}
