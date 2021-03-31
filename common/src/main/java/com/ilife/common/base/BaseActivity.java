package com.ilife.common.base;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.ilife.common.GlobalActivityMgr;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BaseActivity<P extends BasePresenter, CONTRACT> extends FragmentActivity implements IBaseView, CustomAdapt {

    protected P presenter;
    protected CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = getPresenter();
        presenter.bindView(this);
        compositeDisposable = new CompositeDisposable();
        GlobalActivityMgr.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        presenter.unBindView();
        GlobalActivityMgr.getInstance().removeActivity(this);
    }

    @Override
    public abstract CONTRACT getContract();

    @Override
    public abstract P getPresenter();

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 375;
    }
}
