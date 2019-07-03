package com.example.demoslideimage.custom;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.demoslideimage.model.ItemImage;

import java.util.ArrayList;

public class GetListImageFromStorage {
    public static ArrayList<ItemImage> getListImageFromStorage(Context context) {
        ArrayList<ItemImage> listItemImage = new ArrayList<>();
        Uri uri;
        Cursor cursor;
        int column_index_data;
        String absolutePathOfImage;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = context.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listItemImage.add(new ItemImage(absolutePathOfImage.replaceAll("/", ""), absolutePathOfImage));
        }
        return listItemImage;
    }
}
