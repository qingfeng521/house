package com.imooc.house.mapper;

import com.imooc.house.entity.HouseMsgEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HouseMsgMapper {
    void insert(HouseMsgEntity houseMsgEntity);

    List<HouseMsgEntity> queryByEntity(HouseMsgEntity houseMsgEntity);
}
