package com.lvddy.piggy.account.controller;

import com.lvddy.piggy.account.constant.UrlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    DiscoveryClient discoveryClient;

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

}
