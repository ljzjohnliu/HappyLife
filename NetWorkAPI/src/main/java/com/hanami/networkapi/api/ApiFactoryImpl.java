package com.hanami.networkapi.api;

import com.hanami.networkapi.http.LoginRegistryInterface;
import com.hanami.networkapi.http.WeatherInterface;
import com.ilife.common.http.HttpClient;

import retrofit2.Retrofit;

public class ApiFactoryImpl extends IApiFactory {
    public ApiFactoryImpl() {

    }

    @Override
    public <T> T makeApiClient(Class<T> cls) {
        if (cls == LoginRegistryInterface.class) {
            Retrofit qxRetrofit = HttpClient.getInstance().getRetrofit();
            return qxRetrofit.create(cls);
        } else if (cls == WeatherInterface.class) {
            Retrofit qxRetrofit = HttpClient.getInstance().getWeatherRetrofit("http://www.weather.com.cn/");
            return qxRetrofit.create(cls);
        } else {
            throw new UnsupportedOperationException("Unsupported Api class Type");
        }
    }
}
