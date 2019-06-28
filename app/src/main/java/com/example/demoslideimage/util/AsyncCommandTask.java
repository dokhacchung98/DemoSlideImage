package com.example.demoslideimage.util;

import android.os.AsyncTask;

import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.util.RunCallback;

public class AsyncCommandTask extends AsyncTask<String, Integer, Integer> {

    private final RunCallback runCallback;

    public AsyncCommandTask(final RunCallback runCallback) {
        this.runCallback = runCallback;
    }

    @Override
    protected Integer doInBackground(final String... arguments) {
        return FFmpeg.execute(arguments[0], " ");
    }

    @Override
    protected void onPostExecute(final Integer rc) {
        if (runCallback != null) {
            runCallback.apply(rc);
        }
    }

}
