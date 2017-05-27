package com.lvddy.snowflake.config;

import lombok.extern.log4j.Log4j2;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kcz-020 on 2017/5/24.
 */
@Configuration
@Log4j2
public class ZookeeperConfig {

    @Value("${zookeeper.connection}")
    private String connection;

    @Value("${zookeeper.timeout}")
    private String timeout;

    @Bean
    public ZooKeeper zooKeeper() throws Exception{
        System.out.println(timeout);
        return new ZooKeeper(connection, Integer.valueOf(timeout), (event)->{
                log.info("连接zookeeper成功");
        });
    }

}
