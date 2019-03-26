package com.imooc.house.api.dao;

import com.imooc.house.api.common.RestResponse;
import com.imooc.house.api.config.GenericRest;
import com.imooc.house.api.entity.UserEntity;
import com.imooc.house.api.util.RestUtils;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    private GenericRest genericRest;
    @Autowired
    private RestUtils restUtils;

    public String getUserNameById(Long id){
        //RestUtils restUtils = new RestUtils();
        String orgUrl = "http://user/getUserName?id=" + id;
        String result = genericRest.get(orgUrl, new ParameterizedTypeReference<RestResponse<String>>() {}).getBody().getResult();


        String url = "/getUserName?id="+id;
        return genericRest.resultGet("user",url, new ParameterizedTypeReference<RestResponse<String>>() {});
    }

    public UserEntity getUserByToken(String token){
        String url = "/getUserByToken?token="+token;
        return genericRest.resultGet("user",url, new ParameterizedTypeReference<RestResponse<UserEntity>>() {});
    }


}
