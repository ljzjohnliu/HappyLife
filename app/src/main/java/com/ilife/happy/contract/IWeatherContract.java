package com.ilife.happy.contract;

import com.ilife.common.model.BaseEntity;
import com.ilife.happy.bean.HomeInfo;
import com.ilife.happy.bean.WeatherInfo;

public interface IWeatherContract {
    interface Model{
        void executeWeatherApi(int type, String msg) throws Exception;
    }
    interface View<T extends BaseEntity> {
        void onResult(WeatherInfo t);
    }

    interface Presenter<T extends BaseEntity> {
        void weatherApi(int type, String msg);

        void responseResult(T t);
    }
}
