package com.jqh.httpuploaddemo.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jiangqianghua on 18/1/6.
 */

public class ThreadUtils {

    private static ExecutorService sExecutorService =
            Executors.newSingleThreadExecutor();

    public static void execute(Runnable runnable){
        sExecutorService.execute(runnable);
    }
}
