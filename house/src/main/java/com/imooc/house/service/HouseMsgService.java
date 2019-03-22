package com.imooc.house.service;

import com.imooc.house.common.BeanHelper;
import com.imooc.house.common.StringUtil;
import com.imooc.house.entity.HouseEntity;
import com.imooc.house.entity.HouseMsgEntity;
import com.imooc.house.entity.UserEntity;
import com.imooc.house.mapper.HouseMsgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HouseMsgService {
   @Autowired
   private AgencyService agencyService;
   @Autowired
   private HouseMsgMapper houseMsgMapper;
   @Autowired
   private MailService mailService;
    public void addHouseMsg(HouseMsgEntity houseMsgEntity) {
        BeanHelper.setDefaultProp(houseMsgEntity,HouseMsgEntity.class);
        houseMsgEntity.setCreateTime(StringUtil.date2String(new Date()));
        houseMsgMapper.insert(houseMsgEntity);
        UserEntity userEntity = agencyService.getAgencyDetail(houseMsgEntity.getAgentId());
        mailService.sendMail("来自用户"+ houseMsgEntity.getEmail() + "的留言",houseMsgEntity.getMsg(),userEntity.getEmail());
    }
}
