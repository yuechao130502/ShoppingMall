package com.wyuyc.shoppingmall.app;

import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by yc on 2016/12/21.
 */

public class MyApplication extends Application{

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
//        initOkhttpClient();//坑!!!
    }

    // 获取全局上下文
    public static Context getContext() {
        return mContext;
    }

    /**
     * 初始化OkHttpUtils
     */
    private void initOkhttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L,TimeUnit.MICROSECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
