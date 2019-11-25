package com.xian.common.config.filter;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.loadbalancer.Server;
import com.xian.common.rule.GrayscaleConstant;
import com.xian.common.rule.GrayscaleProperties;
import org.springframework.cloud.netflix.ribbon.ZonePreferenceServerListFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <Description> 指定自定义fegin的过滤器
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/25 10:23
 */

public class GrayscaleProServerListFilter extends ZonePreferenceServerListFilter {

    private GrayscaleProperties grayscaleEntity;

    public GrayscaleProServerListFilter(GrayscaleProperties grayscaleEntity ){
        this.grayscaleEntity = grayscaleEntity;
    }

    @Override
    public List<Server> getFilteredListOfServers(List<Server> servers) {
        List<Server> filteredListOfServers = super.getFilteredListOfServers(servers);
        String version = grayscaleEntity.getVersion();
        String serverName = grayscaleEntity.getServerName();
        List<Server> list = new ArrayList<>();
        filteredListOfServers.stream().forEach(server -> {

            NacosServer nacosServer = (NacosServer) server;
            Map<String, String> metadata = nacosServer.getMetadata();
            if(version.equals(metadata.get(GrayscaleConstant.GRAYSCALE_VERSION)) &&  nacosServer.getInstance().getServiceName().equals(serverName)){
                if(nacosServer.getInstance().getClusterName().equals(grayscaleEntity.getServerGroup())){
                    list.add(nacosServer);
                }
            }
        });

        return list;
    }
}
