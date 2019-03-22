package com.imooc.house.controller;

import com.imooc.house.Constants;
import com.imooc.house.common.PageData;
import com.imooc.house.common.PageParams;
import com.imooc.house.common.ResultMsg;
import com.imooc.house.constants.HouseUserType;
import com.imooc.house.entity.*;
import com.imooc.house.interceptor.UserContext;
import com.imooc.house.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@Controller
public class HouseController {
    @Autowired
    private HouseMsgService houseMsgService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private HouseUserService houseUserService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private RecommandService recommandService;

    /**
     * 1. 实现分页
     * 2. 支持小区搜索 排序搜索
     * 3. 支持排序操作
     * 4. 支持展示图片  价格  标题  描述...
     * @return
     */

    @RequestMapping("house/list")
    public String houseList(Integer pageNum, Integer pageSize, HouseEntity query, ModelMap modelMap){
        List<HouseEntity> hotHouseList = recommandService.queryHotHouse(Constants.RECOM_SIZE);
        PageData<HouseEntity> houseList = houseService.queryPageList(query, PageParams.build(pageSize,pageNum));
        modelMap.put("ps",houseList);
        modelMap.put("recomHouses",hotHouseList);
        modelMap.put("vo",query);
        return "house/listing";
    }

    /**
     * 查询所有城市
     * @return
     */
    @RequestMapping("house/toAdd")
    public String toAddHouse(ModelMap modelMap){
        List<CityEntity> cityList = houseService.queryCityList();
        Community community = new Community();
        List<Community> communityList = houseService.queryCommunityByEntity(community);
        modelMap.put("citys",cityList);
        modelMap.put("communitys",communityList);
        return "house/add";
    }
    /**
     * /house/add
     */
    @RequestMapping("house/add")
    public String addHouse(HouseEntity house,ModelMap modelMap){
        UserEntity user = UserContext.getUser();
        house.setState(Integer.valueOf(Constants.HOUSE_STATE_UP));
        houseService.addHouse(house,user);
        return "redirect:/house/ownlist";
    }

    @RequestMapping("house/ownlist")
    public String ownlist(HouseEntity house,Integer pageNum,Integer pageSize,ModelMap modelMap){
        UserEntity user = UserContext.getUser();
        house.setUserId(user.getId());
        PageData<HouseEntity> pageData = houseService.queryPageList(house,PageParams.build(pageSize,pageNum));
        modelMap.put("ps",pageData);
        modelMap.put("pageType", "book");
        return "/house/ownlist";
    }

    /**
     *  1.查看房屋详情
     *  2.查询关联经纪人
     */
    @RequestMapping("house/detail")
    public String getHouseDetail(Long id,ModelMap modelMap){
        recommandService.increase(id);
        HouseEntity house = houseService.queryHouseById(id);
        HouseUserEntity houseUserEntity = houseUserService.queryUserByHouseId(id);
        if(houseUserEntity!=null && houseUserEntity.getUserId()!=0){
            modelMap.addAttribute("agent",agencyService.getAgencyDetail(houseUserEntity.getUserId()));
        }
        List<HouseEntity> hotHouseList = recommandService.queryHotHouse(Constants.RECOM_SIZE);
        modelMap.put("recomHouses",hotHouseList);
        modelMap.addAttribute("house",house);
        return "/house/detail";
    }

    @RequestMapping("house/leaveMsg")
    public String houseMsg(HouseMsgEntity houseMsgEntity,ModelMap modelMap){
        UserEntity user = UserContext.getUser();
        houseMsgEntity.setUserId(user.getId());
        houseMsgService.addHouseMsg(houseMsgEntity);
        return "redirect:/house/detail?id=" + houseMsgEntity.getHouseId();
    }
    @RequestMapping("house/hello")
    @ResponseBody
    public String text(){
        System.out.println("进来了......");
        String set = jedisCluster.set("syc", "syc");
        return jedisCluster.get("syc");
    }
    @ResponseBody
    @RequestMapping("house/rating")
    public ResultMsg houseRate(Double rate, Long id){
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setRating(rate);
        houseEntity.setId(id);
        houseService.updateById(houseEntity);
        return ResultMsg.successMsg("ok");
    }

    @ResponseBody
    @RequestMapping("house/bookmark")
    public ResultMsg houseBookMark(Long id){
        UserEntity userEntity = UserContext.getUser();
        houseUserService.bindUser2House(id,userEntity.getId(),true);
        return ResultMsg.successMsg("ok");
    }

    @ResponseBody
    @RequestMapping("house/unbookmark")
    public ResultMsg unBookMark(Long id){
        UserEntity userEntity = UserContext.getUser();
        houseUserService.unBindUser2House(id,userEntity.getId(), HouseUserType.BOOKMARK);
        return ResultMsg.successMsg("ok");
    }

    @RequestMapping("house/bookmarked")
    public String houseBookMarked(HouseEntity houseEntity,Integer pageNum,Integer pageSize,ModelMap modelMap){
        UserEntity userEntity = UserContext.getUser();
        houseEntity.setBookmarked(true);
        houseEntity.setUserId(userEntity.getId());
        PageData<HouseEntity> ps = houseService.queryBookMarked(houseEntity, PageParams.build(pageSize,pageNum));
        modelMap.put("ps",ps);
        modelMap.put("pageType","book");
        return "house/ownlist";
    }

    @RequestMapping("house/del")
    public String houseDel(Long id,String pageType){
        UserEntity userEntity = UserContext.getUser();
        houseUserService.unBindUser2House(id,userEntity.getId(), StringUtils.equals(pageType,"own")?HouseUserType.SALE:HouseUserType.BOOKMARK);
        return "redirect:ownlist";
    }

}
