package com.imooc.house.mapper;

import com.imooc.house.entity.HouseMsgEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseMsgMapper {
    void insert(HouseMsgEntity houseMsgEntity);
    List<HouseMsgEntity> queryByEntity(HouseMsgEntity houseMsgEntity);
}
