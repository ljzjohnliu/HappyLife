package com.ilife.happy.model;

import android.util.Log;

import com.ilife.common.basemvp.BaseModel;
import com.ilife.happy.bean.UserInfo;
import com.ilife.happy.contract.IMineContract;
import com.ilife.happy.presenter.MinePresenter;

public class MineModel extends BaseModel<MinePresenter, IMineContract.Model> {

    public MineModel(MinePresenter presenter) {
        super(presenter);
    }

    @Override
    public IMineContract.Model getContract() {
        return new IMineContract.Model() {
            @Override
            public void executePersonApi(String arg1, String arg2) throws Exception {
                Log.d("TAG", "executeHomeApi: arg1 = " + arg1 + ", arg2 = " + arg2);
                presenter.getContract().responseResult(new UserInfo(arg1, arg2, 0));
            }
        };
    }
}
