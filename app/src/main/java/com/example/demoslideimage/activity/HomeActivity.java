package com.example.demoslideimage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.demoslideimage.R;
import com.example.demoslideimage.databinding.ActivityHomeBinding;
import com.example.demoslideimage.handler.MyClickHandler;

import java.io.File;

public class HomeActivity extends AppCompatActivity implements MyClickHandler {
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        binding.setHandler(this);
    }

    public static final void startInternt(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, getResources().getText(R.string.denied_permision), Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ln_edit_image:
                EditImageHomeActivity.startInternt(this);
                break;
            case R.id.ln_create_video:
                CreateVideoHomeActivity.startInternt(this);
                break;
            case R.id.ln_edit_video:

                break;
        }
    }

    @Override
    public void onClickWithData(View view, Object value) {

    }
}
