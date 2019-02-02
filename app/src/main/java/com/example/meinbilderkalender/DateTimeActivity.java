package com.example.meinbilderkalender;

import android.content.ActivityNotFoundException;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        String date = intent.getStringExtra("date");
        //Configuration of DateForwardButton
        ImageButton btnDateForward = findViewById(R.id.btnDateForward);
        btnDateForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    switchDate(1);
                } catch (ActivityNotFoundException activityException) {
                    Log.e("Calling a Phone Number", "Call failed", activityException);
                } catch (ParseException e){
                    e.printStackTrace();
                }
            }
        });

        //Configuration of DateBackwardButton
        ImageButton btnDateBackward = findViewById(R.id.btnDateBackward);
        btnDateBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    switchDate(-1);
                } catch (ActivityNotFoundException activityException) {
                    Log.e("Calling a Phone Number", "Call failed", activityException);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        today = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        //set current Date
        TextView txtSelectedDate = findViewById(R.id.txtSelectedDate);

        if(date != null) {
            txtSelectedDate.setText(date);
        } else {
            txtSelectedDate.setText(new SimpleDateFormat (getString(R.string.dateFormat)).format(new Date()));
        }

        tv1 = findViewById(R.id.txtSelectedDate);

       iv1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                time = "Morgens";
                int speechStatus = textToSpeech.speak(time, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                time = "Mittags";
                int speechStatus = textToSpeech.speak(time, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        iv3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                time = "Abends";
                int speechStatus = textToSpeech.speak(time, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


    }


    private boolean saveData(String time) {
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

        int speechStatus = textToSpeech.speak(description + " als Termin gespeichert!", TextToSpeech.QUEUE_FLUSH, null);
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
    private void switchDate(Integer offset) throws ParseException {

        TextView dateText = findViewById(R.id.txtSelectedDate);
        DateFormat format = new SimpleDateFormat(getString(R.string.dateFormat), Locale.GERMAN);
        Date oldDate = format.parse(dateText.getText().toString());


        Calendar cal = Calendar.getInstance();
        cal.setTime(oldDate);
        cal.add(Calendar.DATE, offset);
        dateText.setText(format.format(cal.getTime()));

        int speechStatus = textToSpeech.speak(dateText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

}
