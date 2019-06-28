package com.example.demoslideimage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.demoslideimage.R;
import com.example.demoslideimage.databinding.ActivityHomeBinding;
import com.example.demoslideimage.handler.MyClickHandler;

public class HomeActivity extends AppCompatActivity implements MyClickHandler {
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        binding.setHandler(this);
    }

    public static final void startInternt(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
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
