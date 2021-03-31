package com.ilife.common.log;

public class LogUtils{
    private static String LOG_TAG = "ILifeLog";

    private static Logger logger;
    static{
        logger =  new LoggerImpl.Builder()
                .seEnabled(true)
                .setTag(LOG_TAG)
                .addLogAdapter(new DiskLogAdapter())
                .addLogAdapter(new AndroidLogAdapter())
                .build();
    }


    public static void d(String message) {
        logger.d(message);
    }

    public static void i(String message) {
        logger.i(message);
    }

    public static void w(String message) {
        logger.w(message);
    }

    public static void v(String message) {
        logger.v(message);
    }

    public static void e(String message) {
        logger.e(message);
    }

    public static void e(String message, Throwable throwable) {

    }
}
