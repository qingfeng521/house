package com.imooc.house.controller;

import com.imooc.house.entity.HouseEntity;
import com.imooc.house.service.RecommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomePageController {

    @Autowired
    private RecommandService recommandService;


    @RequestMapping("/index")
    public String index(ModelMap modelMap){
        List<HouseEntity> houseEntityList = recommandService.getLastHouses();
        modelMap.put("recomHouses",houseEntityList);
        return "homepage/index";
    }

    @RequestMapping("")
    public String home(ModelMap modelMap){
        return "redirect:index";
    }


}
