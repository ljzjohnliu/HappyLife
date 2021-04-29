package com.ilife.happy.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.ilife.common.base.BaseSimpleActivity;
import com.ilife.dataroom.RoomDemoDatabase;
import com.ilife.dataroom.dao.FamousAphorismDao;
import com.ilife.dataroom.model.FamousAphorismModel;
import com.ilife.happy.R;
import com.ilife.networkapi.api.ApisManager;
import com.ilife.networkapi.http.TianApiInterface;
import com.ilife.networkapi.model.FamousData;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashActivity extends BaseSimpleActivity {
    public static String TAG = "SplashActivity";

    private int count = 5;

    private TextView mFamousTxt;
    private TextView mFamousNameTxt;
    private TextView mCountTxt;

    private RoomDemoDatabase roomDemoDatabase;
    private FamousAphorismDao mFamousAphorismDao;

    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int count = msg.arg1;
            if (count >= 0) {
                mCountTxt.setText(count + "秒");
                if(count == 0){
                    gotoMian();
                }
            }

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash_layout;
    }


    @Override
    protected void initView() {
        mFamousTxt = findViewById(R.id.famous_content_txt);
        mFamousNameTxt = findViewById(R.id.famous_name_txt);
        mCountTxt = findViewById(R.id.timer_txt);
        mCountTxt.setText("");
    }

    @Override
    protected void initData() {
        roomDemoDatabase = RoomDemoDatabase.getInstance(this);
        mFamousAphorismDao = roomDemoDatabase.famousAphorismDao();
        count = 5;
        getFamousData();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.arg1 = count;
                if (count != -1) {
                    count--;
                } else {
                    return;
                }
                mHandle.sendMessage(message);
            }
        }, 1000, 1000);
    }

    public void gotoMian(){
        Intent intent = new Intent(this,CenterFabActivity.class);
        startActivity(intent);
    }
    public void getFamousData() {
        int count = mFamousAphorismDao.count();
        Log.d(TAG, "getFamousData: 数据库中名言警句 count  " + count);
        queryAllData();
        FamousAphorismModel queryModelData = mFamousAphorismDao.queryFamousAphorism(false);
        if(queryModelData != null){
            Log.d(TAG, "getFamousData: 查询数据库中未读取的数据======");
            showFamousAphorism(queryModelData.content,queryModelData.mrName);
            queryModelData.showTag = true;
            mFamousAphorismDao.updateFamousAphorism(queryModelData);
        }else{
            Log.d(TAG, "getFamousData: 请求名言警句api====");
            String num = "10";
            ApisManager.getInstance().getApi(TianApiInterface.class).getFamousAphorismData(num)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        Log.d(TAG, "testNetLogin: response = " + response);
                        if (response != null && response.getCode() == 200) {
                            List<FamousData.FamousContentData> listData = response.getNewslist();
                            if (listData != null) {
                                FamousData.FamousContentData data = listData.get(0);
                                String mContent = data.getContent();
                                String name = data.getMrname();
                                showFamousAphorism(mContent,name);
                            }
                            for (int i = 1; i < listData.size(); i++) {
                                FamousAphorismModel model = new FamousAphorismModel(listData.get(i).getContent(),listData.get(i).getMrname(),false);
                                mFamousAphorismDao.insertOneFamousAphorism(model);
                            }
                            queryAllData();
                        }
                    }, throwable -> {
                        Log.d(TAG, "testNetLogin: throwable = " + throwable.getMessage());
                    });
        }

    }

    public void queryAllData(){
        List<FamousAphorismModel> models = mFamousAphorismDao.queryAll();
        for (int i = 0; i < models.size(); i++){
            FamousAphorismModel modelData = models.get(i);
            Log.d(TAG, "queryAllData 查询数据的数据 getFamousData: content  " +modelData.content + "  mrName   " +modelData.mrName+"  showTag  " +modelData.showTag);
        }
    }

    public void showFamousAphorism(String content,String name){
        if (!TextUtils.isEmpty(content)) {
            mFamousTxt.setText("     " + content);
        }
        if (!TextUtils.isEmpty(name)) {
            mFamousNameTxt.setText("——" + name);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
