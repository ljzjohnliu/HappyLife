package com.ilife.common.basemvp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseFragment<P extends BasePresenter, CONTRACT> extends Fragment implements IBaseView {

    protected P presenter;
    protected CompositeDisposable compositeDisposable;

    protected View mRootView;
    protected LayoutInflater mInflater;
    protected Context mContext;

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

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null)
                parent.removeView(mRootView);
        } else {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;
            ButterKnife.bind(this, mRootView);
            initView();
            initData();
        }

        return mRootView;
    }

    @Override
    public void onDetach() {
        mContext = null;
        super.onDetach();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
}
