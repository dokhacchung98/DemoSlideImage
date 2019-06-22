package com.example.demoslideimage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.example.demoslideimage.R;
import com.example.demoslideimage.adapter.MyAdapterRecyclerView;
import com.example.demoslideimage.databinding.ActivityMainBinding;
import com.example.demoslideimage.model.ItemImage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<ItemImage> listItemImage;
    private MyAdapterRecyclerView adapterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initData();
    }

    private void initData() {
        listItemImage = new ArrayList<>();
        listItemImage.add(new ItemImage("ảnh 1", R.drawable.a1));
        listItemImage.add(new ItemImage("ảnh 2", R.drawable.a2));
        listItemImage.add(new ItemImage("ảnh 3", R.drawable.a3));
        listItemImage.add(new ItemImage("ảnh 4", R.drawable.a4));
        listItemImage.add(new ItemImage("ảnh 5", R.drawable.a5));
        listItemImage.add(new ItemImage("ảnh 6", R.drawable.a6));
        listItemImage.add(new ItemImage("ảnh 7", R.drawable.a7));
        listItemImage.add(new ItemImage("ảnh 8", R.drawable.a8));
        listItemImage.add(new ItemImage("ảnh 9", R.drawable.a9));
        listItemImage.add(new ItemImage("ảnh 10", R.drawable.a10));
        listItemImage.add(new ItemImage("ảnh 11", R.drawable.a11));
        listItemImage.add(new ItemImage("ảnh 12", R.drawable.a12));
        listItemImage.add(new ItemImage("ảnh 13", R.drawable.a13));

        adapterRecyclerView = new MyAdapterRecyclerView(listItemImage, this);
        binding.setMyAdapter(adapterRecyclerView);
        binding.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
