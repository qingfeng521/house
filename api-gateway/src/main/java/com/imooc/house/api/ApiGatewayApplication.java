package com.imooc.house.api;

import com.imooc.house.api.config.NewRuleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@Controller
@ComponentScan({"com.imooc.house.api.*"})
//@RibbonClient(name="user",configuration= NewRuleConfig.class)
public class ApiGatewayApplication {

    @Autowired
    private DiscoveryClient discoveryClient;


    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }


    @RequestMapping("index1")
    @ResponseBody
    public List<ServiceInstance> getRegister(){
        return discoveryClient.getInstances("user");
    }

}
