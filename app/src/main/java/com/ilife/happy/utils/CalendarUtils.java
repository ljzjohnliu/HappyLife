package com.ilife.happy.utils;

import android.util.Log;

import com.ilife.happy.R;

public class CalendarUtils {

    private static final String TAG = "CalendarUtils";

    public static final int NOTE_TYPE_SPORT = 0;
    public static final int NOTE_TYPE_STUDY = 1;
    public static final int NOTE_TYPE_RELAX = 2;
    public static final int NOTE_TYPE_BABY = 3;

    //定义了两个数组，一个存放图片，一个存放省份
    public static int[] image = {R.mipmap.fire_1, R.mipmap.fire_2,
            R.mipmap.fire_3, R.mipmap.fire_4};
    public static String[] noteTypes = {"健身", "学习", "娱乐", "亲子"};

    public static int getMarkColor(int type) {
        int color = 0xFF000000;
        Log.d(TAG, "getMarkColor: type = " + type);
        switch (type) {
            case NOTE_TYPE_SPORT:
                color = 0xFF40db25;
                break;
            case NOTE_TYPE_STUDY:
                color = 0xFFe69138;
                break;
            case NOTE_TYPE_RELAX:
                color = 0xFFdf1356;
                break;
            case NOTE_TYPE_BABY:
                color = 0xFFbc13f0;
                break;
        }
        Log.d(TAG, "getMarkColor: color = " + color);
        return color;
    }
}
