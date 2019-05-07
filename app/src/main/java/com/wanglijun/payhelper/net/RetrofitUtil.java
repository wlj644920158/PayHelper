package com.wanglijun.payhelper.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.wanglijun.payhelper.common.Constants.BASE_URL;
import static com.wanglijun.payhelper.common.Constants.DEFAULT_TIMEOUT;
import static com.wanglijun.payhelper.common.Constants.IS_DEBUG;

public class RetrofitUtil {

    private static RetrofitUtil util;
    private UserService userService;

    public static RetrofitUtil getInstance() {
        if (util == null) {
            synchronized (RetrofitUtil.class) {
                if (util == null) {
                    util = new RetrofitUtil();
                }
            }
        }
        return util;
    }

    private RetrofitUtil() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(IS_DEBUG ? HttpLoggingInterceptor.Level.BODY:HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(interceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())

                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        userService = retrofit.create(UserService.class);
    }

    public UserService userService() {
        return userService;
    }

}
