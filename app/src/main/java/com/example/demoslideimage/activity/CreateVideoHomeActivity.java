package com.example.demoslideimage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.arthenica.mobileffmpeg.FFmpeg;
import com.example.demoslideimage.R;
import com.example.demoslideimage.adapter.MyAdapterRecyclerViewImageList;
import com.example.demoslideimage.custom.GetListImageFromStorage;
import com.example.demoslideimage.databinding.ActivityCreateVideoHomeBinding;
import com.example.demoslideimage.handler.MyClickHandler;
import com.example.demoslideimage.handler.MySelectedItem;
import com.example.demoslideimage.model.ItemImage;

import java.util.ArrayList;

public class CreateVideoHomeActivity extends AppCompatActivity implements MyClickHandler, MySelectedItem {
    private ActivityCreateVideoHomeBinding binding;
    private static String TAG = EditImageHomeActivity.class.getName();
    private ArrayList<ItemImage> listItemImage;
    private ArrayList<String> listUriImage;
    private MyAdapterRecyclerViewImageList adapterRecyclerView;
    private Animation animZoomIn;
    private Animation animZoomOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_video_home);
        binding.setHandler(this);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        animZoomIn = AnimationUtils.loadAnimation(this, R.anim.anim_rotation_zoom_in);
        animZoomOut = AnimationUtils.loadAnimation(this, R.anim.anim_rotation_zoom_out);

        animZoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onAnimationEnd(Animation animation) {
                binding.btnCreate.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        initData();
    }

    public static void startInternt(Context context) {
        Intent intent = new Intent(context, CreateVideoHomeActivity.class);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(context, R.anim.fade_right_to_left_enter, R.anim.fade_right_to_left_exit);
        context.startActivity(intent, options.toBundle());
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

    private void initData() {
        listItemImage = new ArrayList<>();
        listUriImage = new ArrayList<>();
        getAllImageInStorage();
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/videooo.mp4";
        Log.e(this.getClass().getName(), "PATH: " + path);
        String cmd = "-loop 1 -t 5 -i "
                + listItemImage.get(0).getResourceImage()
                + " -loop 1 -t 3 -i "
                + listItemImage.get(1).getResourceImage()
                + " -loop 1 -t 3 -i "
                + listItemImage.get(4).getResourceImage()
                + " -loop 1 -t 3 -i "
                + listItemImage.get(2).getResourceImage()
                + " -loop 1 -t 3 -i "
                + listItemImage.get(3).getResourceImage()
                + " -filter_complex [0:v]trim=duration=3,fade=t=out:st=2.5:d=0.5[v0];[1:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v1];[2:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v2];[3:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v3];[v0][v1][v2][v3]concat=n=4:v=1:a=0,format=yuv420p[v] -map [v] -preset ultrafast "
                + path;
        EditImageHomeActivity.executeAsync(result -> {
            Log.e(TAG, "result: " + result + ", success: " + FFmpeg.RETURN_CODE_SUCCESS + ", cancel: " + FFmpeg.RETURN_CODE_CANCEL);
        }, cmd);


        adapterRecyclerView = new MyAdapterRecyclerViewImageList(listItemImage, this, true);
        adapterRecyclerView.setMySelectedItem(this);
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
            case R.id.btn_create:
                CreateVideoActivity.startInternt(this, listUriImage);
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

    @Override
    public void selectedItem(Object obj) {
        ItemImage imageView = (ItemImage) obj;
        int index = listItemImage.indexOf(imageView);
        if (index != -1) {
            imageView.setSelected(!imageView.isSelected());
            if (imageView.isSelected()) {
                listUriImage.add(imageView.getResourceImage());
            } else {
                listUriImage.remove(imageView.getResourceImage());
            }
            listItemImage.set(index, imageView);
            adapterRecyclerView.notifyItemChanged(index);
        }
        visibleButton();
    }

    @SuppressLint("RestrictedApi")
    private void visibleButton() {
        if (listUriImage.size() > 0) {
            if (binding.btnCreate.getVisibility() == View.GONE) {
                binding.btnCreate.setVisibility(View.VISIBLE);
                binding.btnCreate.startAnimation(animZoomIn);
            }
        } else {
            if (binding.btnCreate.getVisibility() == View.VISIBLE) {
                binding.btnCreate.startAnimation(animZoomOut);
            }
        }
    }
}
