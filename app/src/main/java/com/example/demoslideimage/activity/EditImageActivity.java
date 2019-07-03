package com.example.demoslideimage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.demoslideimage.R;
import com.example.demoslideimage.databinding.ActivityEditImageBinding;
import com.example.demoslideimage.extensions.ShowLog;

import java.io.IOException;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoFilter;

public class EditImageActivity extends AppCompatActivity {
    private static final String TAG = EditImageActivity.class.getName();
    private ActivityEditImageBinding binding;
    private static final String URI_IMAGE = "uri_image";
    private String uri;
    private PhotoEditor editor;

    public static void startInternt(Context context, String uri) {
        Intent intent = new Intent(context, EditImageActivity.class);
        intent.putExtra(URI_IMAGE, uri);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_image);

        Intent intent = getIntent();
        if (intent != null) {
            uri = intent.getStringExtra(URI_IMAGE);
        }

        if (uri != null) {
            initView();
            Bitmap bitmapImg = null;
            try {
                bitmapImg = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.photoEditorView.getSource().setImageBitmap(bitmapImg);
        } else {
            ShowLog.ShowLog(this, binding.getRoot(), getResources().getString(R.string.error_intent), false);
            this.finish();
        }
    }

    private void initView() {
        Typeface mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");
        editor = new PhotoEditor.Builder(EditImageActivity.this, binding.photoEditorView)
                .setPinchTextScalable(true)
                .setDefaultEmojiTypeface(mEmojiTypeFace)
                .build();

        editor.setFilterEffect(PhotoFilter.BRIGHTNESS);


    }
}
