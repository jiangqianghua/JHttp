package com.jqh.httpuploaddemo.task;

/**
 * Created by jiangqianghua on 18/1/6.
 */

public class Message implements Runnable{

    private Response response ;
    private HttpListener httpListener ;
    public Message(Response response,HttpListener httpListener){
        this.response = response;
        this.httpListener = httpListener ;
    }

    @Override
    public void run() {
        // 主线程执行
        Exception exception = response.getException();
        if(exception != null){
            httpListener.onFailed(exception);
        }
        else
        {
            httpListener.onSuccessed(response);
        }
    }
}
