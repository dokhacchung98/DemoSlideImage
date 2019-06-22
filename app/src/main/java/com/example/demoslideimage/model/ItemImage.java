package com.example.demoslideimage.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.demoslideimage.BR;

public class ItemImage extends BaseObservable {
    private String nameImage;
    private int resourceImage;

    public ItemImage(String nameImage, int resourceImage) {
        this.nameImage = nameImage;
        this.resourceImage = resourceImage;
    }

    public ItemImage() {
    }

    @Bindable
    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
        notifyPropertyChanged(BR.resourceImage);
    }

    @Bindable
    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
        notifyPropertyChanged(BR.nameImage);
    }
}
