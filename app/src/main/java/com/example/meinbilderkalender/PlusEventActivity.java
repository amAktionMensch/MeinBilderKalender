package com.example.meinbilderkalender;

import android.content.Intent;
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

        ImageView iv1 = findViewById(R.id.iv1);
        ImageView iv2 = findViewById(R.id.iv2);
        ImageView iv3 = findViewById(R.id.iv3);
        ImageView iv4 = findViewById(R.id.iv4);

        File imageFile1 = new File("/sdcard/gallery_photo_4.jpg");
        File imageFile2 = new File("/sdcard/gallery_photo_4.jpg");

        Bitmap bitmap1 = BitmapFactory.decodeFile(imageFile1.getAbsolutePath());
        Bitmap bitmap2 = BitmapFactory.decodeFile(imageFile2.getAbsolutePath());

        iv1.setImageBitmap(bitmap1);
        iv2.setImageBitmap(bitmap2);

        int id_event = 3;

        Intent myIntent = new Intent(PlusEventActivity.this, MainActivity.class);
        myIntent.putExtra("event", id_event);
        PlusEventActivity.this.startActivity(myIntent);

    }
}
