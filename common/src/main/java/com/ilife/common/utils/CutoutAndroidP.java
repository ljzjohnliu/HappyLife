package com.ilife.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class CutoutAndroidP extends BaseCutout {
    public CutoutAndroidP() {
    }

    @TargetApi(28)
    public void enterFullScreenDisplay(Activity activity) {
        if (activity != null) {
            Window window = activity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.layoutInDisplayCutoutMode = 1;
            window.setAttributes(lp);
        }
    }

    @TargetApi(28)
    public void exitFullScreenDisplay(Activity activity) {
        if (activity != null) {
            Window window = activity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.layoutInDisplayCutoutMode = 0;
            window.setAttributes(lp);
        }
    }
}

