package com.imooc.house.api.interceptor;

import com.google.common.base.Joiner;
import com.imooc.house.api.constants.Constants;
import com.imooc.house.api.dao.UserDao;
import com.imooc.house.api.entity.UserEntity;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Configuration
public class AuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN_COOKIE = "token";

    @Autowired
    private UserDao userDao;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String reqUrl = request.getRequestURI();
        Map<String,String[]> parMap = request.getParameterMap();
        parMap.forEach((k,v)->{
            if(StringUtils.equals(k,"errorMsg")||StringUtils.equals(k,"successMsg")||StringUtils.equals(k,"targer")){
                request.setAttribute(k,Joiner.on(",").join(v));
            }
        });
        if(reqUrl.startsWith("/static")||reqUrl.startsWith("/error")){
            return true;
        }
       /* HttpSession session = request.getSession(true);
        UserEntity user = (UserEntity) session.getAttribute(Constants.USER_ATTRIBUTE);
        if(user != null){
            UserContext.setUser(user);
        }*/

        Cookie cookie = WebUtils.getCookie(request,TOKEN_COOKIE);
        if(cookie != null && StringUtils.isNotBlank(cookie.getValue())){
            UserEntity userEntity = userDao.getUserByToken(cookie.getValue());
            request.setAttribute(Constants.USER_ATTRIBUTE,userEntity);
            UserContext.setUser(userEntity);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String reqUrl = request.getRequestURI();
        if(reqUrl.startsWith("/static")||reqUrl.startsWith("/error")){
            return;
        }

        UserEntity user = UserContext.getUser();
        if(user != null &&StringUtils.isNotBlank(user.getToken())){
            String token = reqUrl.startsWith("logout") ? "" : user.getToken();
            Cookie cookie = new Cookie(TOKEN_COOKIE,token);
            cookie.setPath("/");
            cookie.setHttpOnly(false);
        }




    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
