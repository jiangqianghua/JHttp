package com.jqh.httpuploaddemo.utils;

import android.util.Log;

/**
 * Created by jiangqianghua on 18/1/6.
 */

public class Logger {

    public static final String TAG = "Upload";

    public static final boolean DEBUG = true ;

    public static String getMessage(Object msg){
        return msg == null ? "NULL":msg.toString();
    }

    public static void i(Object msg){
        if(DEBUG)
            Log.i(TAG,msg.toString());
    }

    public static void d(Object msg){
        if(DEBUG)
            Log.d(TAG,msg.toString());
    }

    public static void w(Object msg){
        if(DEBUG)
            Log.w(TAG,msg.toString());
    }

    public static void e(Object msg){
        if(DEBUG)
            Log.e(TAG,msg.toString());
    }
}
