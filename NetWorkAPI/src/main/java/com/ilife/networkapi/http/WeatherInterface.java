package com.ilife.networkapi.http;

import com.google.gson.JsonObject;
import com.ilife.networkapi.model.WeatherInfoData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherInterface {

    /**
     * 获取天气信息
     *
     * @return 请求结果以 Json 形式返回
     */
    @GET("data/cityinfo/101020100.html")
    Call<JsonObject> getWeaterAsJson();

    /**
     * 获取天气信息
     *
     * @return 请求结果以 WeatherInfoData 形式返回
     */
    @GET("data/cityinfo/101020100.html")
    Call<WeatherInfoData> getWeaterInfo();

    @GET("data/cityinfo/101020100.html")
    Observable<JsonObject> getWeaterUseRxjavaAsJson();

    @GET("data/cityinfo/101020100.html")
    Observable<WeatherInfoData> getWeaterAsWeatherInfo();
}