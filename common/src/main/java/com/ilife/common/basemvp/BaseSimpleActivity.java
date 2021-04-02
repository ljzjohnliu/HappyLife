package com.ilife.common.basemvp;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.ilife.common.GlobalActivityMgr;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import me.jessyan.autosize.internal.CustomAdapt;

public class BaseSimpleActivity extends FragmentActivity implements CustomAdapt {

    protected CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        GlobalActivityMgr.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        GlobalActivityMgr.getInstance().removeActivity(this);
    }

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 375;
    }
}
