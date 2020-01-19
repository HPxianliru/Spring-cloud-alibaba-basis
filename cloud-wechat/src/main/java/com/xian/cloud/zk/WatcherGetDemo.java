package com.xian.cloud.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/19 10:44 上午
 */
@Slf4j
public class WatcherGetDemo implements Watcher {

    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info("watch {}" ,watchedEvent);
    }
}
