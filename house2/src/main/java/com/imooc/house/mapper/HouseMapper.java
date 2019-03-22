package com.imooc.house.mapper;

import com.imooc.house.common.PageParams;
import com.imooc.house.entity.CityEntity;
import com.imooc.house.entity.Community;
import com.imooc.house.entity.HouseEntity;
import com.imooc.house.entity.HouseUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseMapper {
    List<HouseEntity> queryPageList(@Param("house")HouseEntity houseEntity, @Param("page")PageParams pageParams);
    Integer insert(HouseEntity house);
    Long queryPageCount(@Param("house") HouseEntity houseEntity);
    List<Community> queryCommunity(Community community);

    List<CityEntity> queryCityList();

    HouseEntity queryEntityById(Long id);

    void updateById(HouseEntity houseEntity);
}
