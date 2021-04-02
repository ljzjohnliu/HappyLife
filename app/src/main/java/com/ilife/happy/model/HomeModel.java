package com.ilife.happy.model;

import android.util.Log;

import com.ilife.common.basemvp.BaseModel;
import com.ilife.happy.bean.HomeInfo;
import com.ilife.happy.contract.IHomeContract;
import com.ilife.happy.presenter.HomePresenter;

public class HomeModel extends BaseModel<HomePresenter, IHomeContract.Model> {

    public HomeModel(HomePresenter presenter) {
        super(presenter);
    }

    @Override
    public IHomeContract.Model getContract() {
        return new IHomeContract.Model() {
            @Override
            public void executeHomeApi(int type, String msg) throws Exception {
                Log.d("TAG", "executeHomeApi: type = " + type + ", msg = " + msg);
                HomeInfo homeInfo = new HomeInfo(++type, msg + "deal with model");
                homeInfo.setSuccess(true);
                presenter.getContract().responseResult(homeInfo);
            }
        };
    }
}
