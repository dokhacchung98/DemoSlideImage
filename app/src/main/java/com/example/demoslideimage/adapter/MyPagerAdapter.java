package com.example.demoslideimage.adapter;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.demoslideimage.fragment.EffectsFragment;
import com.example.demoslideimage.fragment.FrameFragment;
import com.example.demoslideimage.fragment.ListImageFragment;
import com.example.demoslideimage.fragment.SoundFragment;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList listImage;
    private Activity activity;
    private ListImageFragment listImageFragment;
    private EffectsFragment effectsFragment;
    private SoundFragment soundFragment;
    private FrameFragment frameFragment;

    public MyPagerAdapter(Activity activity,
                          FragmentManager fm,
                          ArrayList listImage,
                          ListImageFragment listImageFragment,
                          EffectsFragment effectsFragment,
                          SoundFragment soundFragment,
                          FrameFragment frameFragment) {
        super(fm);
        this.listImage = listImage;
        this.activity = activity;
        this.frameFragment = frameFragment;
        this.effectsFragment = effectsFragment;
        this.listImageFragment = listImageFragment;
        this.soundFragment = soundFragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = listImageFragment;
                break;
            case 1:
                fragment = effectsFragment;
                break;
            case 2:
                fragment = soundFragment;
                break;
            case 3:
                fragment = frameFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "anh";
                break;
            case 1:
                title = "hieu ung";
                break;
            case 2:
                title = "nhac";
                break;
            case 3:
                title = "khung";
                break;
        }
        return title;
    }
}
