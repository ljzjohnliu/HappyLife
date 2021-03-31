package com.ilife.common.base;

public interface IBaseView<P extends BasePresenter, CONTRACT>  {
    CONTRACT getContract();
    P getPresenter();
}
