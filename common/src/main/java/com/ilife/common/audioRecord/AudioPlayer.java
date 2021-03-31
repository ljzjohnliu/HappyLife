package com.ilife.common.audioRecord;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.ilife.common.audioRecord.exceptions.AppException;
import com.ilife.common.audioRecord.exceptions.PermissionDeniedException;
import com.ilife.common.audioRecord.exceptions.PlayerDataSourceException;
import com.ilife.common.audioRecord.interfaces.PlayerContract;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class AudioPlayer implements PlayerContract.Player, MediaPlayer.OnPreparedListener {

    private List<PlayerContract.PlayerCallback> actionsListeners = new CopyOnWriteArrayList<>();

    private final static String ErrorTag = "AudioPlayerError";

    private MediaPlayer mediaPlayer;
    private Timer timerProgress;
    private boolean isPrepared = false;
    private boolean isPause = false;
    private long seekPos = 0;
    private long pausePos = 0;
    private String dataSource = null;


    private static class SingletonHolder {
        private static AudioPlayer singleton = new AudioPlayer();

        public static AudioPlayer getSingleton() {
            return SingletonHolder.singleton;
        }
    }

    public static AudioPlayer getInstance() {
        return SingletonHolder.getSingleton();
    }

    private AudioPlayer() {}

    @Override
    public void addPlayerCallback(PlayerContract.PlayerCallback callback) {
        if (callback != null) {
            actionsListeners.add(callback);
        }
    }

    @Override
    public boolean removePlayerCallback(PlayerContract.PlayerCallback callback) {
        if (callback != null) {
            return actionsListeners.remove(callback);
        }
        return false;
    }

    @Override
    public void clearCallbacks() {
        actionsListeners.clear();
    }

    @Override
    public void setData(String data) {
        if (mediaPlayer != null && dataSource != null && dataSource.equals(data)) {
            //Do nothing
        } else {
            dataSource = data;
            restartPlayer();
        }
    }

    private void restartPlayer() {
        if (dataSource != null) {
            try {
                isPrepared = false;
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(dataSource);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            } catch (IOException | IllegalArgumentException | IllegalStateException | SecurityException e) {
                Log.e(ErrorTag, e.toString());
                if (e.getMessage().contains("Permission denied")) {
                    onError(new PermissionDeniedException());
                } else {
                    onError(new PlayerDataSourceException());
                }
            }
        }
    }

    @Override
    public void playOrPause() {
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    pause();
                } else {
                    isPause = false;
                    if (!isPrepared) {
                        try {
                            mediaPlayer.setOnPreparedListener(this);
                            mediaPlayer.prepareAsync();
                        } catch (IllegalStateException ex) {
                            Log.e(ErrorTag, ex.toString());
                            restartPlayer();
                            mediaPlayer.setOnPreparedListener(this);
                            try {
                                mediaPlayer.prepareAsync();
                            } catch (IllegalStateException e) {
                                Log.e(ErrorTag, e.toString());
                                restartPlayer();
                            }
                        }
                    } else {
                        mediaPlayer.start();
                        mediaPlayer.seekTo((int) pausePos);
                        onStartPlay();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                stop();
                                onStopPlay();
                            }
                        });

                        timerProgress = new Timer();
                        timerProgress.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                        int curPos = mediaPlayer.getCurrentPosition();
                                        Log.v("playingProgress", "curPos = " + curPos);

                                        onPlayProgress(curPos);
                                    }
                                } catch(IllegalStateException e){
                                    Log.e(ErrorTag, e + "Player is not initialized!");
                                }
                            }
                        }, 0, AudioConstants.VISUALIZATION_INTERVAL);
                    }
                    pausePos = 0;
                }
            }
        } catch(IllegalStateException e){
            Log.e(ErrorTag, e + "Player is not initialized!");
        }
    }

    @Override
    public void onPrepared(final MediaPlayer mp) {
        if (mediaPlayer != mp) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = mp;
        }
        onPreparePlay();
        isPrepared = true;
        mediaPlayer.start();
        mediaPlayer.seekTo((int) seekPos);
        onStartPlay();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop();
                onStopPlay();
            }
        });

        timerProgress = new Timer();
        timerProgress.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        int curPos = mediaPlayer.getCurrentPosition();
                        Log.v("playingProgress", "curPos = " + curPos);
                        onPlayProgress(curPos);
                    }
                } catch(IllegalStateException e){
                    Log.e(ErrorTag, e + "Player is not initialized!");
                }
            }
        }, 0, AudioConstants.VISUALIZATION_INTERVAL);
    }

    @Override
    public void seek(long mills) {
        seekPos = mills;
        if (isPause) {
            pausePos = mills;
        }
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo((int) seekPos);
                onSeek((int) seekPos);
            }
        } catch(IllegalStateException e){
            Log.e(ErrorTag, e + "Player is not initialized!");
        }
    }

    @Override
    public void pause() {
        if (timerProgress != null) {
            timerProgress.cancel();
            timerProgress.purge();
        }
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                onPausePlay();
                seekPos = mediaPlayer.getCurrentPosition();
                isPause = true;
                pausePos = seekPos;
            }
        }
    }

    @Override
    public void stop() {
        if (timerProgress != null) {
            timerProgress.cancel();
            timerProgress.purge();
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.setOnCompletionListener(null);
            isPrepared = false;
            onStopPlay();
            mediaPlayer.getCurrentPosition();
            seekPos = 0;
        }
        isPause = false;
        pausePos = 0;
    }

    @Override
    public boolean isPlaying() {
        try {
            return mediaPlayer != null && mediaPlayer.isPlaying();
        } catch(IllegalStateException e){
            Log.e(ErrorTag, e + "Player is not initialized!");
        }
        return false;
    }

    @Override
    public boolean isPause() {
        return isPause;
    }

    @Override
    public long getPauseTime() {
        return seekPos;
    }

    @Override
    public void release() {
        stop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        isPrepared = false;
        isPause = false;
        dataSource = null;
        actionsListeners.clear();
    }

    private void onPreparePlay() {
        if (!actionsListeners.isEmpty()) {
            for (int i = 0; i < actionsListeners.size(); i++) {
                actionsListeners.get(i).onPreparePlay();
            }
        }
    }

    private  void onStartPlay() {
        if (!actionsListeners.isEmpty()) {
            for (int i = 0; i < actionsListeners.size(); i++) {
                actionsListeners.get(i).onStartPlay();
            }
        }
    }

    private void onPlayProgress(long mills) {
        if (!actionsListeners.isEmpty()) {
            for (int i = 0; i < actionsListeners.size(); i++) {
                actionsListeners.get(i).onPlayProgress(mills);
            }
        }
    }

    private void onStopPlay() {
        if (!actionsListeners.isEmpty()) {
            for (int i = actionsListeners.size()-1; i >= 0; i--) {
                actionsListeners.get(i).onStopPlay();
            }
        }
    }

    private void onPausePlay() {
        if (!actionsListeners.isEmpty()) {
            for (int i = 0; i < actionsListeners.size(); i++) {
                actionsListeners.get(i).onPausePlay();
            }
        }
    }

    private void onSeek(long mills) {
        if (!actionsListeners.isEmpty()) {
            for (int i = 0; i < actionsListeners.size(); i++) {
                actionsListeners.get(i).onSeek(mills);
            }
        }
    }

    private void onError(AppException throwable) {
        if (!actionsListeners.isEmpty()) {
            for (int i = 0; i < actionsListeners.size(); i++) {
                actionsListeners.get(i).onError(throwable);
            }
        }
    }
}
