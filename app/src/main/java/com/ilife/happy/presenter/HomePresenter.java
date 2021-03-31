package com.ilife.happy.presenter;

import com.ilife.common.base.BasePresenter;
import com.ilife.happy.bean.HomeInfo;
import com.ilife.happy.contract.IHomeContract;
import com.ilife.happy.fragment.HomeFragment;
import com.ilife.happy.model.HomeModel;

public class HomePresenter extends BasePresenter<HomeFragment, HomeModel, IHomeContract.Presenter> {

    private final static String TAG = "HomePresenter";

    public HomePresenter() {
        super();
    }

    @Override
    public IHomeContract.Presenter getContract() {
        return new IHomeContract.Presenter<HomeInfo>() {
            @Override
            public void homeApi(int type, String msg) {
                try {
                    //Delegate the data request to corresponding model
                    getModel().getContract().executeHomeApi(type, msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseResult(HomeInfo userInfo) {
                getView().getContract().onResult(userInfo);
            }
        };
    }

    @Override
    public HomeModel getModel() {
        return new HomeModel(this);
    }
}
