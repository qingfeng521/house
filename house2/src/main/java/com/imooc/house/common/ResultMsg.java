package com.imooc.house.common;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;


import java.net.URLEncoder;
import java.util.Map;

public class ResultMsg {
    public static final String errorMsgKey = "errorMsg";
    public static final String successMsgKey = "successMsg";

    private String errorMsg;
    private String successMsg;

    public static String getErrorMsgKey() {
        return errorMsgKey;
    }

    public static String getSuccessMsgKey() {
        return successMsgKey;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public Boolean isSuccess(){
        return errorMsg == null;
    }

    public static ResultMsg errorMsg(String msg){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setErrorMsg(msg);
        return resultMsg;
    }

    public static ResultMsg successMsg(String msg){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccessMsg(msg);
        return resultMsg;
    }
    public Map<String,String> asMap(){
       Map<String,String> map = Maps.newHashMap();
       map.put(successMsg,successMsg);
       map.put(errorMsg,errorMsg);
       return map;
    }

    public String asUrlParams(){
        Map<String,String> map = asMap();
        Map<String,String> newMap = Maps.newHashMap();
        map.forEach((k,v) -> {if(v!=null)
        try{
            newMap.put(k, URLEncoder.encode(v,"UTF-8"));
        }catch (Exception e){
                e.printStackTrace();
            }
        });
        return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
    }

}
