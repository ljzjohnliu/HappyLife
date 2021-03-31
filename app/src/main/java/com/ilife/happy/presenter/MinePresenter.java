package com.ilife.happy.presenter;

import com.ilife.common.base.BasePresenter;
import com.ilife.happy.bean.UserInfo;
import com.ilife.happy.contract.IMineContract;
import com.ilife.happy.fragment.MineFragment;
import com.ilife.happy.model.MineModel;

public class MinePresenter extends BasePresenter<MineFragment, MineModel, IMineContract.Presenter> {

    private final static String TAG = "MinePresenter";

    public MinePresenter() {
        super();
    }

    @Override
    public IMineContract.Presenter getContract() {
        return new IMineContract.Presenter<UserInfo>() {
            @Override
            public void personApi(String name, String pwd) {
                try {
                    //Delegate the data request to corresponding model
                    getModel().getContract().executePersonApi(name, pwd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseResult(UserInfo userInfo) {
                getView().getContract().onResult(userInfo);
            }
        };
    }

    @Override
    public MineModel getModel() {
        return new MineModel(this);
    }
}
