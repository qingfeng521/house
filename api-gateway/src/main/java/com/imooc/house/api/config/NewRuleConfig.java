package com.imooc.house.api.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class NewRuleConfig {

    @Autowired
    private IClientConfig ribbonClientConfig;

    @Bean
    public IPing ribbonPing(IClientConfig config){
        return new PingUrl(false,"/health");
    }

    @Bean
    public IRule ribbonRule(IClientConfig config){
        return new RandomRule();
        //return new AvailabilityFilteringRule();
    }

}
