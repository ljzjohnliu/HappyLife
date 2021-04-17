package com.ilife.networkapi.http;

import com.google.gson.JsonObject;
import com.ilife.networkapi.ConstantUrl;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HeWeatherInterface {

    @GET("v7/weather/now?key="+ ConstantUrl.HE_FENG_WEATHER_KEY)
    Observable<JsonObject> getHeWeaterUseRxjavaAsJson(@Query("location") String location);
}
