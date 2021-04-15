package com.ilife.happy.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ilife.happy.R;
import com.ilife.networkapi.api.ApisManager;
import com.ilife.networkapi.http.LoginRegistryInterface;
import com.ilife.networkapi.http.WeatherInterface;
import com.ilife.networkapi.model.WeatherInfoData;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Just test net api!!!
 */
public class TestNetApiAvtivity extends AppCompatActivity {

    private static final String TAG = "TestNetApi";
    @BindView(R.id.result_tv)
    TextView resultTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_netapi);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.http_test_btn)
    public void onClick() {
//        testNetLogin();

//        getWeatherSyncRequest();
//        getWeatherAsyncRequest();
//        getWeaterUseRxjavaAsJson();
        getWeaterAsWeatherInfo();

    }

    private void testNetLogin() {
        ApisManager.getInstance().getApi(LoginRegistryInterface.class).login("liujianzhang", "123456")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d(TAG, "testNetLogin: response = " + response);
                }, throwable -> {
                    Log.d(TAG, "testNetLogin: throwable = " + throwable.getMessage());
                });
    }

    /**
     * Call 同步网络请求获取天气信息
     */
    private void getWeatherSyncRequest() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<JsonObject> call = ApisManager.getInstance().getApi(WeatherInterface.class).getWeaterAsJson();

        //同步请求
        try {
            Response<JsonObject> response = call.execute();
            Log.d(TAG, "getWeatherSyncRequest: response.body = " + response.body().toString());
            Gson mGson = new Gson();
            WeatherInfoData weatherInfoData = mGson.fromJson(response.body().toString(), WeatherInfoData.class);
            weatherInfoData.show();
            resultTv.setText(weatherInfoData.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "getWeatherSyncRequest, onFailure: 连接失败 : " + e.getMessage());
        }
    }

    /**
     * Call 异步网络请求获取天气信息
     */
    private void getWeatherAsyncRequest() {
        Call<WeatherInfoData> call = ApisManager.getInstance().getApi(WeatherInterface.class).getWeaterInfo();
        call.enqueue(new Callback<WeatherInfoData>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<WeatherInfoData> call, Response<WeatherInfoData> response) {
                //请求处理,输出结果
                WeatherInfoData weatherInfo = response.body();
                Log.d(TAG, "getWeatherAsyncRequest: weatherInfo = " + weatherInfo.toString());
                weatherInfo.show();
                resultTv.setText(weatherInfo.toString());
            }

            //请求失败时候的回调
            @Override
            public void onFailure(Call<WeatherInfoData> call, Throwable throwable) {
                Log.d(TAG, "getWeatherAsyncRequest, onFailure: 连接失败 : " + throwable.getMessage());
            }
        });
    }

    private void getWeaterUseRxjavaAsJson() {
        ApisManager.getInstance().getApi(WeatherInterface.class).getWeaterUseRxjavaAsJson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d(TAG, "getWeaterUseRxjavaAsJson: response = " + response);
                    resultTv.setText(response.toString());
                }, throwable -> {
                    Log.d(TAG, "getWeaterUseRxjavaAsJson: throwable = " + throwable.getMessage());
                });
    }

    private void getWeaterAsWeatherInfo() {
//        ApisManager.getInstance().getApi(WeatherInterface.class).getWeaterAsWeatherInfo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    Log.d(TAG, "getWeaterAsWeatherInfo: response = " + response);
//                    resultTv.setText(response.toString());
//                }, throwable -> {
//                    Log.d(TAG, "getWeaterAsWeatherInfo: throwable = " + throwable.getMessage());
//                });
    }
}
