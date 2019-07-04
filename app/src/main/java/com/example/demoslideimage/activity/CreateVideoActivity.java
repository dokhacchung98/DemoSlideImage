package com.example.demoslideimage.activity;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.arthenica.mobileffmpeg.FFmpeg;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.example.demoslideimage.R;
import com.example.demoslideimage.adapter.MyPagerAdapter;
import com.example.demoslideimage.base.BaseActivity;
import com.example.demoslideimage.databinding.ActivityCreateVideoBinding;
import com.example.demoslideimage.databinding.FragmentListImageBinding;
import com.example.demoslideimage.extensions.PathVideo;
import com.example.demoslideimage.extensions.ShowLog;
import com.example.demoslideimage.extensions.StringComand;
import com.example.demoslideimage.extensions.TimeLoad;
import com.example.demoslideimage.fragment.EffectsFragment;
import com.example.demoslideimage.fragment.FrameFragment;
import com.example.demoslideimage.fragment.ListImageFragment;
import com.example.demoslideimage.fragment.SoundFragment;
import com.example.demoslideimage.handler.CallBackChangeList;
import com.example.demoslideimage.handler.MyClickHandler;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;

public class CreateVideoActivity extends BaseActivity implements MyClickHandler, OnPreparedListener {
    private ActivityCreateVideoBinding binding;
    private static final String TAG = CreateVideoHomeActivity.class.getName();
    private ArrayList<String> listUriImage;
    private ListImageFragment listImageFragment;
    private EffectsFragment effectsFragment;
    private FrameFragment frameFragment;
    private SoundFragment soundFragment;
    private CallBackChangeList callBackChangeList;

    private VideoView videoView;

    public static void startInternt(Context context) {
        Intent intent = new Intent(context, CreateVideoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_video);

        listUriImage = new ArrayList<>();

        binding.setHandler(this);


        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.viewPager));

        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));

        frameFragment = new FrameFragment();
        effectsFragment = new EffectsFragment();
        soundFragment = new SoundFragment();
        listImageFragment = new ListImageFragment(this, listUriImage);

        setupVideoView();
    }

    private void setupVideoView() {
        String path = PathVideo.getPathTempImg(this);

        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File tmp : files) {
                listUriImage.add(tmp.getPath());
            }
        }

        videoView = binding.videoView;
        videoView.setOnPreparedListener(this);
        if (listUriImage.size() > 1) {
            FragmentManager manager = getSupportFragmentManager();
            MyPagerAdapter adapter = new MyPagerAdapter(this, manager, listUriImage, listImageFragment, effectsFragment, soundFragment, frameFragment);
            binding.setAdapter(adapter);
            createVideoFromFolderTemp();
        } else {
            finish();
        }
    }

    private void createVideoFromFolderTemp() {
//        String path = PathVideo.getPathTempVideo(this) + "/temp.mp4";
//
//        String tmp = StringComand.addPictureFrameToVideo(this, path, "/data/data/com.example.demoslideimage/files/frame1.png");
//        Log.e(TAG, tmp);
//        EditImageHomeActivity.executeAsync(returnCode -> {
//            if (returnCode == FFmpeg.RETURN_CODE_SUCCESS) {
//                ShowLog.ShowLog(this, binding.getRoot(), "Them khung thanh cong", true);
//            } else {
//                ShowLog.ShowLog(this, binding.getRoot(), "Them khung that bai", false);
//            }
//        }, tmp);


        String cmd = StringComand.createVideo(this, listUriImage, TimeLoad.TIME_MEDIUM, null);
        Log.e(TAG, cmd);
        EditImageHomeActivity.executeAsync(result -> {
            if (result == FFmpeg.RETURN_CODE_SUCCESS) {
                ShowLog.ShowLog(this, binding.getRoot(), "Thanh cong", true);
                String path = PathVideo.getPathTempVideo(this) + "/temp.mp4";
                videoView.setVideoURI(Uri.parse(path));


            } else {
                ShowLog.ShowLog(this, binding.getRoot(), "That bai", false);
            }
        }, cmd);
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

    @Override
    public void onPrepared() {
        videoView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listUriImage.clear();
        String path = PathVideo.getPathTempImg(this);
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File tmp : files) {
                listUriImage.add(tmp.getPath());
            }
        }
        listImageFragment.changeListImage(listUriImage);
    }
}
