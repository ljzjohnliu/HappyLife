package com.ilife.common.audioRecord.exceptions;

public class PermissionDeniedException extends AppException {
    @Override
    public int getType() {
        return AppException.READ_PERMISSION_DENIED;
    }
}
