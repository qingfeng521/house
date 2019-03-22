package com.imooc.house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

@Controller("hello")
public class HelloController {
    @Autowired
    private JedisCluster jedisCluster;
    @RequestMapping("/indexs")
    public String goIndex(){
        return  "";
    }
}
