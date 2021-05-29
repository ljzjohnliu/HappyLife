package com.ilife.happy.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.ilife.common.basemvp.BaseFragment;
import com.ilife.happy.R;
import com.ilife.happy.activity.test.TestCustomViewActivity;
import com.ilife.happy.bean.AddressBean;
import com.ilife.happy.bean.WeatherInfo;
import com.ilife.happy.bean.WeatherInfoData;
import com.ilife.happy.contract.IWeatherContract;
import com.ilife.happy.presenter.WeatherPresenter;
import com.ilife.happy.utils.IntentUtil;
import com.ilife.happy.utils.SettingUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherFragment extends BaseFragment<WeatherPresenter, IWeatherContract.View> {
    public static String TAG = "WeatherFragment";
    public static int REQUEST_CODE_LOCATION = 1001;
    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};

    private double mLongitude;
    private double mLatitude;
    private WeatherPresenter mWeatherPresenter;

    private Button mGpsBtn;
    private TextView mGpsTxt;
    private TextView mWeatherTemTxt;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            AddressBean mAddressBean = (AddressBean) msg.obj;
            mLongitude = mAddressBean.getLongitude();// 经度
            mLatitude = mAddressBean.getLatitude();// 纬度
            Log.d(TAG, "handleMessage:   longitude   " + mLongitude + " latitude   " + mLatitude);
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> result = null;
            try {
                result = geocoder.getFromLocation(mLatitude, mLongitude, 1);
                if (result != null) {
                    Log.d(TAG, "handleMessage:   result   " + result.get(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String location = mLongitude + "," + mLatitude;
            mWeatherPresenter.getContract().weatherApi("1", location);
        }
    };

    @Override
    public IWeatherContract.View getContract() {
        return new IWeatherContract.View() {
            @Override
            public void onResult(String type, WeatherInfoData weatherInfo) {
                Log.d(TAG, "onResult: weatherInfo code = " + weatherInfo.getmWeatherInfo().getCode());
                WeatherInfo mWeatherInfo = weatherInfo.getmWeatherInfo();
                WeatherInfo.Now mNow = mWeatherInfo.getNow();
                String temp = mNow.getTemp();
                mWeatherTemTxt.setText(temp);
            }

            @Override
            public void fail(String type, String t) {
                Log.d(TAG, "fail: 错误信息  " + t);
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
        mGpsBtn = mRootView.findViewById(R.id.gps_btn);
        mGpsTxt = mRootView.findViewById(R.id.gps_txt);
        mWeatherTemTxt = mRootView.findViewById(R.id.temperature_txt);

        mGpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG, "onClick: isLocationServiceEnabled " + isLocationServiceEnabled());
//                if (isLocationServiceEnabled()) {
//                    getLocation();
//                } else {
//                    Toast.makeText(getContext(), "定位失败", Toast.LENGTH_LONG).show();
//                }
////                mian();

                Intent intent = new Intent(getContext(), TestCustomViewActivity.class);
                startActivity(intent);
            }
        });

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


    public boolean isLocationServiceEnabled() {
        LocationManager mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean networkEnable = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean gpsEnable = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean passiveEnable = mLocationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
        boolean isable = false;
        if (networkEnable || gpsEnable) {
            isable = true;
        } else {
            isable = false;
        }
        return isable;
    }

    public void getLocation() {
        LocationManager mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria mCriteria = new Criteria();
        mCriteria.setAltitudeRequired(false);
        mCriteria.setBearingRequired(false);
        mCriteria.setCostAllowed(false);
        mCriteria.setPowerRequirement(Criteria.POWER_LOW);
        mCriteria.setAccuracy(Criteria.ACCURACY_COARSE);

        String providerName = "";
        List<String> providerList = mLocationManager.getProviders(true);
        if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            providerName = LocationManager.NETWORK_PROVIDER;
        } else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            providerName = LocationManager.GPS_PROVIDER;
        } else {
            if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "权限未授权，请先授权UHello定位权限", Toast.LENGTH_LONG).show();
                return;
            }
        }


        Location location = mLocationManager.getLastKnownLocation(providerName);
        if (location != null) {
            double longitude = location.getLongitude();// 经度
            double latitude = location.getLatitude();// 纬度

            Log.d(TAG, "longitude = " + longitude);
            Log.d(TAG, "latitude = " + latitude);
            mGpsTxt.setText("经度 " + longitude + "   纬度  " + latitude);
            Message message = new Message();
            AddressBean bean = new AddressBean();
            bean.setLongitude(longitude);
            bean.setLatitude(latitude);
            message.obj = bean;
            mHandler.sendMessage(message);

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
            getLocation();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public void mian() {
        List<String> images = new ArrayList<String>();
        images.add("AAAA");
        images.add("BBBB");
        images.add("CCCC");
        images.add("DDDD");
        images.add("EEEE");

        System.out.println("main image 1 is = " + images.get(1));

        for (int i = 0; i < images.size(); i = i++) {
            if (i == 1)
                continue;
            System.out.println("main image " + i + "is = " + images.get(i));

        }

    }
}
