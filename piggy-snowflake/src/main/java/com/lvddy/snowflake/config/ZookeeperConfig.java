package com.lvddy.snowflake.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kcz-020 on 2017/5/24.
 */
@Configuration
public class ZookeeperConfig {

    @Value("zookeeper.connection")
    private String connection;

    @Value("zookeeper.timeout")
    private int timeout;

    @Bean
    public ZooKeeper zooKeeper() throws Exception{
        return new ZooKeeper(connection, timeout, (event)->{
                System.out.println("已经触发了" + event.getType() + "事件！");
        });
    }

}
