package com.example.demoslideimage.extensions;

import android.graphics.Bitmap;

public class ResizeImage {
    public static int WidthImg = 400;
    public static int HeightImg = 800;

    public static Bitmap resizeImage(Bitmap oldBitMap) {
        return Bitmap.createScaledBitmap(oldBitMap, WidthImg, HeightImg, true);
    }
}
