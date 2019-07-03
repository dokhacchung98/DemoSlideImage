package com.example.demoslideimage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.util.RunCallback;
import com.example.demoslideimage.R;
import com.example.demoslideimage.adapter.MyAdapterRecyclerViewImageList;
import com.example.demoslideimage.custom.GetListImageFromStorage;
import com.example.demoslideimage.databinding.ActivityEditImageHomeBinding;
import com.example.demoslideimage.extensions.PathVideo;
import com.example.demoslideimage.handler.MyClickHandler;
import com.example.demoslideimage.model.ItemImage;
import com.example.demoslideimage.util.AsyncCommandTask;

import java.io.File;
import java.util.ArrayList;

public class EditImageHomeActivity extends AppCompatActivity implements MyClickHandler {

    private static String TAG = EditImageHomeActivity.class.getName();
    private ActivityEditImageHomeBinding binding;
    private ArrayList<ItemImage> listItemImage;
    private MyAdapterRecyclerViewImageList adapterRecyclerView;

    public static final void startInternt(Context context) {
        Intent intent = new Intent(context, EditImageHomeActivity.class);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(context, R.anim.fade_right_to_left_enter, R.anim.fade_right_to_left_exit);
        context.startActivity(intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_image_home);
        binding.setHandler(this);
        ActivityCompat.requestPermissions(EditImageHomeActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        initData();
    }

    public static void executeAsync(final RunCallback runCallback, final String arguments) {
        final AsyncCommandTask asyncCommandTask = new AsyncCommandTask(runCallback);
        asyncCommandTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, arguments);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(EditImageHomeActivity.this, getResources().getText(R.string.denied_permision), Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void initData() {
        File file = new File(PathVideo.getPathTempImg(this));
        if (!file.exists())
            file.mkdirs();

        File fileVD = new File(PathVideo.getPathTempVideo(this));
        if (!fileVD.exists())
            fileVD.mkdirs();

        listItemImage = new ArrayList<>();

        getAllImageInStorage();

        adapterRecyclerView = new MyAdapterRecyclerViewImageList(listItemImage, this, false);
        binding.setMyAdapter(adapterRecyclerView);
        binding.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void getAllImageInStorage() {
        listItemImage = GetListImageFromStorage.getListImageFromStorage(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBackPress:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onClickWithData(View view, Object value) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_left_to_right_enter, R.anim.fade_left_to_right_exit);
    }
}
