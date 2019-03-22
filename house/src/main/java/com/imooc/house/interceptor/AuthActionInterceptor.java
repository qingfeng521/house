package com.imooc.house.interceptor;

import com.imooc.house.entity.UserEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
@Configuration
public class AuthActionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserEntity user = UserContext.getUser();
        if(user == null){
            String mString = URLEncoder.encode("请先登录","UTF-8");
            String target = URLEncoder.encode(request.getRequestURI().toString(),"UTF-8");
            if("GET".equalsIgnoreCase(request.getMethod())){
                response.sendRedirect( "/accounts/signin?errorMsg="+ mString + "&target=" + target);
            }
            response.sendRedirect( "/accounts/signin?errorMsg="+ mString);
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
