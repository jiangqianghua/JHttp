package com.jqh.httpuploaddemo.task;

import com.jqh.httpuploaddemo.utils.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

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


        Logger.i("执行请求 " + request.toString());

        Exception exception = null ;
        int responseCode = -1;

        Map<String ,List<String>> responseHeaders = null ;

        String urlStr = request.getUrl();

        RequestMethod method = request.getMethod();

        HttpURLConnection httpURLConnection = null ;

        InputStream inputStream = null ;

        OutputStream outputStream = null ;

        String result = null ;

        String params = request.getParamsForHttp();
        try
        {
            if(RequestMethod.GET.value().equals(method.value()))
            {
                urlStr += "?"+params ;
            }
            URL url = new URL(urlStr);
            //httpURLConnection = (HttpURLConnection)url.openConnection();
            // 利用工厂打开，可以从okhttp 和 httpurlconnecter自由切换
            httpURLConnection = URLConnectionFactory.getInstance().openUrl(url);
            // https 处理
            if(httpURLConnection instanceof HttpsURLConnection){
                HttpsURLConnection httpsURLConnection = ((HttpsURLConnection)httpURLConnection);
                SSLSocketFactory sslSocketFactory = request.getSslSocketFactory();
                HostnameVerifier hostnameVerifier = request.getHostnameVerifier();
                if(sslSocketFactory != null)
                    httpsURLConnection.setSSLSocketFactory(sslSocketFactory);// https证书相关信息

                if(hostnameVerifier != null)
                    httpsURLConnection.setHostnameVerifier(hostnameVerifier);// 主机认证

            }

            // 发送数据
            httpURLConnection.setRequestMethod(method.value());

            // 如果是post，需要输出流
            String m = RequestMethod.POST.value();
            if(m.equals(method.value()))
            {
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(params.getBytes());
            }

            // 构建参数

            httpURLConnection.connect();

            responseCode = httpURLConnection.getResponseCode();

            if(responseCode == 200){
                inputStream = httpURLConnection.getInputStream();
                result = readServerData(inputStream);
            }

            responseHeaders = httpURLConnection.getHeaderFields();

        }catch (Exception e){
            exception = e ;
        }
        finally {
            if(httpURLConnection != null)
                httpURLConnection.disconnect();

            if(inputStream != null)
                try {
                    inputStream.close();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            Logger.i("执行结束 " + request.toString());
            Response response = new Response(request,responseCode,responseHeaders,result,exception);

            Message message = new Message(response,httpListener);

            Poster.getInstance().post(message);
        }



    }

    private String readServerData(InputStream inputStream)throws IOException {
        byte[] buffer = new byte[2014];
        int len ;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer,0,len);
        }
        outputStream.close();
        String data = new String(outputStream.toByteArray());
        //Logger.d("server data:"+data);
        return data ;
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
}
