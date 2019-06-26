package com.example.demoslideimage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;

import com.example.demoslideimage.R;
import com.example.demoslideimage.databinding.ActivityCreateVideoBinding;
import com.example.demoslideimage.handler.MyClickHandler;

public class CreateVideoActivity extends AppCompatActivity implements MyClickHandler {
    private ActivityCreateVideoBinding binding;
    private MediaController controller;
    String path = getFilesDir().getAbsolutePath() + "/videoout.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_video);
        binding.setHandle(this);
    }

    @Override
    public void onClick(View view) {
        if (controller == null) {
            controller = new MediaController(this);
            binding.vdvVideo.setMediaController(controller);
            binding.vdvVideo.setVideoURI(Uri.parse(path));
            binding.vdvVideo.start();
        }
    }

    @Override
    public void onClickWithData(View view, Object value) {

    }

    @Override
    protected void onDestroy() {
        if (binding.vdvVideo.isPlaying()) {
            binding.vdvVideo.stopPlayback();
        }
        super.onDestroy();
    }
}
