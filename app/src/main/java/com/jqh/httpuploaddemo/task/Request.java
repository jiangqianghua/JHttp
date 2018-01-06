package com.jqh.httpuploaddemo.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Request 请求信息封装
 * Created by jiangqianghua on 18/1/6.
 */

public class Request {

    private String url ;

    private RequestMethod method;

    private List<KeyValue> keyValues ;

    public Request(String url){
        this(url,RequestMethod.GET);
    }

    public Request(String url , RequestMethod method){
        this.url = url ;
        this.method = method;

        keyValues = new ArrayList<>();
    }

    public void add(String key ,String value){
        keyValues.add(new KeyValue(key,value));
    }

    public void add(String key , File value){
        keyValues.add(new KeyValue(key,value));
    }

    @Override
    public String toString() {
        return "url = "+ url + " method = "+ method + " params = "+ keyValues.toString();
    }
}
