package com.ilife.common.utils;

public class MediaInfo {
    String name;
    String path;
    String type;
    int width;
    int height;
    int size;
    Integer duration;

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    String thumbnailPath;

    public MediaInfo(String name, String path, String type, int width, int height, int size, Integer duration) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.width = width;
        this.height = height;
        this.size = size;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public boolean isVideo() {
        return !type.isEmpty() && type.contains("video");
    }
}
