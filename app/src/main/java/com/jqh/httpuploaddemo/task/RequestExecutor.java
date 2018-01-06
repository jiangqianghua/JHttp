package com.jqh.httpuploaddemo.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jiangqianghua on 18/1/6.
 */

public enum RequestExecutor {
    // 全局单例
    INTANCE;

    private ExecutorService mExecutorService = null;

    RequestExecutor(){
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    /**
     * 执行请求一个Request对象
     * @param request
     */
    public void execute(Request request,HttpListener httpListener){
        mExecutorService.execute(new RequestTask(request,httpListener));
    }

}
