package com.imooc.house.mapper;

import com.imooc.house.common.PageParams;
import com.imooc.house.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<UserEntity> queryUsers();

    Integer insert(UserEntity user);

    Integer delete(String email);

    Integer updateByEmail(UserEntity updateUser);

    List<UserEntity> selectUsersByQuery(UserEntity user);

    List<UserEntity> queryPageUsers(@Param("user") UserEntity userEntity, @Param("page") PageParams pageParams);

    Long queryUserCount(@Param("user") UserEntity userEntity);
}
