package com.ilife.happy.presenter;

import com.ilife.common.basemvp.BasePresenter;
import com.ilife.common.model.BaseEntity;
import com.ilife.happy.bean.WeatherInfo;
import com.ilife.happy.contract.IWeatherContract;
import com.ilife.happy.fragment.WeatherFragment;
import com.ilife.happy.model.WeatherModel;

public class WeatherPresenter extends BasePresenter<WeatherFragment, WeatherModel,IWeatherContract.Presenter> {
    @Override
    public IWeatherContract.Presenter getContract() {
        return new IWeatherContract.Presenter<WeatherInfo>() {
            @Override
            public void weatherApi(int type, String msg) {
                try {
                    //Delegate the data request to corresponding model
                    getModel().getContract().executeWeatherApi(type, msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseResult(WeatherInfo info) {
                getView().getContract().onResult(info);
            }
        };
    }

    @Override
    public WeatherModel getModel() {
        return new WeatherModel(this);
    }
}
