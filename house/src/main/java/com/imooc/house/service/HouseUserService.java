package com.imooc.house.service;

import com.imooc.house.common.StringUtil;
import com.imooc.house.constants.HouseUserType;
import com.imooc.house.entity.HouseEntity;
import com.imooc.house.entity.HouseUserEntity;
import com.imooc.house.mapper.HouseMapper;
import com.imooc.house.mapper.HouseUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HouseUserService {
    @Autowired
    private HouseUserMapper houseUserMapper;
    private HouseService houseService;

    /**
     * 绑定房屋到用户
     * @param houseId
     * @param userId
     * @param collect
     */
    public void bindUser2House(Long houseId, Long userId, boolean collect){
        HouseUserEntity houserUserEntity = new HouseUserEntity();
        houserUserEntity.setHouseId(houseId);
        houserUserEntity.setUserId(userId);
        Integer type = collect ? HouseUserType.BOOKMARK.value : HouseUserType.SALE.value;
        houserUserEntity.setType(type);
        List<HouseUserEntity> houseUserEntityList = houseUserMapper.queryByEntity(houserUserEntity);
        if(houseUserEntityList.isEmpty()){
            houserUserEntity.setCreateTime(StringUtil.date2String(new Date()));
            houseUserMapper.insert(houserUserEntity);
        }
    };

    public HouseUserEntity queryUserByHouseId(Long houseId) {
       HouseUserEntity houseUserEntity = new HouseUserEntity();
       houseUserEntity.setHouseId(houseId);
       List<HouseUserEntity> houseUserEntityList = houseUserMapper.queryByEntity(houseUserEntity);
       if(houseUserEntityList.isEmpty()){
           return null;
       }
       return houseUserEntityList.get(0);
    }

    public void unBindUser2House(Long houseId, Long userId, HouseUserType bookmark) {
        HouseUserEntity houseUserEntity = new HouseUserEntity();
        houseUserEntity.setHouseId(houseId);
        houseUserEntity.setUserId(userId);
        houseUserEntity.setType(bookmark.value);
        houseUserMapper.deleteByEntity(houseUserEntity);

    }
}
