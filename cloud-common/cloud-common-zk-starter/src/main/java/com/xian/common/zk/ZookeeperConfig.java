package com.xian.common.zk;

import com.xian.common.zk.service.ZkApi;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/17 2:15 下午
 */
@Configuration
@Slf4j
@ConditionalOnProperty(name = "zookeeper.enable",havingValue="true")
@ConfigurationProperties(prefix = "zookeeper")
@Data
public class ZookeeperConfig {

    private  String address;

    private Integer timeout;

    @Bean(name = "zkClient")
    public ZooKeeper zkClient(){
        log.info("ZookeeperConfig init......");
        ZooKeeper zooKeeper=null;
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            //连接成功后，会回调watcher监听，此连接操作是异步的，执行完new语句后，直接调用后续代码
            //  可指定多台服务地址 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
            zooKeeper = new ZooKeeper(address, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if(Event.KeeperState.SyncConnected==event.getState()){
                        //如果收到了服务端的响应事件,连接成功
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            log.info("【初始化ZooKeeper连接状态....】={}",zooKeeper.getState());

        }catch (Exception e){
            log.error("初始化ZooKeeper连接异常....】={}",e);
        }
        return  zooKeeper;
    }

    @Bean("zkApi")
    ZkApi zkApi(ZooKeeper zooKeeper){
        ZkApi zk = new ZkApi(zooKeeper);
        return zk;
    }
}
