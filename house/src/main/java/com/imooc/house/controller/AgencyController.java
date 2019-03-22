package com.imooc.house.controller;

import com.imooc.house.common.PageData;
import com.imooc.house.common.PageParams;
import com.imooc.house.entity.AgencyEntity;
import com.imooc.house.entity.HouseEntity;
import com.imooc.house.entity.HouseMsgEntity;
import com.imooc.house.entity.UserEntity;
import com.imooc.house.interceptor.UserContext;
import com.imooc.house.service.AgencyService;
import com.imooc.house.service.HouseMsgService;
import com.imooc.house.service.HouseService;
import com.imooc.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AgencyController {

    @Autowired
    private AgencyService agencyService;
    @Autowired
    private UserService userService;
    @Autowired
    private HouseMsgService houseMsgService;

    @RequestMapping("agency/agentList")
    public String showAgentList(Integer pageNum, Integer pageSize, ModelMap modelMap){
        PageData<UserEntity> ps = agencyService.getAllAgent(PageParams.build(pageSize,pageNum));
        modelMap.addAttribute("ps",ps);
        return "user/agent/agentList";
    }

    @RequestMapping("agency/agentDetail")
    public String showAgentDetail(Long id, ModelMap modelMap){
        UserEntity userEntity = agencyService.getAgencyDetail(id);
        PageData<HouseEntity> ps = agencyService.queryHousesByAgentId(id);
        if(ps!=null){
            modelMap.addAttribute("bindHouses",ps.getList());
            modelMap.addAttribute("ps",ps);
        }
        modelMap.addAttribute("agent",userEntity);
        return "user/agent/agentDetail";
    }

    @RequestMapping("agency/agentMsg")
    public String agentMsg(HouseMsgEntity houseMsgEntity, ModelMap modelMap){
        UserEntity user = UserContext.getUser();
        houseMsgEntity.setUserId(user.getId());
        houseMsgService.addHouseMsg(houseMsgEntity);
        return "redirect:agentDetail?id=" + houseMsgEntity.getAgentId();
    }

}
