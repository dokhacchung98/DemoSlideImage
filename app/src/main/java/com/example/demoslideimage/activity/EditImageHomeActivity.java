package com.example.demoslideimage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.util.RunCallback;
import com.example.demoslideimage.R;
import com.example.demoslideimage.adapter.MyAdapterRecyclerViewImageList;
import com.example.demoslideimage.custom.GetListImageFromStorage;
import com.example.demoslideimage.databinding.ActivityEditImageHomeBinding;
import com.example.demoslideimage.handler.MyClickHandler;
import com.example.demoslideimage.model.ItemImage;
import com.example.demoslideimage.util.AsyncCommandTask;

import java.util.ArrayList;

public class EditImageHomeActivity extends AppCompatActivity implements MyClickHandler {

    private static String TAG = EditImageHomeActivity.class.getName();
    private ActivityEditImageHomeBinding binding;
    private ArrayList<ItemImage> listItemImage;
    private MyAdapterRecyclerViewImageList adapterRecyclerView;

    public static final void startInternt(Context context) {
        Intent intent = new Intent(context, EditImageHomeActivity.class);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(context, R.anim.fade_right_to_left_enter, R.anim.fade_right_to_left_exit);
        context.startActivity(intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_image_home);
        binding.setHandler(this);
        ActivityCompat.requestPermissions(EditImageHomeActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        initData();
    }

    public static void executeAsync(final RunCallback runCallback, final String arguments) {
        final AsyncCommandTask asyncCommandTask = new AsyncCommandTask(runCallback);
        asyncCommandTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, arguments);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(EditImageHomeActivity.this, getResources().getText(R.string.denied_permision), Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void initData() {
        listItemImage = new ArrayList<>();

        getAllImageInStorage();
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/videooo.mp4";
        Log.e(this.getClass().getName(), "PATH: " + path);
        String cmd = "-loop 1 -t 5 -i "
                + listItemImage.get(0).getResourceImage()
                + " -loop 1 -t 3 -i "
                + listItemImage.get(1).getResourceImage()
                + " -loop 1 -t 3 -i "
                + listItemImage.get(4).getResourceImage()
                + " -loop 1 -t 3 -i "
                + listItemImage.get(2).getResourceImage()
                + " -loop 1 -t 3 -i "
                + listItemImage.get(3).getResourceImage()
                + " -filter_complex [0:v]trim=duration=3,fade=t=out:st=2.5:d=0.5[v0];[1:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v1];[2:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v2];[3:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v3];[v0][v1][v2][v3]concat=n=4:v=1:a=0,format=yuv420p[v] -map [v] -preset ultrafast "
                + path;
        EditImageHomeActivity.executeAsync(result -> {
            Log.e(TAG, "result: " + result + ", success: " + FFmpeg.RETURN_CODE_SUCCESS + ", cancel: " + FFmpeg.RETURN_CODE_CANCEL);
//            CreateVideoActivity.Internt(this);
        }, cmd);


        adapterRecyclerView = new MyAdapterRecyclerViewImageList(listItemImage, this, false);
        binding.setMyAdapter(adapterRecyclerView);
        binding.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void getAllImageInStorage() {
        listItemImage = GetListImageFromStorage.getListImageFromStorage(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBackPress:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onClickWithData(View view, Object value) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_left_to_right_enter, R.anim.fade_left_to_right_exit);
    }
}
