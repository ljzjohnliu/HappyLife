package com.ilife.happy.model;

import android.util.Log;

import com.ilife.common.basemvp.BaseModel;
import com.ilife.happy.bean.HomeInfo;
import com.ilife.happy.bean.WeatherInfo;
import com.ilife.happy.contract.IWeatherContract;
import com.ilife.happy.presenter.WeatherPresenter;

public class WeatherModel extends BaseModel<WeatherPresenter, IWeatherContract.Model> {
    public WeatherModel(WeatherPresenter presenter) {
        super(presenter);
    }

    @Override
    public IWeatherContract.Model getContract() {
        return new IWeatherContract.Model() {
            @Override
            public void executeWeatherApi(int type, String msg) throws Exception {
                Log.d("TAG", "executeWeatherApi: type = " + type + ", msg = " + msg);
                WeatherInfo weatherInfo = new WeatherInfo(++type, msg + "deal with model");
                weatherInfo.setSuccess(true);
                presenter.getContract().responseResult(weatherInfo);
            }
        };
    }
}
