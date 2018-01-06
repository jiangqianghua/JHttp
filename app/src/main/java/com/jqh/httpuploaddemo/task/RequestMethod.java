package com.jqh.httpuploaddemo.task;

/**
 * 请求类型封装
 * Created by jiangqianghua on 18/1/6.
 */

public enum RequestMethod {

    GET("GET"),
    POST("POST"),
    HEAD("HEAD"),
    DELETE("DELETE");

    private  String value;

    public String value(){
        return value ;
    }

    RequestMethod(String value){
        this.value = value ;
    }

    @Override
    public String toString() {
        return value;
    }
}
