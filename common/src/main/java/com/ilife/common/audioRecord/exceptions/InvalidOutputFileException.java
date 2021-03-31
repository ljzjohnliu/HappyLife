package com.ilife.common.audioRecord.exceptions;

public class InvalidOutputFileException extends AppException {
    @Override
    public int getType() {
        return AppException.INVALID_OUTPUT_FILE;
    }
}
