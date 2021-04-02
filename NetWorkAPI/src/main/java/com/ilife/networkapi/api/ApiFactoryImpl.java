package com.ilife.networkapi.api;

import com.ilife.common.http.HttpClient;
import com.ilife.networkapi.http.LoginRegistryInterface;
import com.ilife.networkapi.http.WeatherInterface;

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