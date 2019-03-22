package com.imooc.house.user_service.common;

public class RestResponse <T> {

    private Integer code;
    private String msg;
    private T result;


    public RestResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public RestResponse() {
        this(RestCode.OK.code,RestCode.OK.msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


    public static <T> RestResponse<T> success(){
        RestResponse<T> response = new RestResponse<>();
        return response;
    }

    public static <T> RestResponse<T> success(T result){
        RestResponse<T> response = new RestResponse<>();
        response.setResult(result);
        return response;
    }

    public static <T> RestResponse<T> error(RestCode code){
        return new RestResponse<>(code.code,code.msg);
    }










}
