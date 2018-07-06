package com.gofar.component.basiclib.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.gofar.component.basiclib.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author lcf
 * @date 2018/6/5 17:39
 * @since 1.0
 */
public class Api {

    private Retrofit mRetrofit;

    private Api() {
    }

    public static Api getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private OkHttpClient.Builder buildOkHttpBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        return builder;
    }

    private void buildRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiPath.BASE_URL)
                .client(buildOkHttpBuilder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            buildRetrofit();
        }
        return mRetrofit;
    }

    public <T> T build(Class<T> cls) {
        return build(getRetrofit(), cls);
    }

    public <T> T build(Retrofit retrofit, Class<T> cls) {
        if (retrofit == null) {
            throw new IllegalArgumentException("Retrofit is null!");
        }
        return retrofit.create(cls);
    }

    private static class SingletonHolder {
        private static final Api INSTANCE = new Api();
    }
}
