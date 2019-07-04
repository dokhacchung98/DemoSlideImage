package com.example.demoslideimage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoslideimage.BR;
import com.example.demoslideimage.R;
import com.example.demoslideimage.activity.EditImageActivity;
import com.example.demoslideimage.databinding.ItemImageHorizontalBinding;
import com.example.demoslideimage.handler.CustomItemClickListener;
import com.example.demoslideimage.handler.MyClickHandler;
import com.example.demoslideimage.handler.MySelectedItem;
import com.example.demoslideimage.model.ItemImage;

import java.util.ArrayList;

public class MyAdapterRecyclerViewImageHorizontal extends RecyclerView.Adapter<MyAdapterRecyclerViewImageHorizontal.MyViewHolder>
        implements CustomItemClickListener, MyClickHandler {

    private ArrayList listItemImage;
    private Context context;
    private MySelectedItem mySelectedItem;
    private ItemImageHorizontalBinding itemImageBinding;

    public MyAdapterRecyclerViewImageHorizontal(ArrayList listItemImage, Context context) {
        this.listItemImage = listItemImage;
        this.context = context;
    }

    public void setMySelectedItem(MySelectedItem mySelectedItem) {
        this.mySelectedItem = mySelectedItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemImageBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_image_horizontal,
                parent,
                false
        );
        return new MyViewHolder(itemImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String item = listItemImage.get(position).toString();
        holder.bind(item);
        holder.itemImageBinding.setItemClickListener(this::ItemClick);
        holder.itemImageBinding.setHandler(this);
    }

    @Override
    public int getItemCount() {
        return listItemImage.size();
    }

    @Override
    public void ItemClick(ItemImage item) {
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onClickWithData(View view, Object value) {
        String item = (String) value;
        if (view.getId() == R.id.btnEdit) {
            EditImageActivity.startInterntWithAnimTransition(context, item, itemImageBinding.imgThumbnail, true);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemImageHorizontalBinding itemImageBinding;

        MyViewHolder(ItemImageHorizontalBinding itemImageBinding) {
            super(itemImageBinding.getRoot());
            this.itemImageBinding = itemImageBinding;
        }

        public void bind(Object obj) {
            itemImageBinding.setVariable(BR.model, obj);
            itemImageBinding.executePendingBindings();
        }
    }
}
