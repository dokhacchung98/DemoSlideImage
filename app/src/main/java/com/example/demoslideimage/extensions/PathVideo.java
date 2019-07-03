package com.example.demoslideimage.extensions;

import android.content.Context;

public class PathVideo {
    public static final String getPathTempImg(Context context) {
        return context.getFilesDir().getAbsolutePath() + "/temp";
    }

    public static final String getPathTempVideo(Context context) {
        return context.getFilesDir().getAbsolutePath() + "/tempVD";
    }
}
