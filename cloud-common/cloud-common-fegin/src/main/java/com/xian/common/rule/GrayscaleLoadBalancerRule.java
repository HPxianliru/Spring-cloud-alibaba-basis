package com.xian.common.rule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import com.xian.common.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * fegin 负载均衡。在获取到我们想设置的对象之后，我们还可以设置 服务、用户、角色等各个维度的黑白名单，限制、转发、等策略，具体的使用场景还得需要结合工作中的实际使用场景。
 * 这里只是提供一个简单的思路。希望看到这个注释的人。能够有举一反三的能力，定制自己的规则。
 * @Author: xlr
 * @Date: Created in 12:19 PM 2019/11/24
 */
@Slf4j
public class GrayscaleLoadBalancerRule extends AbstractGrayscalLoadBalancerRule {

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
        log.info("GrayscaleLoadBalancerRule 执行 choose方法 ，参数 key: {}",key);
        try {
            String clusterName = this.nacosDiscoveryProperties.getClusterName();
            DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer)this.getLoadBalancer();
            String name = loadBalancer.getName();
            NamingService namingService = this.nacosDiscoveryProperties.namingServiceInstance();
            List<Instance> instances = namingService.selectInstances(name, true);

            if (CollectionUtils.isEmpty(instances)) {
                log.warn("no instance in service {}", name);
                return null;
            } else {
                List<Instance> instancesToChoose = null;

                String version = (String) ThreadLocalUtils.getKey( GrayscaleConstant.GRAYSCALE_VERSION );

                List <Instance> instanceList = buildVersion( instances,version );

                if (StringUtils.isNotBlank(clusterName)) {
                    List<Instance> sameClusterInstances = (List)instanceList.stream().filter((instancex) -> {
                        return Objects.equals(clusterName, instancex.getClusterName());
                    }).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(sameClusterInstances)) {
                        instancesToChoose = sameClusterInstances;
                    } else {
                        log.warn("A cross-cluster call occurs，name = {}, clusterName = {}, instance = {}", new Object[]{name, clusterName, instanceList});
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
