package com.ilife.happy.contract;

import com.google.gson.JsonObject;
import com.ilife.common.model.BaseEntity;
import com.ilife.happy.bean.WeatherInfoData;

import io.reactivex.rxjava3.core.Observable;

public interface IWeatherContract {
    interface Model{
        Observable<JsonObject> executeWeatherApi(String location) throws Exception;
    }
    interface View {
        //type 是请求天气类型数据参数
        void onResult(String type,WeatherInfoData t);

        void fail(String type,String t);
    }

    interface Presenter<T extends BaseEntity> {
        //请求数据类型 请求的是当天天气数据
        void weatherApi(String type,String location);
    }
}
