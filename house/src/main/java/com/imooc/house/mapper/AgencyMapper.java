package com.imooc.house.mapper;

import com.imooc.house.entity.AgencyEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgencyMapper {

    List<AgencyEntity> queryEntityByHouseId(Long houseId);
}
