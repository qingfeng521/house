package com.imooc.house.api.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.Arrays;


public class FastJsonHttpMessageConverterAdd extends FastJsonHttpMessageConverter {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    public FastJsonHttpMessageConverterAdd(){
        setDefaultCharset(DEFAULT_CHARSET);
        setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,new MediaType("application","*+json")));
    }

}
