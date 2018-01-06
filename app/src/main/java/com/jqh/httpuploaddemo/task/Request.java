package com.jqh.httpuploaddemo.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/**
 * Request 请求信息封装
 * Created by jiangqianghua on 18/1/6.
 */

public class Request {

    private String url ;

    private RequestMethod method;

    private List<KeyValue> keyValues ;

    // https 证书相关
    private SSLSocketFactory sslSocketFactory ;

    private HostnameVerifier hostnameVerifier ;

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

    public String getUrl() {
        return url;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public List<KeyValue> getKeyValues() {
        return keyValues;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public String getParamsForHttp(){
        String params = "";
        if(keyValues == null || keyValues.size() == 0)
            return null ;
        for(KeyValue keyValue:keyValues){
            if(!"".equals(params))
                params += "&";
            params += keyValue.getKey()+"="+keyValue.getValue();
        }
        return params ;
    }
}
