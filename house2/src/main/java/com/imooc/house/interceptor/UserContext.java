package com.imooc.house.interceptor;

import com.imooc.house.entity.UserEntity;

public class UserContext {
    private static final ThreadLocal<UserEntity> USER_LOCAL = new ThreadLocal<UserEntity>();

    public static void setUser(UserEntity user){
        USER_LOCAL.set(user);
    }
    public static void remove(){
        USER_LOCAL.remove();
    }

    public static UserEntity getUser(){
        return USER_LOCAL.get();
    }

}
