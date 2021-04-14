package com.ilife.common.http;

import android.content.Context;

import com.ilife.common.BuildConfig;
import com.ilife.common.endpoint.EndPointHost;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private Context context;

    private Retrofit retrofit;

    private Retrofit.Builder builder;

    private static class SingletonHolder {

        private static final HttpClient INSTANCE = new HttpClient();
    }

    public static HttpClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void setContext(Context mContext) {
        this.context = mContext.getApplicationContext();
    }

    public Context getContext() {
        return context;
    }

    public static void init(Context context, File sysCacheDir) {
        File clientCacheDir = new File(sysCacheDir, HttpConnection.DEFAULT_CACHE_DIR);
        getInstance().setContext(context);
        getInstance().setupRetrofit(clientCacheDir);
    }

    public Retrofit getRetrofit() {
        retrofit = builder.baseUrl(EndPointHost.HOST).build();
        return retrofit;
    }

    public Retrofit getCustomRetrofit(String baseUrl) {
        retrofit = builder.baseUrl(baseUrl).build();
        return retrofit;
    }

    private void setupRetrofit(File cacheDir) {
        //Create OkHttp client
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.cache(new Cache(cacheDir, HttpConnection.DEFAULT_CACHE_SIZE));
        httpClientBuilder.connectTimeout(HttpConnection.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(HttpConnection.READ_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new GlobalParametersInterceptor(context));
        httpClientBuilder.addInterceptor(new AuthCookieExpiredInterceptor());
        httpClientBuilder.addInterceptor(new ParametersInterceptor());
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addNetworkInterceptor(loggingInterceptor);
        }

        //Create retrofit instance
        builder = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create());

//        retrofit = builder.build();
    }

    interface HttpConnection {

        //Unit: second
        int DEFAULT_TIMEOUT = 5;
        int READ_TIMEOUT = 30;

        String DEFAULT_CACHE_DIR = "ilife";
        int DEFAULT_CACHE_SIZE = 1024 * 1024 * 10; //Cache Size: 10MB

        int DEFAULT_FIRST_PAGE_INDEX = 1;
        int DEFAULT_PAGE_SIZE = 20;
    }
}
