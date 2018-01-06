package com.jqh.httpuploaddemo.task;

import com.jqh.httpuploaddemo.utils.Logger;

/**
 * Created by jiangqianghua on 18/1/6.
 */

public class RequestTask implements Runnable {

    private Request request ;
    private HttpListener httpListener ;

    public RequestTask(Request request,HttpListener httpListener){
        this.request = request ;
        this.httpListener = httpListener ;
    }

    @Override
    public void run() {
        // 模拟执行
        try {
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }

        Logger.i("执行请求 " + request.toString());

        Response response = new Response(request,200,null,"data",null);

        Message message = new Message(response,httpListener);

        Poster.getInstance().post(message);
    }
}
