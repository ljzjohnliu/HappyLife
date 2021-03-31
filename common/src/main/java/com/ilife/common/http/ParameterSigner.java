package com.ilife.common.http;

import android.text.TextUtils;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.ilife.common.http.GlobalParametersInterceptor.PARA_TAG;

public class ParameterSigner {

    private static final String APP_SECRET = "9b927e6f02e05d15a3d18894b56d04618e39d258";
    private static final String EXCLUDE_PARAM_SIGN = "sn";
    private static final String KEY_VAL_LINK = "";
    private static final String VAL_EMPTY_DEFAULT = "";

    public static String signParams(Request request) {
        Map<String, String> params = new HashMap<>();
        RequestBody body = request.body();
        if (body != null && body.contentType() != null) {
            if (body.contentType().equals(MediaType.parse("application/x-www-form-urlencoded"))) {
                FormBody formBody = (FormBody) body;
                for (int i = 0; i < formBody.size(); i++) {
                    params.put(formBody.name(i), formBody.value(i));
                }
            }
        }
        return sign(params);
    }

    public static String sign(Map<String, String> params) {
        return sign(params, APP_SECRET, Boolean.TRUE);
    }

    public static String sign(Map<String, String> params, String appSecret, boolean excludeEmpty) {
        SortedMap<String, String> sortedParams = new TreeMap<>(params);
        StringBuilder sb = new StringBuilder();
        String value;
        String parameters = "";
        for (String key : sortedParams.keySet()) {
            if (EXCLUDE_PARAM_SIGN.equals(key)) {
                continue;
            }
            value = sortedParams.get(key);
            if (excludeEmpty && TextUtils.isEmpty(value)) {
                continue;
            }
            Log.d(PARA_TAG, String.format ("signing parameters， key = %s, value = %s", key, value));
            sb.append(key).append(KEY_VAL_LINK).append(TextUtils.isEmpty(value) ? VAL_EMPTY_DEFAULT : value);
        }

        //Add app secret at the tail
        Log.d(PARA_TAG, String.format ("signing parameters， app secret = %s", appSecret));
        sb.append(appSecret);
        parameters = sb.toString();
        Log.d(PARA_TAG, String.format ("signing parameters， all parameters = %s", parameters));
        return stringToSHA1(parameters);
    }

    private static String stringToSHA1(String string) {
        String sha1 = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(string.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < messageDigest.length; i++) {
                int temp = 0xFF & messageDigest[i];
                String s = Integer.toHexString(temp);
                if (temp <= 0x0F) {
                    s = "0" + s;
                }
                sb.append(s);
            }
            sha1 = sb.toString();
            Log.d(PARA_TAG, String.format ("signing parameters， parameters sha-1 = %s", sha1));
            return sha1;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.d(PARA_TAG, String.format ("signing parameters failed， cause = %s", e.toString()));
        }

        return sha1;
    }
}