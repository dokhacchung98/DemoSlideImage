package com.example.demoslideimage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoslideimage.R;
import com.example.demoslideimage.databinding.ItemImageBinding;
import com.example.demoslideimage.handler.CustomItemClickListener;
import com.example.demoslideimage.handler.MyClickHandler;
import com.example.demoslideimage.model.ItemImage;
import com.example.demoslideimage.BR;

import java.util.ArrayList;

public class MyAdapterRecyclerView extends RecyclerView.Adapter<MyAdapterRecyclerView.MyViewHolder>
        implements CustomItemClickListener, MyClickHandler {

    private ArrayList<ItemImage> listItemImage;
    private Context context;
    private boolean isActivityEdit = false;

    private ItemImageBinding itemImageBinding;

    public MyAdapterRecyclerView(ArrayList<ItemImage> listItemImage, Context context) {
        this.listItemImage = listItemImage;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemImageBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_image,
                parent,
                false
        );
        return new MyViewHolder(itemImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemImage item = listItemImage.get(position);
        holder.bind(item);
        holder.itemImageBinding.setItemClickListener(this::ItemClick);
        holder.itemImageBinding.setHandler(this);
        holder.itemImageBinding.setIsActvityEdit(isActivityEdit);
    }

    @Override
    public int getItemCount() {
        return listItemImage.size();
    }

    @Override
    public void ItemClick(ItemImage item) {
        Toast.makeText(context, "click: " + item.getNameImage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onClickWithData(View view, Object value) {
        ItemImage item = (ItemImage) value;
        switch (view.getId()) {
            case R.id.btnDelete:
                if (listItemImage.contains(item)) {
                    listItemImage.remove(item);
                    notifyDataSetChanged();
                }
                break;
            case R.id.btnEdit:
                Toast.makeText(context, "Edit " + item.getNameImage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ItemImageBinding itemImageBinding;

        public MyViewHolder(ItemImageBinding itemImageBinding) {
            super(itemImageBinding.getRoot());
            this.itemImageBinding = itemImageBinding;
        }

        public void bind(Object obj) {
            itemImageBinding.setVariable(BR.model, obj);
            itemImageBinding.executePendingBindings();
        }
    }
}
