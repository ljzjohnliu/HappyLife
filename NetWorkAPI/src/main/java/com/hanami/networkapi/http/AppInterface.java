package com.hanami.networkapi.http;

import com.hanami.networkapi.model.BaseResponse;
import com.hanami.networkapi.model.GlobalConfigurationInfo;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.POST;

public interface AppInterface {

    @POST("/h/app/startup")
    Observable<BaseResponse<GlobalConfigurationInfo>> getGlobalConfiguration();

}
