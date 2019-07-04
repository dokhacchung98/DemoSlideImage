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
import com.example.demoslideimage.databinding.FragmentEffectBinding;

public class EffectsFragment extends Fragment {
    private FragmentEffectBinding binding;

    public EffectsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_effect, container, false);
        return binding.getRoot();
    }
}
