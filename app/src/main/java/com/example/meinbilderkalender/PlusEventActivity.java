package com.example.meinbilderkalender;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

public class PlusEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plusevents);

        File imageFile = new File("/sdcard/gallery_photo_4.jpg");
        ImageView iv1 = findViewById(R.id.iv1);
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        iv1.setImageBitmap(bitmap);
    }
}
