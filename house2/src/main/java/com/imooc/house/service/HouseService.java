package com.imooc.house.service;

import com.google.common.collect.Lists;
import com.imooc.house.Constants;
import com.imooc.house.common.BeanHelper;
import com.imooc.house.common.PageData;
import com.imooc.house.common.PageParams;
import com.imooc.house.common.StringUtil;
import com.imooc.house.constants.HouseUserType;
import com.imooc.house.entity.*;
import com.imooc.house.mapper.HouseMapper;
import com.imooc.house.mapper.HouseUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService {
    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private HouseUserMapper houseUserMapper;

    @Autowired
    private FileService fileService;
    @Autowired
    private HouseUserService houseUserService;
    /**
     * 1. 查询小区 Id
     * 2. 添加图片
     * 3. 构建Vo 对象
     * @param query
     * @param
     */
    public PageData<HouseEntity> queryPageList(HouseEntity query, PageParams pageParams) {
        List<HouseEntity> houseEntityList = Lists.newArrayList();
        if(StringUtils.isNotBlank(query.getName())){
            Community community = new Community();
            community.setName(query.getName());
            List<Community> communityList = houseMapper.queryCommunity(community);
            if(!communityList.isEmpty()){
                query.setCommunityId(communityList.get(0).getId());
                query.setName(null);
            }
        }
        houseEntityList = queryAndSetImag(query,pageParams);
        Long count = houseMapper.queryPageCount(query);
        return PageData.buildPage(houseEntityList,count,pageParams.getPageNum(),pageParams.getPageNum());
    }

    public List<HouseEntity> queryAndSetImag(HouseEntity query, PageParams pageParams) {
        List<HouseEntity> houseEntityList = houseMapper.queryPageList(query,pageParams);
        houseEntityList.forEach(h->{
            h.setFirstImg(Constants.FILE_URL + h.getFirstImg());
            h.setImageList(h.getImageList().stream().map(img-> Constants.FILE_URL + img).collect(Collectors.toList()));
            h.setFloorPlanList(h.getFloorPlanList().stream().map(pic-> Constants.FILE_URL + pic).collect(Collectors.toList()));
        });
        return houseEntityList;
    }

    public List<CityEntity> queryCityList() {
        return houseMapper.queryCityList();
    }

    public List<Community> queryCommunityByEntity(Community community) {
        return houseMapper.queryCommunity(community);
    }

    public void addHouse(HouseEntity house, UserEntity user) {
        List<String> imageList = fileService.getImagPath(house.getHouseFiles());
        List<String> floorList = fileService.getImagPath(house.getFloorPlanFiles());
        if(!imageList.isEmpty()){
            house.setImages(getImage(imageList));
        }
        if(!floorList.isEmpty()){
            house.setFloorPlan(getImage(floorList));
        }
        BeanHelper.setDefaultProp(house,HouseEntity.class);
        house.setCreateTime(StringUtil.date2String(new Date()));
        houseMapper.insert(house);
        houseUserService.bindUser2House(house.getId(),user.getId(),false);
    }

    private String getImage(List<String> list){
        StringBuilder builder = new StringBuilder();
        list.forEach(li->{
            builder.append(li + ",");
        });
        return builder.substring(0,builder.lastIndexOf(",")).toString();
    }

    public HouseEntity queryHouseById(Long houseId) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setId(houseId);
        List<HouseEntity> houseEntityList = queryAndSetImag(houseEntity,PageParams.build(1,1));
        if(houseEntityList.isEmpty()){
            return null;
        }
        return houseEntityList.get(0);
    }

    public void updateById(HouseEntity houseEntity) {
        HouseEntity houseEntityById = houseMapper.queryEntityById(houseEntity.getId());
        if(houseEntityById != null){
            Double orgHouseRate = houseEntity.getRating();
            if(orgHouseRate == null){
                orgHouseRate = 0.0;
            }
            houseEntity.setRating(Math.min((orgHouseRate + houseEntityById.getRating())/2,5));
            houseMapper.updateById(houseEntity);
        }
    }

    public PageData<HouseEntity> queryBookMarked(HouseEntity houseEntity, PageParams build) {
        HouseUserEntity houseUserEntity = new HouseUserEntity();
        houseUserEntity.setUserId(houseEntity.getUserId());
        Integer type = houseEntity.getBookmarked()?HouseUserType.BOOKMARK.value:HouseUserType.SALE.value;
        houseUserEntity.setType(type);
        List<HouseUserEntity> houseUserEntityList = houseUserMapper.queryByEntity(houseUserEntity);
        List<Long> ids = houseUserEntityList.stream().map(HouseUserEntity::getHouseId).collect(Collectors.toList());
        houseEntity.setIds(ids);
        long count = 0l;
        List<HouseEntity> houseEntityList = new ArrayList<HouseEntity>();
        if(!ids.isEmpty()){
            houseEntityList = queryAndSetImag(houseEntity,build);
            if(!houseEntityList.isEmpty()){
                count = houseEntityList.size();
            }
        }
        return PageData.buildPage(houseEntityList,count,build.getPageNum(),build.getPageNum());
    }
}
