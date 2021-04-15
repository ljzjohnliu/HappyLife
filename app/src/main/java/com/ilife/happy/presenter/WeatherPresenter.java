package com.ilife.happy.presenter;

import com.ilife.common.basemvp.BasePresenter;
import com.ilife.common.model.BaseEntity;
import com.ilife.happy.bean.WeatherInfo;
import com.ilife.happy.bean.WeatherInfoData;
import com.ilife.happy.contract.IWeatherContract;
import com.ilife.happy.fragment.WeatherFragment;
import com.ilife.happy.model.WeatherModel;

public class WeatherPresenter extends BasePresenter<WeatherFragment, WeatherModel,IWeatherContract.Presenter> {
    @Override
    public IWeatherContract.Presenter getContract() {
        return new IWeatherContract.Presenter<WeatherInfoData>() {
            @Override
            public void weatherApi(String location) {
                try {
                    //Delegate the data request to corresponding model
                    getModel().getContract().executeWeatherApi(location);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseResult(WeatherInfoData weatherInfoData) {
                getView().getContract().onResult(weatherInfoData);
            }
        };
    }

    @Override
    public WeatherModel getModel() {
        return new WeatherModel(this);
    }
}
