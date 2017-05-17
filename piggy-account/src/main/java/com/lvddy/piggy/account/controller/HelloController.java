package com.lvddy.piggy.account.controller;

import com.lvddy.piggy.account.constant.UrlConstants;

import com.netflix.eureka.resources.ApplicationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/13 0013.
 */
@RestController
public class HelloController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    /**
     * 获取注册服务的信息
     * @return
     */
    @GetMapping(UrlConstants.INDEX)
    public Map index(){
        List<String> services = discoveryClient.getServices();
        final Map<String,List<String>> result = new HashMap<String,List<String>>();

        services.forEach(s->{
            List<ServiceInstance> instances = discoveryClient.getInstances(s);
            instances.forEach(i ->{
                List<String> data = result.get(i.getServiceId());
                if(data !=null){
                    data.add(i.getMetadata().toString());
                }else{
                    data = new ArrayList<>();
                    data.add(i.getMetadata().toString());
                }

            });
        });
        System.out.println(services.toString());
        return result;
    }

    @GetMapping("/hello")
    public String hello(){
        ResponseEntity<String> result = restTemplate.getForEntity("http://piggy-service/hello", String.class);
        String hello = restTemplate.getForObject("http://piggy-service/hello", String.class);
        LoadBalancerClient client;
        LoadBalancerAutoConfiguration configuration;
        String body = result.getBody();
        LoadBalancerInterceptor interceptor;
        return body;
    }

}
