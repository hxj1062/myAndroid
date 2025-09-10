package com.example.look.views;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.example.look.MyBaseActivity;
import com.example.look.R;

import java.io.File;

/**
 * @description: 拍照
 */
public class CameraTestActivity extends MyBaseActivity implements View.OnClickListener {


    private ImageView takePicture;
    private File normalFile;
    public Uri fileUri;
    private final String PHOTO_FILE_NAME = "tempPhoto.jpg";
    private final int RESULT_CODE_TAKE_PICTURE = 01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);
        buildActionBar();
//        normalFile = new File(Environment.getExternalStorageDirectory() + "/photo", PHOTO_FILE_NAME);
        // File directory = new File(Environment.getExternalStorageDirectory() + "/photo");
        //  directory.mkdirs();
        normalFile = new File(Environment.getExternalStorageDirectory() + "/Pictures", PHOTO_FILE_NAME);
//        try {
//            normalFile = File.createTempFile(
//                    PHOTO_FILE_NAME,  /* prefix */
//                    ".jpg",         /* suffix */
//                    directory    /* directory */
//            );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        initView();

    }

    private void initView() {
        takePicture = findViewById(R.id.iv_takePicture);
        takePicture.setOnClickListener(this);
    }


    // 拍照
    private void takePicture() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(this, "com.example.look.provider", normalFile);
        } else {
            fileUri = Uri.fromFile(normalFile);
        }
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        takePictureIntent.putExtra("return_data", true);
        startActivityForResult(takePictureIntent, RESULT_CODE_TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CODE_TAKE_PICTURE) {
            String a = normalFile.getPath();
            takePicture.setImageBitmap(BitmapFactory.decodeFile(a));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_takePicture:
                takePicture();
                break;
            default:
                break;
        }
    }
}