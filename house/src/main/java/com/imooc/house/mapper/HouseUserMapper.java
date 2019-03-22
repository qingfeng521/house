package com.imooc.house.mapper;

import com.imooc.house.entity.HouseEntity;
import com.imooc.house.entity.HouseUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseUserMapper {
    List<HouseUserEntity> queryByEntity(HouseUserEntity houserUserEntity);

    void insert(HouseUserEntity houserUserEntity);

    void deleteByEntity(HouseUserEntity houseUserEntity);
}
