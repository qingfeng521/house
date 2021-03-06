package com.imooc.house.api.config;

import com.imooc.house.api.configEntity.HttpClientEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.httpclient.LogbookHttpRequestInterceptor;
import org.zalando.logbook.httpclient.LogbookHttpResponseInterceptor;


@Configuration
@ConditionalOnClass({HttpClient.class})
@EnableConfigurationProperties(HttpClientEntity.class)
public class HttpClientAutoConfigeration {

    @Autowired
    public HttpClientEntity httpClientEntity;

    @Autowired
    private LogbookHttpRequestInterceptor logbookHttpRequestInterceptor;
    @Autowired
    private LogbookHttpResponseInterceptor logbookHttpResponseInterceptor;

    public HttpClientAutoConfigeration(HttpClientEntity httpClientEntity) {
        this.httpClientEntity = httpClientEntity;
    }

    public HttpClientAutoConfigeration() {
        this.httpClientEntity = httpClientEntity;
    }

    @Bean
    @ConditionalOnMissingBean(HttpClient.class)
    public HttpClient httpClient(){
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(httpClientEntity.getConnectTimeOut())
                .setSocketTimeout(httpClientEntity.getSocketTimeOut()).build();     // 构建requestConfig
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setUserAgent(httpClientEntity.getAgent())
                .setMaxConnPerRoute(httpClientEntity.getMaxConnPerRoute()).setMaxConnTotal(httpClientEntity.getMaxConnTotaol())
                .addInterceptorFirst(logbookHttpRequestInterceptor)
                .addInterceptorFirst(logbookHttpResponseInterceptor)
                .build();
        return client;
    }
}
