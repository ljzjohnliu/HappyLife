package com.ilife.happy.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static boolean checkPhoneNumber(String phoneNum) {
        return phoneNum.length() == 11 && checkValid(phoneNum, "^1[0-9]{10}$");
    }

    public static boolean checkVerifyCode(String verifyCode) {
        return verifyCode.length() == 6 && checkValid(verifyCode, "[0-9]{6}$");
    }

    private static boolean checkValid(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static int randomCode() {
        return (int) ((Math.random() * 9 + 1) * 1000);
    }

    /**
     * 毫秒时间戳转Date
     *
     * @param timestamp 13位的秒级别的时间戳
     * @return
     */
    public static String formatTime(double timestamp) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        return date;
    }

    public static String formatTime(String timestamp) {
        double time = Double.parseDouble(timestamp);
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
        return date;
    }

    public static int getAppVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String getAppVedrsionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }
}
