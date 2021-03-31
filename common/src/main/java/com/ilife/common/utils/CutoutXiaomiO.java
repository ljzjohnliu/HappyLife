package com.ilife.common.utils;

import android.app.Activity;
import android.view.Window;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CutoutXiaomiO extends BaseCutout {
    public CutoutXiaomiO() {
    }

    public void enterFullScreenDisplay(Activity activity) {
        if (activity != null) {
            Window window = activity.getWindow();
            this.addWindowExtraFlags(window);
        }
    }

    public void exitFullScreenDisplay(Activity activity) {
        if (activity != null) {
            Window window = activity.getWindow();
            this.clearWindowExtraFlags(window);
        }
    }

    private void clearWindowExtraFlags(Window window) {
        short flag = 1792;

        try {
            Method method = Window.class.getMethod("clearExtraFlags", Integer.TYPE);
            method.invoke(window, Integer.valueOf(flag));
        } catch (NoSuchMethodException var4) {
            var4.printStackTrace();
        } catch (IllegalAccessException var5) {
            var5.printStackTrace();
        } catch (InvocationTargetException var6) {
            var6.printStackTrace();
        }

    }

    private void addWindowExtraFlags(Window window) {
        short flag = 1792;

        try {
            Method method = Window.class.getMethod("addExtraFlags", Integer.TYPE);
            method.invoke(window, Integer.valueOf(flag));
        } catch (NoSuchMethodException var4) {
            var4.printStackTrace();
        } catch (IllegalAccessException var5) {
            var5.printStackTrace();
        } catch (InvocationTargetException var6) {
            var6.printStackTrace();
        }

    }
}

