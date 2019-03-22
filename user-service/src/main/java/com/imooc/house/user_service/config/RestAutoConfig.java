package com.imooc.house.user_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import java.nio.charset.Charset;

public class RestAutoConfig {


    @Bean
    @LoadBalanced
    public RestTemplate lbRestTemplate(HttpClient httpClient){
        RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        template.getMessageConverters().add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));
        template.getMessageConverters().add(1,new FastJsonHttpMessageConverterAdd());
        return template;
    }

    @Bean
    public RestTemplate directLbRestTemplate(HttpClient httpClient){
        RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        template.getMessageConverters().add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));
        template.getMessageConverters().add(1,new FastJsonHttpMessageConverterAdd());
        return template;
    }



}
