package com.example.demoslideimage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.demoslideimage.R;
import com.example.demoslideimage.adapter.MyAdapterRecyclerView;
import com.example.demoslideimage.databinding.ActivityMainBinding;
import com.example.demoslideimage.ffmpeg.AsyncCommandExecutor;
import com.example.demoslideimage.ffmpeg.Command;
import com.example.demoslideimage.ffmpeg.MyFFmpeg;
import com.example.demoslideimage.ffmpeg.ProcessingListener;
import com.example.demoslideimage.model.ItemImage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProcessingListener {

    private ActivityMainBinding binding;
    private ArrayList<ItemImage> listItemImage;
    private MyAdapterRecyclerView adapterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initData();
    }

    private void initData() {
        listItemImage = new ArrayList<>();

//        listItemImage.add(new ItemImage("ảnh 1", R.drawable.a1));
//        listItemImage.add(new ItemImage("ảnh 2", R.drawable.a2));
//        listItemImage.add(new ItemImage("ảnh 3", R.drawable.a3));
//        listItemImage.add(new ItemImage("ảnh 4", R.drawable.a4));
//        listItemImage.add(new ItemImage("ảnh 5", R.drawable.a5));
//        listItemImage.add(new ItemImage("ảnh 6", R.drawable.a6));
//        listItemImage.add(new ItemImage("ảnh 7", R.drawable.a7));
//        listItemImage.add(new ItemImage("ảnh 8", R.drawable.a8));
//        listItemImage.add(new ItemImage("ảnh 9", R.drawable.a9));
//        listItemImage.add(new ItemImage("ảnh 10", R.drawable.a10));
//        listItemImage.add(new ItemImage("ảnh 11", R.drawable.a11));
//        listItemImage.add(new ItemImage("ảnh 12", R.drawable.a12));
//        listItemImage.add(new ItemImage("ảnh 13", R.drawable.a13));

        String path = getFilesDir().getAbsolutePath() + "/videoout.mp4";

        getAllImageInStorage();
        String cmd = "ffmpeg -loop 1 -t 3 -i "
                + listItemImage.get(0).getResourceImage()
                + " -loop 1 -t 3 -i "
                + listItemImage.get(1).getResourceImage()
                + " -loop 1 -t 3 -i "
                + listItemImage.get(2).getResourceImage()
                + " -loop 1 -t 3 -i "
                + listItemImage.get(3).getResourceImage()
                + " -filter_complex [0:v]trim=duration=3,fade=t=out:st=2.5:d=0.5[v0];[1:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v1];[2:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v2];[3:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v3];[v0][v1][v2][v3]concat=n=4:v=1:a=0,format=yuv420p[v] -map [v] -preset ultrafast ";

        ArrayList<String> commandList = new ArrayList<>();
//        commandList.add("-loop");
//        commandList.add("1");
//        commandList.add("-t");
//        commandList.add("3");
//        commandList.add("-i");
//        commandList.add(listItemImage.get(0).getResourceImage());
//
//        commandList.add("-loop");
//        commandList.add("1");
//        commandList.add("-t");
//        commandList.add("3");
//        commandList.add("-i");
//        commandList.add(listItemImage.get(1).getResourceImage());
//
//        commandList.add("-loop");
//        commandList.add("1");
//        commandList.add("-t");
//        commandList.add("3");
//        commandList.add("-i");
//        commandList.add(listItemImage.get(2).getResourceImage());
//
//        commandList.add("-loop");
//        commandList.add("1");
//        commandList.add("-t");
//        commandList.add("3");
//        commandList.add("-i");
//        commandList.add(listItemImage.get(3).getResourceImage());
//
//        commandList.add("-filter_complex");
//        commandList.add("[0:v]trim=duration=3,fade=t=out:st=2.5:d=0.5[v0];[1:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v1];[2:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v2];[3:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v3];[v0][v1][v2][v3]concat=n=4:v=1:a=0,format=yuv420p[v]");
//        commandList.add("-map");
//        commandList.add("-preset");
//        commandList.add("ultrafast");
//        commandList.add(path);

        commandList.add("-i");
        commandList.add("/storage/emulated/0/DCIM/Camera/VID_20190626_111020.mp4");
        commandList.add("/storage/emulated/0/DCIM/Camera/VID_20190626_111020.avi");

        final MyFFmpeg myFFmpeg = new MyFFmpeg();
        final Command command = myFFmpeg.createCommand()
//                .overwriteOutput()
//                .customCommand("ffmpeg -loop 1 -t 3 -i ")
//                .inputPath(listItemImage.get(0).getResourceImage())
//                .customCommand(" -loop 1 -t 3 -i ")
//                .inputPath(listItemImage.get(1).getResourceImage())
//                .customCommand(" -loop 1 -t 3 -i ")
//                .inputPath(listItemImage.get(2).getResourceImage())
//                .customCommand(" -loop 1 -t 3 -i ")
//                .inputPath(listItemImage.get(3).getResourceImage())
//                .customCommand(" -filter_complex [0:v]trim=duration=3,fade=t=out:st=2.5:d=0.5[v0];[1:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v1];[2:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v2];[3:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v3];[v0][v1][v2][v3]concat=n=4:v=1:a=0,format=yuv420p[v] -map [v] -preset ultrafast ")
//                .outputPath(path)
//                .copyVideoCodec()
//                .experimentalFlag()
                .buildCustomCommand(commandList);

        new AsyncCommandExecutor(command, this).execute();

        adapterRecyclerView = new MyAdapterRecyclerView(listItemImage, this);
        binding.setMyAdapter(adapterRecyclerView);
        binding.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void getAllImageInStorage() {
        Uri uri;
        Cursor cursor;
        int column_index_data;
        String absolutePathOfImage;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listItemImage.add(new ItemImage(absolutePathOfImage, absolutePathOfImage));
        }
    }

    @Override
    public void onSuccess(String path) {
        Intent intent = new Intent(this, CreateVideoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailure(int returnCode) {

    }
}
