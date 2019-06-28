package com.example.demoslideimage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.example.demoslideimage.R;
import com.example.demoslideimage.databinding.ActivityCreateVideoBinding;
import com.example.demoslideimage.extensions.ShowLog;
import com.example.demoslideimage.handler.MyClickHandler;

import java.util.ArrayList;

public class CreateVideoActivity extends AppCompatActivity implements MyClickHandler {
    private ActivityCreateVideoBinding binding;
    private static final String LIST_IMAGE = "LIST_IMAGE";
    private static final String TAG = CreateVideoHomeActivity.class.getName();
    private ArrayList<String> listUriImage;

    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/videooo.mp4";

    public static void startInternt(Context context, ArrayList<String> listImage) {
        Intent intent = new Intent(context, CreateVideoActivity.class);
        intent.putStringArrayListExtra(LIST_IMAGE, listImage);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_video);

        Intent intent = getIntent();
        listUriImage = intent.getStringArrayListExtra(LIST_IMAGE);
        if (listUriImage == null || listUriImage.size() == 0) {
            ShowLog.ShowLog(this, binding.getRoot(), getString(R.string.error_intent), false);
            finish();
        }

        binding.setHandler(this);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onClickWithData(View view, Object value) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
