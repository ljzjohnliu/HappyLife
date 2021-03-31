package com.ilife.common.log;

public interface Logger {

    void setEnabled(boolean enable);
    boolean getEnabled();

    void d(String message);
    void i(String message);
    void w(String message);
    void v(String message);
    void e(String message);
    void e(String message,Throwable throwable);
}
