package com.example.demoslideimage.ffmpeg;

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
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
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

    public CommandBuilder createCommand() {
        return new VideoCommandBuilder(this);
    }
}
