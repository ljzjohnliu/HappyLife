package com.ilife.happy.activity.test.threadpool;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ilife.happy.R;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThreadPoolActivity extends AppCompatActivity {
    private static final String TAG = "thread";

    //创建基本线程池
    final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(100));

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_thread_pool_layout);
        ButterKnife.bind(this);
    }

    private void initThreadPool() {

    }

    @OnClick(R.id.test_tread)
    public void onClick() {
        /**
         * 基本线程池使用
         */
        for (int i = 0; i < 30; i++) {
            final int finali = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        Log.d(TAG, "Thread, run: " + finali);
                        Log.d(TAG, "当前线程：" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        Log.d(TAG, "run: ");
                        e.printStackTrace();
                    }
                }
            };
            threadPoolExecutor.execute(runnable);
        }
    }
}
