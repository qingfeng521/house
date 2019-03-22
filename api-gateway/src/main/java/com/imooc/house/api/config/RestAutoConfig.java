package com.imooc.house.api.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.apache.http.client.HttpClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import sun.nio.cs.FastCharsetProvider;

import java.nio.charset.Charset;

@Configuration
public class RestAutoConfig {


    @Bean
    @LoadBalanced
    public RestTemplate lbRestTemplate(HttpClient httpClient){
        RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

        template.getMessageConverters().add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));
        template.getMessageConverters().add(1,new FastJsonHttpMessageConverterAdd());
        return template;
    }

    @Bean
    public RestTemplate directLbRestTemplate(HttpClient httpClient){
        RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
        template.getMessageConverters().add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));
        template.getMessageConverters().add(1,new FastJsonHttpMessageConverterAdd());
        return template;
    }



}
