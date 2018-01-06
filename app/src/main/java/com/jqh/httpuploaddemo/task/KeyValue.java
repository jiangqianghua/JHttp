package com.jqh.httpuploaddemo.task;

import java.io.File;
import java.security.Key;

/**
 * Created by jiangqianghua on 18/1/6.
 */

public class KeyValue {

    private String key ;

    private Object value ;

    public KeyValue(String key,String value){
        this.key = key;
        this.value = value ;
    }

    public KeyValue(String key, File value){
        this.key = key ;
        this.value = value ;
    }


    public Object getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "key="+key + " value="+value;
    }
}
