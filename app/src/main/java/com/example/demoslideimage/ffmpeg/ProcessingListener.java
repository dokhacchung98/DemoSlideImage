package com.example.demoslideimage.ffmpeg;

public interface ProcessingListener {
    void onSuccess(String path);
    void onFailure(int returnCode);
}
