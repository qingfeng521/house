package com.imooc.house.mapper;

import com.imooc.house.entity.AgencyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgencyMapper {

    List<AgencyEntity> queryEntityByHouseId(Long houseId);
}
