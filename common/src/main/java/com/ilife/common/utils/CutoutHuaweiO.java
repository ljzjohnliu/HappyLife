package com.ilife.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CutoutHuaweiO extends BaseCutout {
    public static final int FLAG_NOTCH_SUPPORT = 65536;

    public CutoutHuaweiO() {
    }

    public void enterFullScreenDisplay(Activity activity) {
        if (activity != null) {
            Window window = activity.getWindow();
            this.addWindowFlags(window);
        }
    }

    public void exitFullScreenDisplay(Activity activity) {
        if (activity != null) {
            Window window = activity.getWindow();
            this.clearWindowFlags(window);
        }
    }

    @TargetApi(19)
    private void addWindowFlags(Window window) {
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        try {
            Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj = con.newInstance(layoutParams);
            Method method = layoutParamsExCls.getMethod("addHwFlags", Integer.TYPE);
            method.invoke(layoutParamsExObj, 65536);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException var7) {
            var7.printStackTrace();
        }

    }

    @TargetApi(19)
    private void clearWindowFlags(Window window) {
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        try {
            Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj = con.newInstance(layoutParams);
            Method method = layoutParamsExCls.getMethod("clearHwFlags", Integer.TYPE);
            method.invoke(layoutParamsExObj, 65536);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException var7) {
            var7.printStackTrace();
        }

    }
}

