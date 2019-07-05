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
import com.example.demoslideimage.databinding.ItemRowBinding;
import com.example.demoslideimage.handler.CallBackAddFrame;
import com.example.demoslideimage.handler.CustomItemClickListener;
import com.example.demoslideimage.handler.MyClickHandler;
import com.example.demoslideimage.handler.MySelectedItem;
import com.example.demoslideimage.model.ItemImage;
import com.example.demoslideimage.model.ItemRow;

import java.util.ArrayList;

public class MyAdapterFrameVideo extends RecyclerView.Adapter<MyAdapterFrameVideo.MyViewHolder>
        implements CustomItemClickListener, MyClickHandler {

    private ArrayList<ItemRow> listItem;
    private Context context;
    private MySelectedItem mySelectedItem;
    private ItemRowBinding binding;
    private CallBackAddFrame callBackAddFrame;

    public MyAdapterFrameVideo(Context context, ArrayList<ItemRow> listItem, CallBackAddFrame callBackAddFrame) {
        this.listItem = listItem;
        this.context = context;
        this.callBackAddFrame = callBackAddFrame;
    }

    public void setMySelectedItem(MySelectedItem mySelectedItem) {
        this.mySelectedItem = mySelectedItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_row,
                parent,
                false
        );
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemRow item = listItem.get(position);
        holder.bind(item);
        holder.binding.setHandler(this);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    @Override
    public void ItemClick(ItemImage item) {
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onClickWithData(View view, Object value) {
        ItemRow item = (ItemRow) value;
        if (view.getId() == R.id.imgThumbnail) {
            callBackAddFrame.AddFrame(item.getPath());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemRowBinding binding;

        MyViewHolder(ItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Object obj) {
            binding.setVariable(BR.model, obj);
            binding.executePendingBindings();
        }
    }
}
