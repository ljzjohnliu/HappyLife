package com.ilife.happy.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ilife.common.base.BaseFragment;
import com.ilife.happy.R;
import com.ilife.happy.bean.HomeInfo;
import com.ilife.happy.contract.IHomeContract;
import com.ilife.happy.presenter.HomePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment<HomePresenter, IHomeContract.View> {

    private static final String TAG = "HomeFragment";

    private HomePresenter homePresenter;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public IHomeContract.View getContract() {
        return new IHomeContract.View() {
            @Override
            public void onResult(HomeInfo homeInfo) {
                Log.d(TAG, "onResult: homeInfo code = " + homeInfo.getCode() + ", getType = " + homeInfo.getType() + ", msg = " + homeInfo.getMsg() + ", isSuccess = " + homeInfo.isSuccess());
            }
        };
    }

    @Override
    public HomePresenter getPresenter() {
        if (homePresenter == null) {
            homePresenter = new HomePresenter();
        }
        return homePresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_home, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.tv_title)
    public void onClick() {
        getPresenter().getContract().homeApi(0, "mock test");
    }
}
