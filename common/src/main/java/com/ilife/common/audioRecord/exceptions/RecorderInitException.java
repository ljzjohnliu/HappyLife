package com.ilife.common.audioRecord.exceptions;

public class RecorderInitException extends AppException {
    @Override
    public int getType() {
        return AppException.RECORDER_INIT_EXCEPTION;
    }
}
