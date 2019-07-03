package com.example.demoslideimage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoslideimage.R;
import com.example.demoslideimage.activity.EditImageActivity;
import com.example.demoslideimage.databinding.ItemImageBinding;
import com.example.demoslideimage.handler.CustomItemClickListener;
import com.example.demoslideimage.handler.MyClickHandler;
import com.example.demoslideimage.handler.MySelectedItem;
import com.example.demoslideimage.model.ItemImage;
import com.example.demoslideimage.BR;

import java.util.ArrayList;

public class MyAdapterRecyclerViewImageList extends RecyclerView.Adapter<MyAdapterRecyclerViewImageList.MyViewHolder>
        implements CustomItemClickListener, MyClickHandler {

    private ArrayList<ItemImage> listItemImage;
    private Context context;
    private boolean isActivityVideo;
    private MySelectedItem mySelectedItem;

    private ItemImageBinding itemImageBinding;

    public MyAdapterRecyclerViewImageList(ArrayList<ItemImage> listItemImage, Context context, boolean isActivityVideo) {
        this.listItemImage = listItemImage;
        this.context = context;
        this.isActivityVideo = isActivityVideo;
    }

    public void setMySelectedItem(MySelectedItem mySelectedItem) {
        this.mySelectedItem = mySelectedItem;
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
        holder.itemImageBinding.setIsActvityEdit(isActivityVideo);
    }

    @Override
    public int getItemCount() {
        return listItemImage.size();
    }

    @Override
    public void ItemClick(ItemImage item) {
        if (mySelectedItem != null) {
            mySelectedItem.selectedItem(item);
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onClickWithData(View view, Object value) {
        ItemImage item = (ItemImage) value;
        if (view.getId() == R.id.btnEdit) {
            EditImageActivity.startInternt(context, item.getResourceImage());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemImageBinding itemImageBinding;

        MyViewHolder(ItemImageBinding itemImageBinding) {
            super(itemImageBinding.getRoot());
            this.itemImageBinding = itemImageBinding;
        }

        public void bind(Object obj) {
            itemImageBinding.setVariable(BR.model, obj);
            itemImageBinding.executePendingBindings();
        }
    }
}
