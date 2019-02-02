package com.example.meinbilderkalender;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Locale;

public class PlusEventActivity extends AppCompatActivity {

    public String id_event;

    private TextToSpeech textToSpeech;
    public boolean firstClick1 = true;
    public boolean firstClick2 = true;
    public boolean firstClick3 = true;
    public boolean firstClick4 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plusevents);

        ImageView iv1 = findViewById(R.id.iv1);
        ImageView iv2 = findViewById(R.id.iv2);
        ImageView iv3 = findViewById(R.id.iv3);
        ImageView iv4 = findViewById(R.id.iv4);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.GERMAN);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv1.setImageDrawable(getResources().getDrawable(R.drawable.bowling));
        iv2.setImageDrawable(getResources().getDrawable(R.drawable.roa));
        iv3.setImageDrawable(getResources().getDrawable(R.drawable.cake));
        iv4.setImageDrawable(getResources().getDrawable(R.drawable.chess));

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstClick1) {
                    int speechStatus = textToSpeech.speak("Bowling spielen", TextToSpeech.QUEUE_FLUSH, null);
                    firstClick1 = false;
                    firstClickTrue();
                } else {
                    id_event = "R.drawable.bowling";
                    forward();
                }
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstClick2) {
                    int speechStatus = textToSpeech.speak("Rikscha fahren", TextToSpeech.QUEUE_FLUSH, null);
                    firstClick2 = false;
                    firstClickTrue();
                } else {
                    id_event = "R.drawable.roa";
                    forward();
                }
            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstClick3) {
                    int speechStatus = textToSpeech.speak("Geburtstagsfeier", TextToSpeech.QUEUE_FLUSH, null);
                    firstClick3 = false;
                    firstClickTrue();
                } else {
                    id_event = "R.drawable.cake";
                    forward();
                }
            }
        });

        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstClick4) {
                    int speechStatus = textToSpeech.speak("Spiele-Treffen", TextToSpeech.QUEUE_FLUSH, null);
                    firstClickTrue();
                    firstClick4 = false;
                } else {
                    id_event = "R.drawable.chess";
                    forward();
                }
            }
        });
    }

    public void forward() {
        Intent myIntent = new Intent(PlusEventActivity.this, DateTimeActivity.class);
        myIntent.putExtra("event", id_event);
        PlusEventActivity.this.startActivity(myIntent);
        //onDestroy();
    }

    public void firstClickTrue() {
        firstClick1 = true;
        firstClick2 = true;
        firstClick3 = true;
        firstClick4 = true;
        return;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
