package com.ilife.common.basemvp;

public interface IBaseView<P extends BasePresenter, CONTRACT>  {
    CONTRACT getContract();
    P getPresenter();
}
