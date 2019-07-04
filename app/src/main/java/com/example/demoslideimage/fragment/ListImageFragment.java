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
import com.example.demoslideimage.adapter.MyAdapterRecyclerViewImageHorizontal;
import com.example.demoslideimage.databinding.FragmentListImageBinding;
import com.example.demoslideimage.handler.CallBackChangeList;
import com.example.demoslideimage.handler.MyClickHandler;

import java.util.ArrayList;

public class ListImageFragment extends Fragment implements MyClickHandler, CallBackChangeList {
    private FragmentListImageBinding binding;
    private ArrayList listImage;
    private Activity activity;
    private MyAdapterRecyclerViewImageHorizontal adapterRecyclerViewImageHorizontal;

    public ListImageFragment(Activity activity, ArrayList listImage) {
        this.listImage = listImage;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_image, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.setHandler(this);
        adapterRecyclerViewImageHorizontal = new MyAdapterRecyclerViewImageHorizontal(listImage, activity);
        binding.setMyAdapter(adapterRecyclerViewImageHorizontal);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onClickWithData(View view, Object value) {

    }

    @Override
    public void changeListImage(ArrayList listImage) {
        this.listImage = listImage;
        if (adapterRecyclerViewImageHorizontal != null)
            adapterRecyclerViewImageHorizontal.notifyDataSetChanged();
    }
}