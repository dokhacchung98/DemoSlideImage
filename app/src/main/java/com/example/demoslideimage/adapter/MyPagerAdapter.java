package com.example.demoslideimage.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.demoslideimage.fragment.FragmentEffects;
import com.example.demoslideimage.fragment.FragmentFrame;
import com.example.demoslideimage.fragment.FragmentListImage;
import com.example.demoslideimage.fragment.FragmentSound;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FragmentListImage();
                break;
            case 1:
                fragment = new FragmentEffects();
                break;
            case 2:
                fragment = new FragmentSound();
                break;
            case 3:
                fragment = new FragmentFrame();
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
