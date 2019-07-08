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
import com.example.demoslideimage.extensions.PathVideo;
import com.example.demoslideimage.extensions.ShowLog;
import com.example.demoslideimage.extensions.StringComand;
import com.example.demoslideimage.extensions.StringDemo;
import com.example.demoslideimage.extensions.TimeLoad;
import com.example.demoslideimage.fragment.EffectsFragment;
import com.example.demoslideimage.fragment.FrameFragment;
import com.example.demoslideimage.fragment.ListImageFragment;
import com.example.demoslideimage.fragment.SoundFragment;
import com.example.demoslideimage.handler.CallBackAddFrame;
import com.example.demoslideimage.handler.CallBackChangeList;
import com.example.demoslideimage.handler.MyClickHandler;
import com.example.demoslideimage.model.ItemRow;
import com.example.demoslideimage.util.SettingVideo;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;

public class CreateVideoActivity extends BaseActivity implements MyClickHandler, OnPreparedListener, CallBackAddFrame {
    private ActivityCreateVideoBinding binding;
    private static final String TAG = CreateVideoHomeActivity.class.getName();
    private ArrayList<String> listUriImage;
    private ListImageFragment listImageFragment;
    private EffectsFragment effectsFragment;
    private FrameFragment frameFragment;
    private SoundFragment soundFragment;
    private SettingVideo settingVideo;
    private SettingVideo.Builder builderSettingVideo;
    private String path;

    private VideoView videoView;

    public static void startInternt(Context context) {
        Intent intent = new Intent(context, CreateVideoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_video);

        builderSettingVideo = new SettingVideo.Builder();

        listUriImage = new ArrayList<>();
        binding.setHandler(this);

        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.viewPager));

        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));

        ArrayList<ItemRow> listFrame = new ArrayList<>();
        File directory = new File("/data/data/com.example.demoslideimage/files/frame");
        File[] files = directory.listFiles();
        if (files != null) {
            for (File tmp : files) {
                listFrame.add(new ItemRow(tmp.getPath()));
            }
        }
//        listFrame.add(new ItemRow(R.drawable.f2));
//        listFrame.add(new ItemRow(R.drawable.f3));
//        listFrame.add(new ItemRow(R.drawable.f4));
//        listFrame.add(new ItemRow(R.drawable.f5));
//        listFrame.add(new ItemRow(R.drawable.f6));
//        listFrame.add(new ItemRow(R.drawable.f7));
//        listFrame.add(new ItemRow(R.drawable.f8));

        frameFragment = new FrameFragment(this, listFrame, this);
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
        } else {
            finish();
        }
    }

    private void createVideoFromFolderTemp() {
        Log.e(TAG, "createVideoFromFolderTemp() start create video");
//        String cmd = StringComand.createVideo(this, listUriImage, TimeLoad.TIME_MEDIUM, null);
        String path = PathVideo.getPathTempVideo(this);
        StringDemo stringDemo = new StringDemo(listUriImage, path + "/temp.mp4");
        String cmd = stringDemo.toString();
        Log.e(TAG, cmd);
        EditImageHomeActivity.executeAsync(result -> {
            if (result == FFmpeg.RETURN_CODE_SUCCESS) {
                Log.e(TAG, "createVideoFromFolderTemp() success: " + settingVideo.getPathFrame());
                if (settingVideo.getPathFrame() == null || settingVideo.getPathFrame().isEmpty()) {
                    setPathVideoView("temp.mp4");
                } else {
                    addFrameToVideo();
                }
            } else {
                ShowLog.ShowLog(this, binding.getRoot(), "That bai", false);
            }
        }, cmd);
    }

    private void addFrameToVideo() {
        Log.e(TAG, "addFrameToVideo() start add frame");
        String pathFrame = settingVideo.getPathFrame();
        String cmd = StringComand.addPictureFrameToVideo(this, pathFrame);
        EditImageHomeActivity.executeAsync(result -> {
            if (result == FFmpeg.RETURN_CODE_SUCCESS) {
                Log.e(TAG, "addFrameToVideo() success");
                if (settingVideo.getPathSound() == null || settingVideo.getPathSound().isEmpty()) {
                    setPathVideoView("temp1.mp4");
                } else {
                    addSoundToVideo();
                }
            } else {
                ShowLog.ShowLog(this, binding.getRoot(), "That bai", false);
            }
        }, cmd);
    }

    private void addSoundToVideo() {
        Log.e(TAG, "addSoundToVideo() start add Sound");
        String pathSound = settingVideo.getPathSound();
        String cmd = StringComand.addMusicToVideoCancelOldSound(this, pathSound);
        EditImageHomeActivity.executeAsync(result -> {
            if (result == FFmpeg.RETURN_CODE_SUCCESS) {
                Log.e(TAG, "addSoundToVideo() success");
                setPathVideoView("temp2.mp4");
            } else {
                ShowLog.ShowLog(this, binding.getRoot(), "That bai", false);
            }
        }, cmd);
    }

    private void setPathVideoView(String name) {
        ShowLog.ShowLog(this, binding.getRoot(), "Thanh cong", true);
        path = PathVideo.getPathTempVideo(this) + "/" + name;
        videoView.setVideoURI(Uri.parse(path));
    }

    @Override
    public void onClick(View view) {
        settingVideo = builderSettingVideo.build();
        if (view.getId() == R.id.btnCreate) {
            createVideoFromFolderTemp();
        }
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

    @Override
    public void AddFrame(String source) {
        builderSettingVideo.setPathFrame(source);
    }
}
