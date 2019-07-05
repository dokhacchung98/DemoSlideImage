package com.example.demoslideimage.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.demoslideimage.R;
import com.example.demoslideimage.adapter.MyAdapterFrameVideo;
import com.example.demoslideimage.databinding.FragmentFramesBinding;
import com.example.demoslideimage.handler.CallBackAddFrame;
import com.example.demoslideimage.model.ItemRow;

import java.util.ArrayList;

public class FrameFragment extends Fragment {
    private FragmentFramesBinding binding;
    private MyAdapterFrameVideo myAdapterFrameVideo;
    private Activity activity;
    private ArrayList<ItemRow> listItem;
    private CallBackAddFrame callBackAddFrame;

    public FrameFragment(Activity activity, ArrayList<ItemRow> listItem, CallBackAddFrame callBackAddFrame) {
        this.activity = activity;
        this.callBackAddFrame = callBackAddFrame;
        this.listItem = listItem;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_frames, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        myAdapterFrameVideo = new MyAdapterFrameVideo(activity, listItem, callBackAddFrame);
        binding.setMyAdapter(myAdapterFrameVideo);
    }
}
