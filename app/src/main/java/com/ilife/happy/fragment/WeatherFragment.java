package com.ilife.happy.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.ilife.common.basemvp.BaseFragment;
import com.ilife.happy.R;
import com.ilife.happy.bean.WeatherInfo;
import com.ilife.happy.contract.IWeatherContract;
import com.ilife.happy.presenter.WeatherPresenter;
import com.ilife.happy.utils.IntentUtil;
import com.ilife.happy.utils.SettingUtils;

public class WeatherFragment extends BaseFragment<WeatherPresenter, IWeatherContract.View> {
    public static String TAG = "WeatherFragment";
    public static int REQUEST_CODE_LOCATION = 1001;
    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};

    private WeatherPresenter mWeatherPresenter;

    @Override
    public IWeatherContract.View getContract() {
        return new IWeatherContract.View() {
            @Override
            public void onResult(WeatherInfo weatherInfo) {
                Log.d(TAG, "onResult: weatherInfo code = " + weatherInfo.getCode() + ", getType = " + weatherInfo.getType() + ", msg = " + weatherInfo.getMsg() + ", isSuccess = " + weatherInfo.isSuccess());
            }
        };
    }

    @Override
    public WeatherPresenter getPresenter() {
        if (mWeatherPresenter == null) {
            mWeatherPresenter = new WeatherPresenter();
        }
        return mWeatherPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Bundle mBundle = this.getArguments();
        if (mBundle != null) {
            boolean weatherSelected = mBundle.getBoolean("weatherSelected");
            if (weatherSelected) {
                checkPermissions();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void checkPermissions() {
        boolean gsp = SettingUtils.isLocServiceEnable(getContext());
        Log.d(TAG, "checkPermissions: gps权限是否打开  === " + gsp);
        if (!SettingUtils.isLocServiceEnable(getContext())) {
            Toast.makeText(getContext(), "请打开GPS定位开关", Toast.LENGTH_LONG).show();
        } else {
            requestPermissions(permissions, REQUEST_CODE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //获取到了权限

        } else {
            //权限被拒绝
            new AlertDialog.Builder(getContext())
                    .setTitle("需要开启权限后才能使用")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            IntentUtil.goPermissionActivity(getContext());
                        }
                    })
                    .show();
        }
    }
}
