package com.example.demoslideimage.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoslideimage.custom.ItemOffSetDecoration;
import com.squareup.picasso.Picasso;

import java.io.File;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resoucre) {
        Picasso.get().load(resoucre).resize(800, 1200).into(imageView);
    }

    @androidx.databinding.BindingAdapter("android:src")
    public static void setImageDrawable(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    @androidx.databinding.BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        if (!url.isEmpty()) {
            Picasso.get().load(url).into(imageView);
        }
    }

    @androidx.databinding.BindingAdapter({"app:imageUri"})
    public static void loadImageUri(ImageView imageView, String uri) {
        if (!uri.isEmpty()) {
            Picasso.get().load(new File(uri)).centerCrop().fit().into(imageView);
        }
    }

    @androidx.databinding.BindingAdapter("app:layoutGridManager")
    public static void setLayoutManager(RecyclerView recyclerView, GridLayoutManager gridLayoutManager) {
        if (gridLayoutManager != null) {
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }

    @androidx.databinding.BindingAdapter("app:layoutLayoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        if (linearLayoutManager != null) {
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    @androidx.databinding.BindingAdapter("app:adapter")
    public static void setAdapter(RecyclerView recyclerView, MyAdapterRecyclerViewImageList adapterRecyclerView) {
        if (adapterRecyclerView != null) {
            recyclerView.setAdapter(adapterRecyclerView);
            ItemOffSetDecoration decoration = new ItemOffSetDecoration(0);
            recyclerView.addItemDecoration(decoration);
        }
    }
}
