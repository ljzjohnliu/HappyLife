package com.ilife.happy.model;

import android.util.Log;

import com.google.gson.JsonObject;
import com.ilife.common.basemvp.BaseModel;
import com.ilife.happy.contract.IWeatherContract;
import com.ilife.happy.presenter.WeatherPresenter;
import com.ilife.networkapi.api.ApisManager;
import com.ilife.networkapi.http.HeWeatherInterface;

import io.reactivex.rxjava3.core.Observable;

public class WeatherModel extends BaseModel<WeatherPresenter, IWeatherContract.Model> {
    public WeatherModel(WeatherPresenter presenter) {
        super(presenter);
    }

    @Override
    public IWeatherContract.Model getContract() {
        return new IWeatherContract.Model() {
            @Override
            public Observable<JsonObject> executeWeatherApi(String location) throws Exception {
                Log.d("TAG", "executeWeatherApi: location = " + location);
                Observable<JsonObject> data = ApisManager.getInstance().getApi(HeWeatherInterface.class).getHeWeaterUseRxjavaAsJson(location);
                return data;
            }
        };
    }
}
