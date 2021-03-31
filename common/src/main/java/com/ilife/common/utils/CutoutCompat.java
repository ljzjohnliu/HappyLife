package com.ilife.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CutoutCompat {
    private static final String TAG = "cutout";
    private static final int CUTOUT_IN_SCREEN_VOIO = 32;
    private static boolean sHasCuroutScreen = false;
    private static boolean sHasInitLowerP = false;
    private static boolean sHasInitUpperP = false;
    private static BaseCutout sCutout;

    public CutoutCompat() {
    }



    public static boolean hasCutoutInHuaweiScreen(@NonNull Context context) {
        boolean ret = false;

        try {
            ClassLoader cl = context.getClassLoader();
            Class hwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = hwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (Boolean)get.invoke(hwNotchSizeUtil);
        } catch (ClassNotFoundException var11) {
            Log.v("cutout", "hasCutoutInHuaweiScreen ClassNotFoundException");
        } catch (NoSuchMethodException var12) {
            Log.v("cutout", "hasCutoutInHuaweiScreen NoSuchMethodException");
        } catch (IllegalAccessException var13) {
            Log.v("cutout", "hasCutoutInHuaweiScreen IllegalAccessException");
        } catch (InvocationTargetException var14) {
            Log.v("cutout", "hasCutoutInHuaweiScreen InvocationTargetException");
        } finally {
            Log.d("cutout", "hasCutoutInHuaweiScreen; result=" + ret);
        }

        return ret;
    }

    public static boolean hasCutoutInMiScreen(@NonNull Context context) {
        boolean ret = false;
        ClassLoader cl = context.getClassLoader();

        try {
            Class systemProperties = cl.loadClass("android.os.SystemProperties");
            Method get = systemProperties.getMethod("get", String.class);
            String value = (String)get.invoke(systemProperties, "ro.miui.notch");
            ret = "1".equals(value);
        } catch (ClassNotFoundException var12) {
            Log.v("cutout", "hasCutoutInMiScreen ClassNotFoundException");
        } catch (NoSuchMethodException var13) {
            Log.v("cutout", "hasCutoutInMiScreen NoSuchMethodException");
        } catch (IllegalAccessException var14) {
            Log.v("cutout", "hasCutoutInMiScreen IllegalAccessException");
        } catch (InvocationTargetException var15) {
            Log.v("cutout", "hasCutoutInMiScreen InvocationTargetException");
        } finally {
            Log.d("cutout", "hasCutoutInMiScreen; result=" + ret);
        }

        return ret;
    }

    public static boolean hasCutoutInOppoScreen(@NonNull Context context) {
        boolean ret = false;

        try {
            ret = context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
        } catch (RuntimeException var3) {
            ret = false;
        }

        Log.d("cutout", "hasCutoutInOppoScreen; result=" + ret);
        return ret;
    }

    public static boolean hasCutoutInVivoScreen(@NonNull Context context) {
        boolean ret = false;

        try {
            ClassLoader cl = context.getClassLoader();
            Class ftFeature = cl.loadClass("android.util.FtFeature");
            Method get = ftFeature.getMethod("isFeatureSupport", Integer.TYPE);
            ret = (Boolean)get.invoke(ftFeature, 32);
        } catch (ClassNotFoundException var11) {
            Log.v("cutout", "hasCutoutInVivoScreen ClassNotFoundException");
        } catch (NoSuchMethodException var12) {
            Log.v("cutout", "hasCutoutInVivoScreen NoSuchMethodException");
        } catch (IllegalAccessException var13) {
            Log.v("cutout", "hasCutoutInVivoScreen IllegalAccessException");
        } catch (InvocationTargetException var14) {
            Log.v("cutout", "hasCutoutInVivoScreen InvocationTargetException");
        } finally {
            Log.d("cutout", "hasCutoutInLenovoScreen; result=" + ret);
        }

        return ret;
    }

    public static boolean hasCutoutInSamsungScreen(@NonNull Context context) {
        Resources res = context.getResources();
        int resId = res.getIdentifier("config_mainBuiltInDisplayCutout", "string", "android");
        String spec = resId > 0 ? res.getString(resId) : "";
        boolean ret = !TextUtils.isEmpty(spec);
        Log.i("cutout", "hasCutoutInSamsungScreen; result=" + ret);
        return ret;
    }

    public static boolean hasCutoutInLenovoScreen(@NonNull Context context) {
        boolean ret = false;

        try {
            Class<?> cl = Class.forName("com.android.internal.R$bool");
            Field field = cl.getField("config_screen_has_notch");
            Object obj = cl.newInstance();
            field.setAccessible(true);
            ret = context.getResources().getBoolean(field.getInt(obj));
        } catch (ClassNotFoundException var11) {
            Log.v("cutout", "hasCutoutInLenovoScreen ClassNotFoundException");
        } catch (NoSuchFieldException var12) {
            Log.v("cutout", "hasCutoutInLenovoScreen NoSuchFieldException");
        } catch (IllegalAccessException var13) {
            Log.v("cutout", "hasCutoutInLenovoScreen IllegalAccessException");
        } catch (InstantiationException var14) {
            Log.v("cutout", "hasCutoutInLenovoScreen InstantiationException");
        } finally {
            Log.d("cutout", "hasCutoutInLenovoScreen; result=" + ret);
        }

        return ret;
    }

    public static boolean hasCutoutLowerP(Context context) {
        Log.d("cutout", "hasCutoutLowerP");

        if (context == null) {
            return false;
        } else if (sHasInitLowerP) {
            return sHasCuroutScreen;
        } else {
            boolean flag = hasCutoutInHuaweiScreen(context)
                    || hasCutoutInMiScreen(context)
                    || hasCutoutInOppoScreen(context)
                    || hasCutoutInVivoScreen(context)
                    || hasCutoutInSamsungScreen(context)
                    || hasCutoutInLenovoScreen(context)
                    || hasCutoutInHaiXinScreen(context);
            sHasCuroutScreen = flag;
            sHasInitLowerP = true;
            return flag;
        }
    }

    public static boolean hasCutoutInHaiXinScreen(@NonNull Context context) {
        boolean ret = false;

        try {
            ClassLoader cl = context.getClassLoader();
            Class systemProperties = cl.loadClass("android.os.SystemProperties");
            Method get = systemProperties.getMethod("get", String.class, String.class);
            String height = (String)((String)get.invoke(cl, "ro.hmct.notch_height", "0"));
            ret = !"0".equals(height);
        } catch (ClassNotFoundException var12) {
            Log.v("cutout", "hasCutoutInHaiXinScreen ClassNotFoundException");
        } catch (NoSuchMethodException var13) {
            Log.v("cutout", "hasCutoutInHaiXinScreen NoSuchMethodException");
        } catch (IllegalAccessException var14) {
            Log.v("cutout", "hasCutoutInHaiXinScreen IllegalAccessException");
        } catch (InvocationTargetException var15) {
            Log.v("cutout", "hasCutoutInHaiXinScreen InvocationTargetException");
        } finally {
            Log.d("cutout", "hasCutoutInLenovoScreen; result=" + ret);
        }

        return ret;
    }

    @TargetApi(28)
    public static boolean hasCutoutifApiUpperP(@NonNull Activity activity) {
        Log.d("cutout", "hasCutoutifApiUpperP");

        if (sHasInitUpperP) {
            return sHasCuroutScreen;
        } else {
            View decorView = activity.getWindow().getDecorView();
            sHasCuroutScreen = hasCutoutifApiUpperP(decorView);
            sHasInitUpperP = true;
            return sHasCuroutScreen;
        }
    }

    @TargetApi(28)
    public static boolean hasCutoutifApiUpperP(@NonNull View view) {
        Log.d("cutout", "hasCutoutifApiUpperP");

        if (sHasInitUpperP) {
            return sHasCuroutScreen;
        } else if (!view.isAttachedToWindow()) {
            return false;
        } else {
            WindowInsets insets = view.getRootWindowInsets();
            sHasCuroutScreen = insets != null && insets.getDisplayCutout() != null;
            Log.d("cutout", "hasCutoutifApiUpperP; result=" + sHasCuroutScreen);
            sHasInitUpperP = true;
            return sHasCuroutScreen;
        }
    }

    /** @deprecated */
    @Deprecated
    public static boolean hasCutout(View view) {
        return Build.VERSION.SDK_INT >= 28 ? hasCutoutifApiUpperP(view) : hasCutoutLowerP(view.getContext());
    }

    public static boolean hasCutout(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            return Build.VERSION.SDK_INT >= 28
                    ? hasCutoutifApiUpperP(activity.getWindow().getDecorView())
                    : hasCutoutLowerP(activity);
        } else {
            return false;
        }
    }

    public static void enterFullScreenDisplay(Activity activity) {
        sCutout = initCutoutIfNull(activity);
        sCutout.enterFullScreenDisplay(activity);
    }

    public static void exitFullScreenDisplay(Activity activity) {
        sCutout = initCutoutIfNull(activity);
        sCutout.exitFullScreenDisplay(activity);
    }

    private static BaseCutout initCutoutIfNull(Activity activity) {
        if (sCutout != null) {
            return sCutout;
        } else {
            if (Build.VERSION.SDK_INT >= 28) {
                if (hasCutoutifApiUpperP(activity)) {
                    sCutout = new CutoutAndroidP();
                    Log.i("cutout", "sCutout = CutoutAndroidP.");
                } else {
                    sCutout = new BaseCutout();
                    Log.i("cutout", "sCutout = BaseCutout, api>=28.");
                }
            } else if (hasCutoutInHuaweiScreen(activity)) {
                sCutout = new CutoutHuaweiO();
                Log.i("cutout", "sCutout = CutoutHuaweiO.");
            } else if (hasCutoutInMiScreen(activity)) {
                sCutout = new CutoutXiaomiO();
                Log.i("cutout", "sCutout = CutoutXiaomiO.");
            } else {
                sCutout = new BaseCutout();
                Log.i("cutout", "sCutout = BaseCutout, api< 28.");
            }

            return sCutout;
        }
    }
}
