package com.ilife.happy.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilife.common.basemvp.BaseModel;
import com.ilife.happy.bean.HomeInfo;
import com.ilife.happy.bean.WeatherInfo;
import com.ilife.happy.bean.WeatherInfoData;
import com.ilife.happy.contract.IWeatherContract;
import com.ilife.happy.presenter.WeatherPresenter;
import com.ilife.networkapi.api.ApisManager;
import com.ilife.networkapi.http.WeatherInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WeatherModel extends BaseModel<WeatherPresenter, IWeatherContract.Model> {
    public WeatherModel(WeatherPresenter presenter) {
        super(presenter);
    }

    @Override
    public IWeatherContract.Model getContract() {
        return new IWeatherContract.Model() {
            @Override
            public void executeWeatherApi(String location) throws Exception {
                Log.d("TAG", "executeWeatherApi: location = " + location);
                ApisManager.getInstance().getApi(WeatherInterface.class).getHeWeaterUseRxjavaAsJson(location)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            Log.d("TAG", "WeatherFragment: response = " + response);
                            WeatherInfo gson = new Gson().fromJson(response, new TypeToken<WeatherInfo>() {
                            }.getType());
                            WeatherInfoData mWeatherInfoData = new WeatherInfoData();
                            mWeatherInfoData.setmWeatherInfo(gson);
                            mWeatherInfoData.setSuccess(true);
                            presenter.getContract().responseResult(mWeatherInfoData);
                        }, throwable -> {
                            WeatherInfoData mWeatherInfoData = new WeatherInfoData();
                            mWeatherInfoData.setSuccess(true);
                            mWeatherInfoData.setError(throwable.getMessage());
                            Log.d("TAG", "WeatherFragment: throwable = " + throwable.getMessage());
                        });

            }
        };
    }
}
