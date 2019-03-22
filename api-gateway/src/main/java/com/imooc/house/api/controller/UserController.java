package com.imooc.house.api.controller;


import com.imooc.house.api.common.RestResponse;
import com.imooc.house.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @RequestMapping("test/getUserName")
    public RestResponse<String> getUserNameById(Long id){
        return RestResponse.success(userService.getUserNameById(id));
    }



}
