package com.ilife.common.events;

public class ForceReLoginEvent {
    long timeStampMillis;

    public ForceReLoginEvent() {
        this.timeStampMillis = System.currentTimeMillis();
    }
}
