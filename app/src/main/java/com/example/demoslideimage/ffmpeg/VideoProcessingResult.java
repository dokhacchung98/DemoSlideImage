package com.example.demoslideimage.ffmpeg;

public class VideoProcessingResult {
    static final int SUCCESSFUL_RESULT = 0;

    private final int returnCode;
    private final String pathToFile;

    VideoProcessingResult(int code, String path) {
        returnCode = code;
        pathToFile = path;
    }

    public boolean isSuccessful() {
        return returnCode == SUCCESSFUL_RESULT;
    }

    public String getPath() {
        return pathToFile;
    }

    public int getCode() {
        return returnCode;
    }
}
