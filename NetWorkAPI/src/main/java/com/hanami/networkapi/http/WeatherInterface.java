package com.hanami.networkapi.http;

import com.google.gson.JsonObject;
import com.hanami.networkapi.model.ResultData;
import com.hanami.networkapi.model.WeatherInfoData;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherInterface {

    /**
     * 获取天气信息
     *
     * @return 请求结果以 ResponseBody 形式返回
     */
    @GET("data/cityinfo/101020100.html")
    Call<ResponseBody> getWeater();

    /**
     * 获取天气信息
     *
     * @return 请求结果以 String 形式返回
     */
    @GET("data/cityinfo/101020100.html")
    Call<String> getWeaterAsString();

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

    @GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
    Call<ResultData> getCall();
    // @GET注解的作用:采用Get方法发送网络请求

    // getCall() = 接收网络请求数据的方法
    // 其中返回类型为Call<*>，*是接收数据的类（即上面定义的Translation类）
    // 如果想直接获得Responsebody中的内容，可以定义网络请求返回值为Call<ResponseBody>

    @GET("data/cityinfo/101020100.html")
    Observable<JsonObject> getWeaterUseRxjavaAsJson();

}