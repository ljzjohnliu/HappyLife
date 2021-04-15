package com.ilife.happy.contract;

import com.ilife.common.model.BaseEntity;
import com.ilife.happy.bean.HomeInfo;
import com.ilife.happy.bean.WeatherInfo;
import com.ilife.happy.bean.WeatherInfoData;

public interface IWeatherContract {
    interface Model{
        void executeWeatherApi(String location) throws Exception;
    }
    interface View<T extends BaseEntity> {
        void onResult(WeatherInfoData t);
    }

    interface Presenter<T extends BaseEntity> {
        void weatherApi(String location);

        void responseResult(T t);
    }
}
