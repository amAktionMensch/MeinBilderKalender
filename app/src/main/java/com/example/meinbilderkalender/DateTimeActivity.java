package com.example.meinbilderkalender;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class DateTimeActivity extends AppCompatActivity {

    public String task;
    public String description;
    public String time;
    public String today;

    public TextView tv1;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetime);

        Intent intent = getIntent();
        task = intent.getStringExtra("event");

       /* ImageView iv1 = findViewById(R.id.);
        ImageView iv2 = findViewById(R.id.);
        ImageView iv3 = findViewById(R.id.);*/

        tv1 = findViewById(R.id.txtSelectedDate);

       /* iv1.setOnClickListener(new View.OnClickListener(){
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
        });*/


    }


    private boolean saveData(String time) {

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
