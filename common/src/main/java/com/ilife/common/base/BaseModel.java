package com.ilife.common.base;

public abstract class BaseModel<P extends BasePresenter, CONTRACT> {

    protected P presenter;

    public BaseModel(P presenter) {
        this.presenter = presenter;
    }

    public abstract CONTRACT getContract();
}
