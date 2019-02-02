package com.example.meinbilderkalender;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class DateTimeActivity extends AppCompatActivity {

    public String task;
    public String description;
    public String time = "Morgens";
    public String today ="";

    public TextView tv1;
    public TextToSpeech textToSpeech;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetime);

        Intent intent = getIntent();
        task = intent.getStringExtra("event");

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

        ImageView iv1 = findViewById(R.id.btnMorning);
        ImageView iv2 = findViewById(R.id.btnNoon);
        ImageView iv3 = findViewById(R.id.btnEvening);
        ImageButton btnSave = findViewById(R.id.btnPlus);
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(saveData(time)) {
                    Intent intent = new Intent(DateTimeActivity.this, MainActivity.class);
                    intent.putExtra("date", today);
                    DateTimeActivity.this.startActivity(intent);
                }
            }
        });

       /* ImageView iv1 = findViewById(R.id.);
        ImageView iv2 = findViewById(R.id.);
        ImageView iv3 = findViewById(R.id.);*/

        tv1 = findViewById(R.id.txtSelectedDate);

       iv1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                time = "Morgens";
                saveData(time);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                time = "Mittags";
                saveData(time);
            }
        });

        iv3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                time = "Abends";
                saveData(time);
            }
        });


    }


    private boolean saveData(String time) {
        int speechStatus = textToSpeech.speak(time, TextToSpeech.QUEUE_FLUSH, null);
        getDate();
        getDescription(time);
        DateFormat format = new SimpleDateFormat("hh:mm", Locale.GERMAN);
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Set<String> set = sharedPref.getStringSet(today, null);
        if(set == null) {
            set = new HashSet<>();
        }
        set.add(task);
        set.add(description);
        set.add(time);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(today, set);
        editor.commit();
        return true;
    }

    private boolean getDescription(String task) {
        if (task.equals("R.drawable.bowling")) {
            description = "Bowling spielen gehen am ";
        }
        if (task.equals("R.drawable.chess")) {
            description = "Schach spielen am ";
        }
        if (task.equals("R.drawable.cake")) {
            description = "Geburtstagsfeier am ";
        }
        if (task.equals("R.drawable.roa")) {
            description = "Rikscha fahren am ";
        }

        description = description + " " + today + " " + time;
        return true;
    }

    private boolean getDate() {
        today = tv1.getText().toString();
        return true;
    }


}
