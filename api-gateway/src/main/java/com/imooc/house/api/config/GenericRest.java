package com.imooc.house.api.config;

import com.imooc.house.api.common.RestResponse;
import com.imooc.house.api.util.RestUtils;
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


    public <T> T  resultGet(String serviceName, String path,ParameterizedTypeReference<RestResponse<T>> param){
        /*Callable<RestResponse<T>> callable = new Callable() {
            @Override
            public Object call() throws Exception {
                String url = toUrl(serviceName,path);
                ResponseEntity<RestResponse<T>> responseEntity =
                        genericRest.get(url,param);
                return responseEntity.getBody();
            }
        };
       return RestUtils.exe(callable).getResult();*/
        // return RestUtils.exe(callable)
        RestResponse<T> restResponse = RestUtils.exe(()->{
            String url = RestUtils.toUrl(serviceName,path);
            ResponseEntity<RestResponse<T>> responseEntity =
                    get(url,param);
            return responseEntity.getBody();
        });
        if(restResponse == null || restResponse.getCode()!=0){
            return null;
        }
        return restResponse.getResult();
    }

    public <T> T  resultPost(String serviceName, String path,Object reqBody,ParameterizedTypeReference<RestResponse<T>> param){
        RestResponse<T> restResponse =  RestUtils.exe(()->{
            String url = RestUtils.toUrl(serviceName,path);
            ResponseEntity<RestResponse<T>> responseEntity =
                    post(url,reqBody,param);
            return responseEntity.getBody();
        });
        if(restResponse == null || restResponse.getCode()!=0){
            return null;
        }
        return restResponse.getResult();
    }








}
