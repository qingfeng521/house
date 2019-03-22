package com.imooc.house.user_service.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GenericRest {

    @Autowired
    private RestTemplate lbRestTemplate;

    @Autowired
    private RestTemplate directTemplate;

    private static final String directFlag = "direct://";

    public <T> ResponseEntity<T> post(String url, Object reqBody, ParameterizedTypeReference<T> parameterizedTypeReference){
        RestTemplate template = getRestTemplate(url);
        url = url.replace(directFlag,"");
        return  template.exchange(url, HttpMethod.POST,new HttpEntity(reqBody),parameterizedTypeReference);
    }

    public <T> ResponseEntity<T> get(String url, Object reqBody, ParameterizedTypeReference<T> parameterizedTypeReference){
        RestTemplate template = getRestTemplate(url);
        url = url.replace(directFlag,"");
        return  template.exchange(url, HttpMethod.GET,HttpEntity.EMPTY,parameterizedTypeReference);
    }

    public RestTemplate getRestTemplate(String url){
        if(StringUtils.isBlank(url)){
            throw new RuntimeException("调用接口的url有误....");
        }
        if(url.contains(directFlag)){
            return directTemplate;
        }
        return lbRestTemplate;
    }








}
