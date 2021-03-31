package com.ilife.common.audioRecord;

import android.media.MediaRecorder;
import android.os.Build;
import android.util.Log;

import com.ilife.common.audioRecord.exceptions.InvalidOutputFileException;
import com.ilife.common.audioRecord.exceptions.RecorderInitException;
import com.ilife.common.audioRecord.interfaces.RecorderContract;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static com.ilife.common.audioRecord.AudioConstants.VISUALIZATION_INTERVAL;

public class AudioRecorder implements RecorderContract.Recorder {

    private final static String ErrorTag = "Recorder_Error";

    private MediaRecorder recorder = null;
    private File recordFile = null;

    private boolean deleteRecordFile = false;
    private boolean isPrepared = false;
    private boolean isRecording = false;
    private boolean isPaused = false;
    private Timer timerProgress;
    private long progress = 0;

    private RecorderContract.RecorderCallback recorderCallback;

    private static class RecorderSingletonHolder {
        private static AudioRecorder singleton = new AudioRecorder();

        public static AudioRecorder getSingleton() {
            return RecorderSingletonHolder.singleton;
        }
    }

    public static AudioRecorder getInstance() {
        return RecorderSingletonHolder.getSingleton();
    }

    private AudioRecorder() { }

    @Override
    public void setRecorderCallback(RecorderContract.RecorderCallback callback) {
        this.recorderCallback = callback;
    }

    @Override
    public void prepare(String outputFile, int channelCount, int sampleRate, int bitrate) {
        recordFile = new File(outputFile);
        if (recordFile.exists() && recordFile.isFile()) {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            recorder.setAudioChannels(channelCount);
            recorder.setAudioSamplingRate(sampleRate);
            recorder.setAudioEncodingBitRate(bitrate);
            recorder.setMaxDuration(-1); //Duration unlimited or use RECORD_MAX_DURATION
            recorder.setOutputFile(recordFile.getAbsolutePath());
            try {
                recorder.prepare();
                isPrepared = true;
                if (recorderCallback != null) {
                    recorderCallback.onPrepareRecord();
                }
            } catch (IOException | IllegalStateException e) {
                Log.e(ErrorTag, e +  "prepare() failed");
                if (recorderCallback != null) {
                    recorderCallback.onError(new RecorderInitException());
                }
            }
        } else {
            if (recorderCallback != null) {
                recorderCallback.onError(new InvalidOutputFileException());
            }
        }
    }

    @Override
    public void startRecording() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && isPaused) {
            try {
                recorder.resume();
                startRecordingTimer();
                if (recorderCallback != null) {
                    recorderCallback.onStartRecord(recordFile);
                }
                isPaused = false;
            } catch (IllegalStateException e) {
                Log.e(ErrorTag, e +  "unpauseRecording() failed");
                if (recorderCallback != null) {
                    recorderCallback.onError(new RecorderInitException());
                }
            }
        } else {
            if (isPrepared) {
                try {
                    recorder.start();
                    isRecording = true;
                    startRecordingTimer();
                    if (recorderCallback != null) {
                        recorderCallback.onStartRecord(recordFile);
                    }
                } catch (RuntimeException e) {
                    Log.e(ErrorTag, e +  "startRecording() failed");
                    if (recorderCallback != null) {
                        recorderCallback.onError(new RecorderInitException());
                    }
                }
            } else {
                Log.e(ErrorTag, "Recorder is not prepared!!!");
            }
            isPaused = false;
        }
    }

    @Override
    public void pauseRecording() {
        if (isRecording) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    recorder.pause();
                    pauseRecordingTimer();
                    if (recorderCallback != null) {
                        recorderCallback.onPauseRecord();
                    }
                    isPaused = true;
                } catch (IllegalStateException e) {
                    Log.e(ErrorTag, e +  "pauseRecording() failed");
                    if (recorderCallback != null) {
                        //TODO: Fix exception
                        recorderCallback.onError(new RecorderInitException());
                    }
                }
            } else {
                stopRecording();
            }
        }
    }

    @Override
    public void stopRecording() {
        if (isRecording) {
            stopRecordingTimer();
            try {
                recorder.stop();
            } catch (RuntimeException e) {
                Log.e(ErrorTag, e +  "stopRecording() problems");
            }
            recorder.release();

            if (recorderCallback != null) {
                recorderCallback.onStopRecord(recordFile);
            }
            recordFile = null;
            isPrepared = false;
            isRecording = false;
            isPaused = false;
            recorder = null;
        } else {
            Log.e(ErrorTag, "Recording has already stopped or hasn't started");
        }
    }

    private void startRecordingTimer() {
        timerProgress = new Timer();
        timerProgress.schedule(new TimerTask() {
            @Override
            public void run() {
                if (recorderCallback != null && recorder != null) {
                    progress += VISUALIZATION_INTERVAL;
                }
            }
        }, 0, VISUALIZATION_INTERVAL);
    }

    private void stopRecordingTimer() {
        timerProgress.cancel();
        timerProgress.purge();
        progress = 0;
    }

    private void pauseRecordingTimer() {
        timerProgress.cancel();
        timerProgress.purge();
    }

    @Override
    public boolean isRecording() {
        return isRecording;
    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }
}
