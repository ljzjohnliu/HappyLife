package com.ilife.common.utils;

import android.content.Context;
import android.provider.Settings;

import java.util.UUID;

public class DeviceUtils {
    public static String getDeviceId(Context context) {
        String androidId = DeviceUtils.getAndroidID(context);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)androidId.hashCode() << 32));
        return deviceUuid.toString();
    }

    private static String getAndroidID(Context context) {
        String id = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        return id == null ? "" : id;
    }
}
