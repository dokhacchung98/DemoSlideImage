package com.example.demoslideimage.ffmpeg;

import android.util.Log;

public class MyFFmpeg {
    static {
        try {
            System.loadLibrary("avutil");
            System.loadLibrary("swresample");
            System.loadLibrary("avcodec");
            System.loadLibrary("avformat");
            System.loadLibrary("swscale");
            System.loadLibrary("avfilter");
            System.loadLibrary("avdevice");
            System.loadLibrary("videokit");
            Log.e("Loadding Libs", "Success");
        } catch (UnsatisfiedLinkError e) {
            Log.e("Loadding Libs", "Error: " + e.getMessage());
        }
    }

    private LogLevel logLevel = LogLevel.NO_LOG;

    public void setLogLevel(LogLevel level) {
        logLevel = level;
    }

    int process(String[] args) {
        return run(logLevel.getValue(), args);
    }

    private native int run(int loglevel, String[] args);

    native static int nativeExecute(final String[] arguments);

    public CommandBuilder createCommand() {
        return new VideoCommandBuilder(this);
    }
}
