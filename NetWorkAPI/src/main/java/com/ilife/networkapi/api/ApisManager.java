package com.ilife.networkapi.api;

import android.util.LruCache;

public class ApisManager {

    private static LruCache<Class, Object> apiCache = new LruCache<>(10);
    private IApiFactory apiFactory;
    private static ApisManager INSTANCE;


    private ApisManager() {
        this.apiFactory = new ApiFactoryImpl();
    }

    public static void init() {
        ApisManager localManager = INSTANCE;
        if (null == localManager) {
            synchronized (ApisManager.class) {
                if (null == localManager) {
                    INSTANCE = new ApisManager();
                }
            }
        }
    }


    public static ApisManager getInstance() {
        return INSTANCE;
    }

    public <T> T getApi(Class<T> cls) {
        Object apiObject = apiCache.get(cls);
        if (null == apiObject) {
            apiObject = apiFactory.makeApiClient(cls);
            apiCache.put(cls, apiObject);
        }
        return (T) apiObject;
    }

}
