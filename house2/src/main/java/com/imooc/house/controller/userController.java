package com.imooc.house.controller;

import com.imooc.house.Constants;
import com.imooc.house.common.HashUtil;
import com.imooc.house.common.ResultMsg;
import com.imooc.house.entity.UserEntity;
import com.imooc.house.mapper.UserMapper;
import com.imooc.house.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class userController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @RequestMapping("accounts/toRegister")
    public String queryUsers(){
        return "/user/accounts/register";
    }

    @RequestMapping("accounts/register")
    public String userRegist(UserEntity user,ModelMap model){
        if(user == null || user.getName() == null ){
            return "/user/accounts/register";
        }

        ResultMsg resultMsg = UserHelper.validate(user);
        if(resultMsg.isSuccess() && userService.addUser(user)){
            model.addAttribute("email",user.getEmail());
            return "/user/accounts/registerSubmit";

        }else{
            return "/user/accounts/register" + resultMsg.asUrlParams();
        }




    }

    @RequestMapping("accounts/verify")
    public String verify(String key){
        Boolean flag = userService.enable(key);
        if(flag){
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        }
        return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败,请重新注册").asUrlParams();
    }
    @RequestMapping("accounts/signin")
    public String login(HttpServletRequest request,ModelMap model){
        String userName = request.getParameter("username");
        String passwd = request.getParameter("password");
        String target = request.getParameter("target");
        if(userName == null || passwd == null){
            request.setAttribute("target",target);
            model.addAttribute("username",userName);
            return "/user/accounts/signin";
        }

        UserEntity user = userService.checkUserName(userName,passwd);
        if(user == null){
            return "redirect:/accounts/signin?target=" + target + "&username="+ userName + "&" + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        }
        HttpSession session = request.getSession(true);
        session.setAttribute(Constants.USER_ATTRIBUTE,user);
        session.setAttribute(Constants.PLAIN_USER_ATTRIBUTE,user);
        return StringUtils.isNotBlank(target)?"redirect:" + target : "redirect:/index";
    }
    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "redirect:/index";
    }

    //@RequestMapping("index")
    public String toIndex(HttpServletRequest request){
        return "homepage/index";
    }

    @RequestMapping("accounts/profile")
    public String profile(HttpServletRequest request,UserEntity updateUser, ModelMap model){
        if (StringUtils.isNotBlank(updateUser.getEmail())){
            userService.updateUser(updateUser);
            UserEntity queryUser = new UserEntity();
            queryUser.setEmail(updateUser.getEmail());
            List<UserEntity> userList = userService.queryUserByEntity(queryUser);
            request.getSession(true).setAttribute(Constants.USER_ATTRIBUTE,userList.get(0));
            return "redirect:profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
        }
        return "user/accounts/profile";
    }
    @RequestMapping("accounts/changePassword")
    public String changePwd(String email,String password,String newPassword,String confirmPassword,ModelMap model){
        UserEntity user = userService.checkUserName(email,password);
        if(user == null || !StringUtils.equals(newPassword,confirmPassword)){
            return "redirect:accounts/profile?" + ResultMsg.errorMsg("密码错误").asUrlParams();
        }
        UserEntity queryEntity = new UserEntity();
        queryEntity.setEmail(email);
        queryEntity.setPasswd(HashUtil.encryPasswd(newPassword));
        userService.updateUser(queryEntity);
        return "redirect:profile?" + ResultMsg.successMsg("修改密码成功").asUrlParams();
    }

}
