package com.imooc.house.api.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
@Component
public class GenericRest {

    @Autowired
    private RestTemplate lbRestTemplate;

    @Autowired
    private RestTemplate directLbRestTemplate;

    private static final String directFlag = "direct://";

    public <T> ResponseEntity<T> post(String url, Object reqBody, ParameterizedTypeReference<T> parameterizedTypeReference){
        RestTemplate template = getRestTemplate(url);
        url = url.replace(directFlag,"");
        return  template.exchange(url, HttpMethod.POST,new HttpEntity(reqBody),parameterizedTypeReference);
    }

    public <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> parameterizedTypeReference){
        RestTemplate template = getRestTemplate(url);
        url = url.replace(directFlag,"");
        return  template.exchange(url, HttpMethod.GET,HttpEntity.EMPTY,parameterizedTypeReference);
    }

    public RestTemplate getRestTemplate(String url){
        if(StringUtils.isBlank(url)){
            throw new RuntimeException("调用接口的url有误....");
        }
        if(url.contains(directFlag)){
            return directLbRestTemplate;
        }
        return lbRestTemplate;
    }








}
