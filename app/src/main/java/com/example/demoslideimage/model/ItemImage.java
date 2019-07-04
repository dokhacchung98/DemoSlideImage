package com.example.demoslideimage.model;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.demoslideimage.BR;

public class ItemImage extends BaseObservable {
    private String nameImage;
    private String resourceImage;
    private boolean isSelected;

    public ItemImage(String nameImage, String resourceImage, boolean isSelected) {
        this.nameImage = nameImage;
        this.resourceImage = resourceImage;
        this.isSelected = isSelected;
    }

    public ItemImage(String nameImage, String resourceImage) {
        this.nameImage = nameImage;
        this.resourceImage = resourceImage;
    }

    public ItemImage() {
    }

    @Bindable
    public String getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(String resourceImage) {
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

    @Bindable
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        notifyPropertyChanged(BR.selected);
    }

    @NonNull
    @Override
    public String toString() {
        return resourceImage.toString();
    }
}
