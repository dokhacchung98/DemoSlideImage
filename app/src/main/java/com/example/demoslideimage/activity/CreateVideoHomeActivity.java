package com.example.demoslideimage.activity;

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
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.demoslideimage.R;
import com.example.demoslideimage.adapter.MyAdapterRecyclerViewImageList;
import com.example.demoslideimage.base.BaseActivity;
import com.example.demoslideimage.custom.GetListImageFromStorage;
import com.example.demoslideimage.databinding.ActivityCreateVideoHomeBinding;
import com.example.demoslideimage.extensions.PathVideo;
import com.example.demoslideimage.extensions.StringComand;
import com.example.demoslideimage.handler.MyClickHandler;
import com.example.demoslideimage.handler.MySelectedItem;
import com.example.demoslideimage.model.ItemImage;

import java.io.File;
import java.util.ArrayList;

public class CreateVideoHomeActivity extends BaseActivity implements MyClickHandler, MySelectedItem {
    private ActivityCreateVideoHomeBinding binding;
    private static String TAG = EditImageHomeActivity.class.getName();
    private ArrayList<ItemImage> listItemImage;
    private ArrayList<String> listUriImage;
    private MyAdapterRecyclerViewImageList adapterRecyclerView;
    private Animation animZoomIn;
    private Animation animZoomOut;
    private int indexOfListUri = 1;

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
        File file = new File(PathVideo.getPathTempImg(this));
        if (!file.exists())
            file.mkdirs();

        File fileVD = new File(PathVideo.getPathTempVideo(this));
        if (!fileVD.exists())
            fileVD.mkdirs();

        listItemImage = new ArrayList<>();
        listUriImage = new ArrayList<>();
        getAllImageInStorage();

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
                resizeImageBeforeHanlder();
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
        if (listUriImage.size() > 1) {
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

    private void resizeImageBeforeHanlder() {
        String path = PathVideo.getPathTempImg(this);

        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File tmp : files) {
                tmp.delete();
            }
        }

        if (listUriImage.size() > 0) {
            resizeImage(listUriImage.get(0));
        }
    }

    private void resizeImage(String uri) {
        String cmd = StringComand.resizeImage(uri, this);
        EditImageHomeActivity.executeAsync(returnCode -> {
            if (indexOfListUri < listUriImage.size()) {
                resizeImage(listUriImage.get(indexOfListUri));
                indexOfListUri++;
            } else {
                CreateVideoActivity.startInternt(this);
            }
        }, cmd);
    }

    @Override
    protected void onResume() {
        super.onResume();
        indexOfListUri = 1;
        ArrayList<ItemImage> list = GetListImageFromStorage.getListImageFromStorage(this);
        for (ItemImage x : list) {
            if (!listItemImage.contains(x))
                listItemImage.add(x);
        }
        adapterRecyclerView.notifyDataSetChanged();
        Log.e(TAG, "Number of size: " + listItemImage.size());
    }
}
