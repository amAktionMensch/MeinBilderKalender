package com.example.meinbilderkalender;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    public static String txtPhn = "01631694010";
    public String today;
    public Set<String> tasks;
    public TextToSpeech textToSpeech;

    public boolean firstClickAdd = true;
    public boolean firstClickForward = true;
    public boolean firstClickBack = true;
    public boolean firstClickHelp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialization of the screenreader
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

        //Configuration of HelpButton
        ImageButton btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstClickHelp) {
                    int speechStatus = textToSpeech.speak("Betreuer anrufen.", TextToSpeech.QUEUE_FLUSH, null);
                    firstClickTrue();
                    firstClickHelp = false;
                } else {
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + txtPhn));
                        startActivity(callIntent);
                    } catch (ActivityNotFoundException activityException) {
                        Log.e("Calling a Phone Number", "Call failed", activityException);
                    }
                }
            }
        });

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");


        //Configuration of DateForwardButton
        ImageButton btnDateForward = findViewById(R.id.btnDateForward);
        btnDateForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstClickForward) {
                    int speechStatus = textToSpeech.speak("Ein Tag vorwärts.", TextToSpeech.QUEUE_FLUSH, null);
                    firstClickTrue();
                    firstClickForward = false;
                } else {
                    try {
                        switchDate(1);
                    } catch (ActivityNotFoundException activityException) {
                        Log.e("Calling a Phone Number", "Call failed", activityException);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        displayContent();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        //Configuration of DateBackwardButton
        ImageButton btnDateBackward = findViewById(R.id.btnDateBackward);
        btnDateBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstClickBack) {
                    int speechStatus = textToSpeech.speak("Ein Tag zurück.", TextToSpeech.QUEUE_FLUSH, null);
                    firstClickTrue();
                    firstClickBack = false;
                } else {
                    try {
                        switchDate(-1);
                    } catch (ActivityNotFoundException activityException) {
                        Log.e("Calling a Phone Number", "Call failed", activityException);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        displayContent();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        today = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        //set current Date
        final TextView txtSelectedDate = findViewById(R.id.txtSelectedDate);
        txtSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int speechStatus = textToSpeech.speak(txtSelectedDate.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                getSchedule();
            }
        });

        if (date != null) {
            txtSelectedDate.setText(date);
        } else {
            txtSelectedDate.setText(new SimpleDateFormat(getString(R.string.dateFormat)).format(new Date()));
        }

        //set Plus Button
        ImageButton btnPlus = findViewById(R.id.btnPlus);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstClickAdd) {
                    int speechStatus = textToSpeech.speak("Neuen Termin hinzufügen.", TextToSpeech.QUEUE_FLUSH, null);
                    firstClickTrue();
                    firstClickAdd = false;
                } else {
                    Intent myIntent = new Intent(MainActivity.this, PlusEventActivity.class);
                    MainActivity.this.startActivity(myIntent);
                }
            }
        });


        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //SharedPreferences.Editor editor = sharedPref.edit();
        //editor.clear();
        //editor.commit();
        //load the content
        try {
            displayContent();
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

    private void displayContent() throws ParseException {

        TextView dateText = findViewById(R.id.txtSelectedDate);

        LinearLayout layout = findViewById(R.id.lytContentMorning);

        //clear all Layouts for current date
        layout.removeAllViews();
        layout = findViewById(R.id.lytContentNoon);
        layout.removeAllViews();
        layout = findViewById(R.id.lytContentEvening);
        layout.removeAllViews();

        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        Set<String> events = sharedPref.getStringSet(dateText.getText().toString(), null);

        System.out.println("Events: "+events);
        //new idea
        if(events != null) {
            String[] eventArray = events.toArray(new String[events.size()]);

            for (int i = 0; i < eventArray.length; i++) {
                String task = eventArray[i].substring(0, eventArray[i].indexOf(";"));
                String description = eventArray[i].substring(eventArray[i].indexOf(";")+1, eventArray[i].indexOf("+"));
                String time = eventArray[i].substring(eventArray[i].indexOf("+")+1);
                description = time + " :" + description;
                System.out.println("Task of i=" + i + " : " + task);
                System.out.println("Descrip of i=" + i + " : " + description);
                System.out.println("Time of i=" + i + " : " + time);


                if (time.equals("Morgens"))
                {
                    layout = findViewById(R.id.lytContentMorning);
                }
                else if (time.equals("Mittags"))
                {
                    layout = findViewById(R.id.lytContentNoon);
                }
                else if (time.equals("Abends"))
                {
                    layout = findViewById(R.id.lytContentEvening);
                }

                final ImageView imageView = new ImageView(this);
                imageView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                if(task.equals("1")) {
                    imageView.setBackgroundResource(R.drawable.bowling);
                }
                if(task.equals("2")) {
                    imageView.setBackgroundResource(R.drawable.roa);
                }
                if(task.equals("3")) {
                    imageView.setBackgroundResource(R.drawable.cake);
                }
                if(task.equals("4")) {
                    imageView.setBackgroundResource(R.drawable.chess);
                }
                imageView.setContentDescription(description);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int speechStatus = textToSpeech.speak(imageView.getContentDescription().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                    });
                imageView.setMaxWidth(50);
                imageView.setMaxHeight(50);

                System.out.println("loop:" +i);
                System.out.println("layout:" +layout);
                System.out.println("image:" +imageView);
                if (layout != null)
                    layout.addView(imageView);
            }
        } else {

        }




    }


    public void getSchedule() {
        TextView dateText = findViewById(R.id.txtSelectedDate);

        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        Set<String> events = sharedPref.getStringSet(dateText.getText().toString(), null);

        if(events == null) {
            String text = "Für den " + dateText.getText().toString() + " sind keine Termine vorhanden.";
            int speechStatus = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            return;
        }
/*        ImageView iv1 = findViewById(R.id.imgMorning);
        ImageView iv2 = findViewById(R.id.imgNoon);
        ImageView iv3 = findViewById(R.id.imgEvening);

        String text1 = iv1.getContentDescription().toString();
        String text2 = iv2.getContentDescription().toString();
        String text3 = iv3.getContentDescription().toString();

        if(text1.length() > 2) {
            int speechStatus = textToSpeech.speak(text1, TextToSpeech.QUEUE_FLUSH, null);
        }
        if(text2.length() > 2) {
            int speechStatus = textToSpeech.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
        }
        if(text3.length() > 2) {
            int speechStatus = textToSpeech.speak(text3, TextToSpeech.QUEUE_FLUSH, null);
        }
*/
    }

    private void firstClickTrue() {
        firstClickAdd = true;
        firstClickBack = true;
        firstClickForward = true;
        firstClickHelp = true;
        return;
    }
}
