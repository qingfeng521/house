package com.imooc.house.user_service.controller;


import com.imooc.house.user_service.common.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Value("${server.port}")
    private String port ;
    @RequestMapping("getUserName")
    public RestResponse<String> getUserNameById(String id){
        LOGGER.info("Incomming getUserNameById  and port=" + port);
        return RestResponse.success("test-ok and port=" + port);
    }



}
