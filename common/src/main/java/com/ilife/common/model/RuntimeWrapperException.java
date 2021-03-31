package com.ilife.common.model;

public class RuntimeWrapperException extends RuntimeException {
    int errorCode;
    String desc;

    public RuntimeWrapperException(int errorCode, String desc) {
        this.errorCode = errorCode;
        this.desc = desc;
    }

    public RuntimeWrapperException(Throwable cause, int errorCode, String desc) {
        super(cause);
        this.errorCode = errorCode;
        this.desc = desc;
    }
}
