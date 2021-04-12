package com.ilife.common.endpoint;

public class EndPointHost {
    public static final String HTTP_PREFIX = "http://";
    public static final String HTTPS_PREFIX = "https://";

    public static final String ILIFE_HOST_SUFFIX = "test";//正式环境
    public static final String ILIFE_HOST_SUFFIX_HOST_PRE = "";  //预发环境
    public static final String ILIFE_HOST_SUFFIX_HOST_DEBUG = "10.5.146.85:8080";//测试环境

    public final static String HOST = HTTP_PREFIX + ILIFE_HOST_SUFFIX_HOST_DEBUG + "/";
}
