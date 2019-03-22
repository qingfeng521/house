package com.imooc.house.service;

import com.imooc.house.Constants;
import com.imooc.house.common.PageData;
import com.imooc.house.common.PageParams;
import com.imooc.house.entity.AgencyEntity;
import com.imooc.house.entity.HouseEntity;
import com.imooc.house.entity.HouseMsgEntity;
import com.imooc.house.entity.UserEntity;
import com.imooc.house.mapper.AgencyMapper;
import com.imooc.house.mapper.HouseMsgMapper;
import com.imooc.house.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgencyService {

    @Autowired
    private AgencyMapper agencyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HouseMsgMapper houseMsgMapper;
    @Autowired
    private HouseService houseService;

    public AgencyEntity queryEntityByHouseId(Long houseId) {
        List<AgencyEntity> agencyEntityList = agencyMapper.queryEntityByHouseId(houseId);
        return null;
    }

    public UserEntity getAgencyDetail(Long userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setType(2);
        userEntity.setEnable(1);
        List<UserEntity> userEntityList = userMapper.selectUsersByQuery(userEntity);
        if(userEntityList.isEmpty()){
            return null;
        }
        return setImage(userEntityList).get(0);
    }

    private List<UserEntity> setImage(List<UserEntity> list){
        list.forEach(u -> {
            u.setAvatar(Constants.FILE_URL + u.getAvatar());
        });
        return list;
    }
    public PageData<UserEntity> getAllAgent(PageParams pageParams) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEnable(1);
        userEntity.setType(2);
        List<UserEntity> userEntityList = userMapper.queryPageUsers(userEntity,pageParams);
        userEntityList = setImage(userEntityList);
        Long userCount = userMapper.queryUserCount(userEntity);
        return PageData.buildPage(userEntityList,userCount,pageParams.getPageNum(),pageParams.getPageSize());
    }

    public PageData<HouseEntity> queryHousesByAgentId(Long id) {
        HouseMsgEntity houseMsgEntity = new HouseMsgEntity();
        houseMsgEntity.setAgentId(id);
        List<HouseMsgEntity> houseMsgEntityList = houseMsgMapper.queryByEntity(houseMsgEntity);
        List<Long> ids = houseMsgEntityList.stream().map(h->h.getHouseId()).collect(Collectors.toList());
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setType(1);
        houseEntity.setIds(ids);
        PageData<HouseEntity> ps = houseService.queryPageList(houseEntity,PageParams.build(3,1));
        return ps;
    }
}
