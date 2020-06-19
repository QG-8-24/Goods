package com.com.example.goods.utils;


import com.com.example.goods.entity.Goods;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    public final static int CONNECT_TIMEOUT = 6;
    public final static int READ_TIMEOUT = 6;
    public final static int WRITE_TIMEOUT = 6;
    public final static String BASE_URL = "http://192.168.101.8:80";

    public static String postRequest(String api) throws IOException {
        //客户端
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                ////连接超时
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                //读取超时
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                //写入超时
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
        //请求方法
        Goods goods = new Goods();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),"");
        Request request = new Request.Builder()
                .post(requestBody)
                .url(BASE_URL + api)
                .build();
        //发起请求
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();

    }

    public static String postRequestJSON(String api, String JSON) throws IOException {
        //客户端
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                ////连接超时
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                //读取超时
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                //写入超时
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
        //请求方法
        Goods goods = new Goods();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), JSON);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(BASE_URL + api)
                .build();
        //发起请求
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();

    }
}