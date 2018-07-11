package com.stx.xhb.core.api;

import android.text.TextUtils;


import com.stx.xhb.core.utils.LoggerHelper;

import java.lang.reflect.Field;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {

    private static final String TAG = "===Enjoy";
    private static RestApi mInstance;
    public static boolean isDebug = false;

    public static synchronized RestApi getInstance() {
        if (mInstance == null) {
            mInstance = new RestApi();
        }
        return mInstance;
    }

    public void bug(boolean isDebug) {
        RestApi.isDebug = isDebug;
    }

    // create retrofit singleton
    private Retrofit createApiClient(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createOkHttpClient())
                .build();
    }

    // create api service singleton
    public <T> T create(String baseUrl, Class<T> clz) {
        String serviceUrl = "";
        try {
            Field field1 = clz.getField("BASE_URL");
            serviceUrl = (String) field1.get(clz);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.getMessage();
            e.printStackTrace();
        }
        return createApiClient(
                TextUtils.isEmpty(serviceUrl) ? baseUrl : serviceUrl).create(clz);
    }

    // create api service baseUrl singleton
    public <T> T create(Class<T> clz) {
        String serviceUrl = "";
        try {
            Field field1 = clz.getField("BASE_URL");
            serviceUrl = (String) field1.get(clz);
            if (TextUtils.isEmpty(serviceUrl)) {
                throw new NullPointerException("base_url is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createApiClient(serviceUrl).create(clz);
    }

    // create okHttpClient singleton
    private OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .addInterceptor(
                        new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                            @Override
                            public void log(String message) {
                                if (message.startsWith("{") || message.startsWith("[")) {
                                    LoggerHelper.json(TAG,message);
                                } else {
                                    LoggerHelper.d(TAG,message);
                                }
                            }
                        }).setLevel(HttpLoggingInterceptor.Level.BODY)).build();
    }

}
