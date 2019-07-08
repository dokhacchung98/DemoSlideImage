package com.example.demoslideimage.util;

import com.example.demoslideimage.extensions.EnumEffect;
import com.example.demoslideimage.extensions.SizeVideo;

import java.util.ArrayList;

public class SettingVideo {
    private ArrayList<String> listImage;
    private String pathSound;
    private String pathFrame;
    private int width;
    private int height;
    private int fps;
    private float transitionDuration;
    private int imageDuration;
    private String backGroundColor;
    private EnumEffect effect;


    public SettingVideo(Builder builder) {
        this.listImage = builder.listImage;
        this.pathFrame = builder.pathFrame;
        this.pathSound = builder.pathSound;
        this.width = builder.width;
        this.height = builder.height;
        this.fps = builder.fps;
        this.transitionDuration = builder.transitionDuration;
        this.imageDuration = builder.imageDuration;
        this.backGroundColor = builder.backGroundColor;
        this.effect = builder.effect;
    }

    public ArrayList<String> getListImage() {
        return listImage;
    }

    public String getPathSound() {
        return pathSound;
    }

    public String getPathFrame() {
        return pathFrame;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFps() {
        return fps;
    }

    public float getTransitionDuration() {
        return transitionDuration;
    }

    public int getImageDuration() {
        return imageDuration;
    }

    public String getBackGroundColor() {
        return backGroundColor;
    }

    public float getTransitionFrameCount() {
        return transitionDuration * fps;
    }

    public int getImageFrameCount() {
        return imageDuration * fps;
    }

    public EnumEffect getEffect() {
        return effect;
    }

    public static class Builder {
        private ArrayList<String> listImage = null;
        private String pathSound = "/data/data/com.example.demoslideimage/files/a.wav";
        private String pathFrame = "";
        private int width = SizeVideo.W_720;
        private int height = SizeVideo.H_720;
        private int fps = 30;
        private float transitionDuration = 1;
        private int imageDuration = 1;
        private String backGroundColor = "black";
        private EnumEffect effect = EnumEffect.BAR_HORIZONTAL;

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

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setFps(int fps) {
            this.fps = fps;
            return this;
        }

        public Builder setTransitionDuration(float transitionDuration) {
            this.transitionDuration = transitionDuration;
            return this;
        }

        public Builder setImageDuration(int imageDuration) {
            this.imageDuration = imageDuration;
            return this;
        }

        public Builder setBackGroundColor(String backGroundColor) {
            this.backGroundColor = backGroundColor;
            return this;
        }

        public Builder setEffect(EnumEffect effect) {
            this.effect = effect;
            return this;
        }

        public SettingVideo build() {
            return new SettingVideo(this);
        }
    }
}
