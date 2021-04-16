package com.ilife.networkapi.http;

import com.google.gson.JsonObject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HeWeatherInterface {

    @GET("v7/weather/now?key=55d18cc792074a2faa4d4578382ada7d")
    Observable<JsonObject> getHeWeaterUseRxjavaAsJson(@Query("location") String location);
}
