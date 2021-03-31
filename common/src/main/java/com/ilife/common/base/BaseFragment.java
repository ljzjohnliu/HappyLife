package com.ilife.common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseFragment<P extends BasePresenter, CONTRACT> extends Fragment implements IBaseView {

    protected P presenter;
    protected CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = getPresenter();
        presenter.bindView(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unBindView();
        compositeDisposable.dispose();
    }

    @Override
    public abstract CONTRACT getContract();

    @Override
    public abstract P getPresenter();
}
