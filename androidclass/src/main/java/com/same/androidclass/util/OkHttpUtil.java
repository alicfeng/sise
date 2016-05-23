package com.same.androidclass.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp简单的工具类
 * Created by alic on 16-4-28.
 */
public class OkHttpUtil {
    public static String cookie;
    //目的是复用连接池
    private final static OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    static {
    }

    /**
     * 不开启异步线程
     * @param request 请求参数
     * @return 返回响应
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return OK_HTTP_CLIENT.newCall(request).execute();
    }

    /**
     * 开启异步线程访问 自定义对结果处理
     * @param request 请求参数
     * @param callback 回调方法
     */
    public static void enqueue(Request request, Callback callback){
        OK_HTTP_CLIENT.newCall(request).enqueue(callback);
    }

    /**
     * 开启异步线程访问 但是不对结果处理
     * @param request 请求参数
     */
    public static void enqueue(Request request){
        OK_HTTP_CLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

}
