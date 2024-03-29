package com.example.demoslideimage.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.demoslideimage.BR;
import com.example.demoslideimage.R;

public class ItemRow extends BaseObservable {
    private int source;
    private String path;

    public ItemRow() {
    }

    public ItemRow(int source) {
        this.source = source;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ItemRow(String path) {
        this.path = path;
    }

    @Bindable
    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
        notifyPropertyChanged(BR.source);
    }

    public String getPath() {
        return path;
    }
}
