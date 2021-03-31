package com.ilife.happy;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.getkeepsafe.relinker.ReLinker;
import com.google.common.base.Stopwatch;
import com.ilife.common.GlobalActivityMgr;
import com.ilife.common.connection.NetworkInfoProvider;
import com.ilife.common.http.HttpClient;
import com.ilife.happy.crash.ILifeUncaughtException;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.onAdaptListener;
import me.jessyan.autosize.utils.AutoSizeLog;

public class ILifeApplication extends Application implements GlobalActivityMgr.OnApplicationExitListener {
    private final static String TAG = "ILifeApplication";
    private final static String TIME_TRACE = "initTimeLapse";

    public static Context applicationContext;
    private ExecutorService executor;
    private boolean initialized = false;
    private ILifeUncaughtException exceptionHandler = null;

    public static final int MSG_CHECK_AUTH = 0;

    public static NetworkInfoProvider networkInfoProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("ILifeApplication", "ILifeApplication onCreate invoked");
        applicationContext = getApplicationContext();
        GlobalActivityMgr.getInstance().registerExitListener(this);

        networkInfoProvider = new NetworkInfoProvider(applicationContext, null);
        init();

        //Async initialization
        executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> initAsync());
    }

    private void init() {
        Log.d(TIME_TRACE, "================================================");
        Log.d(TIME_TRACE, "ILife application init start to run");
        Stopwatch overall = Stopwatch.createStarted();

        Stopwatch subRouteWatch = Stopwatch.createStarted();
        HttpClient.init(applicationContext, applicationContext.getCacheDir());
        subRouteWatch.stop();
        Log.d(TIME_TRACE, "http init time lapse = " + subRouteWatch.elapsed(TimeUnit.MILLISECONDS));

        subRouteWatch.reset();
        subRouteWatch.start();
        initMMKV();
        subRouteWatch.stop();
        Log.d(TIME_TRACE, "mmkv init time lapse = " + subRouteWatch.elapsed(TimeUnit.MILLISECONDS));

        /**
         * init Fresco
         */
        subRouteWatch.reset();
        subRouteWatch.start();
        initFresco();
        subRouteWatch.stop();
        Log.d(TIME_TRACE, "fresco init time lapse = " + subRouteWatch.elapsed(TimeUnit.MILLISECONDS));

        initialized = true;

        initAutoSize();

        overall.stop();
        Log.d(TIME_TRACE, "ILife application init finished, time lapse = " + overall.elapsed(TimeUnit.MILLISECONDS));
        Log.d(TIME_TRACE, "================================================");
    }

    private void initAsync() {
        //todo 为加速启动把一些初始化放到异步执行
    }

    private void initFresco() {
        DiskCacheConfig mainConfig = DiskCacheConfig.newBuilder(applicationContext).build();
        DiskCacheConfig smallConfig = DiskCacheConfig.newBuilder(applicationContext).build();

        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(applicationContext)
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(mainConfig)
                .setSmallImageDiskCacheConfig(smallConfig);

        ImagePipelineConfig config = configBuilder.build();
        Fresco.initialize(this, config);
    }

    private void initAutoSize() {
//        AutoSize.checkAndInit(this);
        AutoSizeConfig.getInstance()
                .setCustomFragment(true)
                .setOnAdaptListener(new onAdaptListener() {
                    @Override
                    public void onAdaptBefore(Object target, Activity activity) {
                        //使用以下代码, 可以解决横竖屏切换时的屏幕适配问题
                        //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
                        //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
//                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
//                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
                        AutoSizeLog.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
                    }

                    @Override
                    public void onAdaptAfter(Object target, Activity activity) {
                        AutoSizeLog.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
                    }
                });
    }

    private void initCrashReport() {
        Thread.setDefaultUncaughtExceptionHandler(new ILifeUncaughtException());
    }

    private void initMMKV() {
        String dir = getFilesDir().getAbsolutePath() + "/mmkv";
        //Use relinker to fix MMKV crash on Google Pixel
        MMKV.initialize(dir, libName -> ReLinker.loadLibrary(ILifeApplication.this, libName));
        Log.d(TAG, "MMKV root: " + dir);
    }

    @Override
    public void onExit() {
        EventBus.getDefault().unregister(this);
    }

    public static boolean isMainProcess(Context context) {
        try {
            int pid = android.os.Process.myPid();
            String process = getAppNameByPID(context, pid);
            Log.d("ILifeApplication", "process name = " + process);
            if (TextUtils.isEmpty(process)) {
                return true;
            } else if (context.getPackageName().equalsIgnoreCase(process)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static String getAppNameByPID(Context context, int pid) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return null;
    }

}
