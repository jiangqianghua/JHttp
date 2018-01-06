package com.jqh.httpuploaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.jqh.httpuploaddemo.entity.UserInfo;
import com.jqh.httpuploaddemo.task.HttpListener;
import com.jqh.httpuploaddemo.task.Request;
import com.jqh.httpuploaddemo.task.RequestExecutor;
import com.jqh.httpuploaddemo.task.RequestMethod;
import com.jqh.httpuploaddemo.task.Response;
import com.jqh.httpuploaddemo.utils.Constants;
import com.jqh.httpuploaddemo.utils.Logger;
import com.jqh.httpuploaddemo.utils.ThreadUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_get).setOnClickListener(this);
        findViewById(R.id.button_head).setOnClickListener(this);
        findViewById(R.id.button_post).setOnClickListener(this);
    }

    /**
     * Get请求
     */
    private void getRequest(){
//        ThreadUtils.execute(new Runnable() {
//            @Override
//            public void run() {
//                executeGet();
//            }
//        });

        Request request = new Request(Constants.UR_UPOAD_POST);
        request.add("userName","jiang");
        request.add("passWord","150700");
        RequestExecutor.INTANCE.execute(request, new HttpListener() {
            @Override
            public void onSuccessed(Response response) {
                Logger.i("结果："+response.getResult());
            }

            @Override
            public void onFailed(Exception e) {
                Logger.i("结果："+e.getMessage());
            }
        });
    }

    /**
     * 执行Get请求，在子线程执行
     */
    private void executeGet(){
        // 执行Get请求，用URLConnect请求
        URL url = null ;
        HttpURLConnection httpURLConnection = null ;
        InputStream inputStream = null ;
        try {
            url = new URL(Constants.URL_UPOAD);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 判断是否是https
//        if(httpURLConnection instanceof HttpsURLConnection){
//            ((HttpsURLConnection)httpURLConnection).setHostnameVerifier();
//            ((HttpsURLConnection)httpURLConnection).setSSLSocketFactory();
//        }

            httpURLConnection.setRequestMethod("GET");
            // httpURLConnection.setRequestProperty();
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                inputStream = httpURLConnection.getInputStream();
                readServerData(inputStream);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
            if(inputStream != null)
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
        }

    }


    /**
     * Head请求
     */
    private void headRequest(){

        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                executeHeader();
            }
        });

    }

    /**
     * 执行Header请求，在子线程执行
     */
    private void executeHeader(){
        // 执行Head请求，用URLConnect请求
        URL url = null ;
        HttpURLConnection httpURLConnection = null ;
        InputStream inputStream = null ;
        try {
            url = new URL(Constants.URL_UPOAD);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("HEAD");
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                readServerHead(httpURLConnection);
                //inputStream = httpURLConnection.getInputStream();
                //readServerData(inputStream); // Head请求不能获取body信息，只能获取返回的头部信息
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
            if(inputStream != null)
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
        }

    }

    /**
     * Post请求
     */
    private void postRequest(){
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                executePost();
            }
        });
    }

    /**
     * 执行Post请求，在子线程执行
     */
    private void executePost(){
        // 执行POST请求，用URLConnect请求
        URL url = null ;
        HttpURLConnection httpURLConnection = null ;
        InputStream inputStream = null ; // 接受数据流
        OutputStream outputStream = null ; // 发送数据流
        try {
            url = new URL(Constants.UR_UPOAD_POST);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("ContentType","application/json");
            httpURLConnection.connect();


            outputStream = httpURLConnection.getOutputStream();
//            String outputMsg = "hello server";
//            outputStream.write(outputMsg.getBytes());

            // 提交json数据
//            UserInfo userInfo = new UserInfo("333","1234");
//            String json = JSON.toJSONString(userInfo);
//            Logger.d("to send server json:\n"+json);
//            outputStream.write(json.getBytes());

            String postParams = "userName=胡丹123&passWord=1223";
            outputStream.write(postParams.getBytes());
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                readServerData(httpURLConnection.getInputStream());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
            if(inputStream != null)
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
        }

    }

    private void readServerData(InputStream inputStream)throws IOException{
        byte[] buffer = new byte[2014];
        int len ;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer,0,len);
        }
        outputStream.close();
        String data = new String(outputStream.toByteArray());
        Logger.d("server data:"+data);

    }

    /**
     * 读取服务器头部信息
     * @param urlConnection
     */
    private void readServerHead(HttpURLConnection urlConnection){
        Map<String,List<String>> stringListMap = urlConnection.getHeaderFields();
        Set<Map.Entry<String,List<String>>> entries = stringListMap.entrySet();
        Logger.i("head info:");
        for(Map.Entry<String,List<String>> entry:entries){
            String headKey = entry.getKey();
            List<String> headValue = entry.getValue();
            Logger.i(" head key: "+headKey + "  value: ");
            for(String val:headValue){
                Logger.i("---" + val);
            }
            //Logger.i(" head key: "+headKey + "  value: "+headValue);
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_get:{
                getRequest();
                break;
            }
            case R.id.button_head: {
                headRequest();
                break;
            }
            case R.id.button_post: {
                postRequest();
                break;
            }
        }
    }
}
