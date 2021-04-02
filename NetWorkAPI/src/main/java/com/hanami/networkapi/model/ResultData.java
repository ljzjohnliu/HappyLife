package com.hanami.networkapi.model;

import android.util.Log;

public class ResultData {
    // 根据返回数据的格式和数据解析方式（Json、XML等）定义
    private String msg;

    public void show() {
        Log.d("ljz", "ResultData, show: " + msg);
    }
}