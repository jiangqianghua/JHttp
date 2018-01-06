package com.jqh.httpuploaddemo.task;

import java.util.List;
import java.util.Map;

/**
 * 请求的响应类
 * Created by jiangqianghua on 18/1/6.
 */

public class Response {
    // response code
    private int responseCode ;

    private String result ;

    private Exception exception ;

    private Request request;

    private Map<String,List<String>> responseHeaders; //服务器响应头

    public Response(Request request,int responseCode , Map<String,List<String>> responseHeaders ,String result,Exception exception)
    {
        this.request = request ;
        this.responseCode = responseCode ;
        this.result = result ;
        this.exception = exception;
        this.responseHeaders = responseHeaders ;
    }

    public Exception getException() {
        return exception;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResult() {
        return result;
    }

    public Request getRequest() {
        return request;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }
}
