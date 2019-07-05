package com.example.demoslideimage.util;

import java.util.ArrayList;

public class SettingVideo {
    private ArrayList<String> listImage;
    private String pathSound;
    private String pathFrame;

    public ArrayList<String> getListImage() {
        return listImage;
    }

    public String getPathSound() {
        return pathSound;
    }

    public String getPathFrame() {
        return pathFrame;
    }

    public SettingVideo(Builder builder) {
        this.listImage = builder.listImage;
        this.pathFrame = builder.pathFrame;
        this.pathSound = builder.pathSound;
    }

    public static class Builder {
        private ArrayList<String> listImage = null;
        private String pathSound = "";
        private String pathFrame = "";

        public Builder setListImage(ArrayList<String> listImage) {
            this.listImage = listImage;
            return this;
        }

        public Builder setPathSound(String pathSound) {
            this.pathSound = pathSound;
            return this;
        }

        public Builder setPathFrame(String pathFrame) {
            this.pathFrame = pathFrame;
            return this;
        }

        public SettingVideo build() {
            return new SettingVideo(this);
        }
    }
}
