package com.imooc.house.api.util;



import com.imooc.house.api.common.RestResponse;
import com.imooc.house.api.config.GenericRest;
import org.apache.commons.lang.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.xml.ws.Response;
import java.util.concurrent.Callable;

/**
 * 远程调用工具类
 */
@Component
public  class RestUtils {

//    @Autowired
//    private GenericRest genericRest;

    private final static Logger LOGGER = LoggerFactory.getLogger(RestUtils.class);
    private static DefaultHander defaultHander = new DefaultHander();
    private RestUtils(){}

    public static <T> T exe (Callable<T> callable){
        T result = sendReq(callable);
        return RestUtils.exe(callable,defaultHander);
    }

    public static <T> T exe (Callable<T> callable,ResultHandler handler){
        T result = sendReq(callable);
        return handler.handle(result);
    }

    public static String toUrl(String serviceName,String path){
        return "http://" + serviceName + path;
    }

    public interface ResultHandler{
        <T> T handle(T result);
    }

    public static class DefaultHander implements ResultHandler{
        @Override
        public <T> T handle(T result) {
            int code = 1;
            String msg="";
            try {
                code = (Integer) FieldUtils.readDeclaredField(result,"code",true);
                msg = (String) FieldUtils.readDeclaredField(result,"msg",true);
            }catch (Exception e){

            }
            if(code != 0){
                throw new RuntimeException();
            }
            return result;
        }
    }

    public static <T> T sendReq(Callable<T> callable){
        T result = null;
        try{
            result = callable.call();

        }catch (Exception e){
            throw  new RuntimeException("远程调用失败");
        }finally {
            LOGGER.info("result={}",result);
        }
        return result;
    }

}
