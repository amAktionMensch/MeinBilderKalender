package com.example.meinbilderkalender;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class PlusEventActivity extends AppCompatActivity {

    public String id_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plusevents);

        ImageView iv1 = findViewById(R.id.iv1);
        ImageView iv2 = findViewById(R.id.iv2);
        ImageView iv3 = findViewById(R.id.iv3);
        ImageView iv4 = findViewById(R.id.iv4);

        /*File imageFile1 = new File("/sdcard/gallery_photo_4.jpg");
        File imageFile2 = new File("/sdcard/gallery_photo_4.jpg");
        File imageFile3 = new File();
        File imageFile4 = new File();

        Bitmap bitmap1 = BitmapFactory.decodeFile(imageFile1.getAbsolutePath());
        Bitmap bitmap2 = BitmapFactory.decodeFile(imageFile2.getAbsolutePath());
        Bitmap bitmap3 = BitmapFactory.decodeFile(imageFile3.getAbsolutePath());
        Bitmap bitmap4 = BitmapFactory.decodeFile(imageFile4.getAbsolutePath());

        iv1.setImageBitmap(bitmap1);
        iv2.setImageBitmap(bitmap2);
        iv3.setImageBitmap(bitmap3);
        iv4.setImageBitmap(bitmap4);*/

        iv1.setImageDrawable(getResources().getDrawable(R.drawable.bowling));
        iv2.setImageDrawable(getResources().getDrawable(R.drawable.roa));
        iv3.setImageDrawable(getResources().getDrawable(R.drawable.cake));
        iv4.setImageDrawable(getResources().getDrawable(R.drawable.chess));

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_event = "R.drawable.bowling";
                forward();
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_event = "R.drawable.roa";
                forward();
            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_event = "R.drawable.cake";
                forward();
            }
        });

        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_event = "R.drawable.chess";
                forward();
            }
        });
    }

    public void forward() {
        Intent myIntent = new Intent(PlusEventActivity.this, MainActivity.class);
        myIntent.putExtra("event", id_event);
        PlusEventActivity.this.startActivity(myIntent);
    }
}
