package com.xian.common.rule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <Description> 自定义负载规则。根据version做请求的转发。同时整合nacos 控制台的随机权重设置。
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/22 19:15
 */
@Slf4j
public class GrayscaleGatewayLoadBalancerRule extends AbstractGrayscalLoadBalancerRule {

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
            GrayscaleProperties grayscale = (GrayscaleProperties) key;
            String version = grayscale.getVersion();
            String clusterName = this.nacosDiscoveryProperties.getClusterName();
            NamingService namingService = this.nacosDiscoveryProperties.namingServiceInstance();
            List<Instance> instances = namingService.selectInstances(grayscale.getServerName(), true);

            if (CollectionUtils.isEmpty(instances)) {
                log.warn("no instance in service {}", grayscale.getServerName());
                return null;
            } else {
                List<Instance> instancesToChoose = buildVersion(instances,version);
                //进行cluster-name分组筛选
                // TODO 思考如果cluster-name 节点全部挂掉。是不是可以请求其他的分组的服务？可以根据情况在定制一份规则出来
                if (StringUtils.isNotBlank(clusterName)) {
                    List<Instance> sameClusterInstances = (List)instancesToChoose.stream().filter((instancex) -> {
                        return Objects.equals(clusterName, instancex.getClusterName());
                    }).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(sameClusterInstances)) {
                        instancesToChoose = sameClusterInstances;
                    } else {
                        log.warn("A cross-cluster call occurs，name = {}, clusterName = {}, instance = {}", new Object[]{grayscale.getServerName(), clusterName, instances});
                    }
                }
                //按随机nacos权重获取
                Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToChoose);
                return new NacosServer(instance);
            }
        } catch (Exception var9) {
            log.warn("NacosRule error", var9);
            return null;
        }
    }
    
}
