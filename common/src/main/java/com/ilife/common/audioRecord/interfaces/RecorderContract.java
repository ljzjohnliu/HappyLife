package com.ilife.common.audioRecord.interfaces;

import com.ilife.common.audioRecord.exceptions.AppException;

import java.io.File;

public interface RecorderContract {

    interface RecorderCallback {
        void onPrepareRecord();
        void onStartRecord(File output);
        void onPauseRecord();
        void onStopRecord(File output);
        void onError(AppException throwable);
    }

    interface Recorder {
        void setRecorderCallback(RecorderCallback callback);
        void prepare(String outputFile, int channelCount, int sampleRate, int bitrate);
        void startRecording();
        void pauseRecording();
        void stopRecording();
        boolean isRecording();
        boolean isPaused();
    }
}
