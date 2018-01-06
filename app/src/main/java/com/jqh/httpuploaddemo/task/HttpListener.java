package com.jqh.httpuploaddemo.task;

/**
 * Created by jiangqianghua on 18/1/6.
 */

public interface HttpListener {


    public void onSuccessed(Response response);

    public void onFailed(Exception e);
}
