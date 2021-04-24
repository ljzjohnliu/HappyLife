package com.ilife.happy.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ilife.common.basemvp.BasePresenter;
import com.ilife.happy.bean.WeatherInfo;
import com.ilife.happy.bean.WeatherInfoData;
import com.ilife.happy.contract.IWeatherContract;
import com.ilife.happy.fragment.WeatherFragment;
import com.ilife.happy.model.WeatherModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WeatherPresenter extends BasePresenter<WeatherFragment, WeatherModel,IWeatherContract.Presenter> {
    @Override
    public IWeatherContract.Presenter getContract() {
        return new IWeatherContract.Presenter<WeatherInfoData>() {
            @Override
            public void weatherApi(String type,String location) {
                try {
                    //Delegate the data request to corresponding model
                    Observable<JsonObject> data = getModel().getContract().executeWeatherApi(location);
                    data.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                Log.d("TAG", "WeatherFragment: response = " + response);
                                WeatherInfo gson = new Gson().fromJson(response, new TypeToken<WeatherInfo>() {
                                }.getType());
                                WeatherInfoData mWeatherInfoData = new WeatherInfoData();
                                mWeatherInfoData.setmWeatherInfo(gson);
                                mWeatherInfoData.setSuccess(true);
                                getView().getContract().onResult(type,mWeatherInfoData);
                            }, throwable -> {
                                WeatherInfoData mWeatherInfoData = new WeatherInfoData();
                                mWeatherInfoData.setSuccess(true);
                                mWeatherInfoData.setError(throwable.getMessage());
                                Log.d("TAG", "WeatherFragment: throwable = " + throwable.getMessage());
                                getView().getContract().fail(type,throwable.getMessage());
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
    }

    @Override
    public WeatherModel getModel() {
        return new WeatherModel(this);
    }
}
