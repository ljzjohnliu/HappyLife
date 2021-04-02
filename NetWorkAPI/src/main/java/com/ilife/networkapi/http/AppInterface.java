package com.ilife.networkapi.http;


import com.ilife.networkapi.model.BaseResponse;
import com.ilife.networkapi.model.GlobalConfigurationInfo;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.POST;

public interface AppInterface {

    @POST("/h/app/startup")
    Observable<BaseResponse<GlobalConfigurationInfo>> getGlobalConfiguration();

}
