package com.xian.common.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/17 2:41 下午
 */
@Slf4j
public class WatcherApi implements Watcher {

    @Override
    public void process(WatchedEvent event) {
        log.info("【Watcher监听事件】={}",event.getState());
        log.info("【监听路径为】={}",event.getPath());
        log.info("【监听的类型为】={}",event.getType()); //  三种监听类型： 创建，删除，更新

    }
}
