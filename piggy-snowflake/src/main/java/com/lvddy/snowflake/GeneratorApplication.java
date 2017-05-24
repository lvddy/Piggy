package com.lvddy.snowflake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by kcz-020 on 2017/5/23.
 */
@SpringBootApplication
@EnableEurekaClient
public class GeneratorApplication {


    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }
}
