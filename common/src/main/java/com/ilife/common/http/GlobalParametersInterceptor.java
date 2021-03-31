package com.ilife.common.http;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import com.ilife.common.BuildConfig;
import com.ilife.common.utils.DeviceUtils;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class GlobalParametersInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    public static final String PLATFORM_ID = "2_30_300";
    public static final String APP_SECRET_KEY = "h_android";
    private Context context;
    public static String PARA_TAG = "PARA_TAG";

    public GlobalParametersInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        HttpUrl url = request.url();
        if (request.body() != null) {
            FormBody.Builder formBodyBuilder = newFormBuilder(retrieveFormBodyParametersMap(request))
                    .add("ak", APP_SECRET_KEY)
                    .add("did", DeviceUtils.getDeviceId(context))
                    .add("p", PLATFORM_ID)
                    .add("ts", String.valueOf(System.currentTimeMillis()))
                    .add("ua", getUserAgent())
                    .add("v", BuildConfig.VERSION_NAME);

            //Get the temp request instance to calculate the sign value
            request = requestBuilder.post(formBodyBuilder.build()).build();

            // Add sign value in form builder
            formBodyBuilder.add("sn", ParameterSigner.signParams(request));

            // Get the final request
            request = requestBuilder
                    .url(url)
                    .post(formBodyBuilder.build())
                    .build();
        }
        return chain.proceed(request);
    }

    private static Map<String, String> retrieveFormBodyParametersMap(Request request) {
        Map<String, String> formBodyParameters = new ArrayMap<>();

        RequestBody requestBody = request.body();
        Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Charset charset = UTF8;
        MediaType contentType = requestBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        if (isPlaintext(buffer)) {
            String s = buffer.readString(charset);
            if (!TextUtils.isEmpty(s)) {
                Uri parse = Uri.parse("?" + s);
                if (null != parse) {
                    try {
                        Log.d(PARA_TAG, "current name = " + parse.toString());
                        Set<String> set = parse.getQueryParameterNames();
                        for (String name : set) {
                            formBodyParameters.put(name, parse.getQueryParameter(name));
                        }
                    } catch (Exception e) {
                        Log.e(PARA_TAG, "get you");
                    }
                }
            }
        }

        return formBodyParameters;
    }


    private static FormBody.Builder newFormBuilder(@NonNull Map<String, String> formBodyParameters) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : formBodyParameters.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder;
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    public static String getUserAgent() {
        String userAgent = System.getProperty("http.agent");
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return Uri.encode(sb.toString());
    }
}


