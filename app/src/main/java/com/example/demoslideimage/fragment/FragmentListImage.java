package com.example.demoslideimage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.demoslideimage.R;
import com.example.demoslideimage.databinding.FragmentListImageBinding;

public class FragmentListImage extends Fragment {
    private FragmentListImageBinding binding;

    public FragmentListImage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_image, container, false);
        return binding.getRoot();
    }
}