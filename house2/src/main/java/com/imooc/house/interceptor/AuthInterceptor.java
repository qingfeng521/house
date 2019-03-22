package com.imooc.house.interceptor;

import com.google.common.base.Joiner;
import com.imooc.house.Constants;
import com.imooc.house.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Configuration
public class AuthInterceptor implements HandlerInterceptor {
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
        HttpSession session = request.getSession(true);
        UserEntity user = (UserEntity) session.getAttribute(Constants.USER_ATTRIBUTE);
        if(user != null){
            UserContext.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
