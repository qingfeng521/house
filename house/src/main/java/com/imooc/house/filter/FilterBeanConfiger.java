package com.imooc.house.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FilterBeanConfiger {

    @Bean
    public FilterRegistrationBean logFilter(){
         FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
         filterRegistrationBean.setFilter(new LogerFilter());
         List<String> urlList = new ArrayList<String>();
         urlList.add("*");
         filterRegistrationBean.setUrlPatterns(urlList);
         return filterRegistrationBean;
    }
}
