package com.ilife.happy.crash;

import android.util.Log;

import androidx.annotation.NonNull;

public class ILifeUncaughtException implements Thread.UncaughtExceptionHandler {
    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        e.printStackTrace();
        Log.e("ILifeUncaughtException", "ILifeCrash: " + e.getStackTrace());
    }
}
