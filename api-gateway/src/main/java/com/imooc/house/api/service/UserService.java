package com.imooc.house.api.service;

import com.imooc.house.api.dao.UserDao;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public String getUserNameById(Long id){
        return userDao.getUserNameById(id);
    }

}
