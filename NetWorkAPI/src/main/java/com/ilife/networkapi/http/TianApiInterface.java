package com.ilife.networkapi.http;

import com.google.gson.JsonObject;
import com.ilife.networkapi.ConstantUrl;
import com.ilife.networkapi.model.FamousData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 天行数据
 * 提供免费名言警句等api
 * https://www.tianapi.com/list/5
 */
public interface TianApiInterface {
//    @GET("txapi/dictum/index?key="+ ConstantUrl.FAMOUS_APHORISM_KEY)
//    Observable<JsonObject> getFamousAphorismData(@Query("num") String num);

    @GET("txapi/dictum/index?key="+ ConstantUrl.FAMOUS_APHORISM_KEY)
    Observable<FamousData> getFamousAphorismData(@Query("num") String num);
}
