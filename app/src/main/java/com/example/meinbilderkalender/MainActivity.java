package com.example.meinbilderkalender;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    public static String txtPhn = "01631694010";
    public String today;
    public Set<String> tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configuration of HelpButton
        ImageButton btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+txtPhn));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {
                    Log.e("Calling a Phone Number", "Call failed", activityException);
                }
            }
        });

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
        txtSelectedDate.setText(new SimpleDateFormat (getString(R.string.dateFormat)).format(new Date()));

        //set Plus Button
        ImageButton btnPlus = findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, PlusEventActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });


        //load the content
        try {
            displayContent();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private boolean saveData(int task, String description, Date time) {
        DateFormat format = new SimpleDateFormat("hh:mm", Locale.GERMAN);
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Set<String> set = sharedPref.getStringSet(today, null);
        /*if(set == null) {
            set = new Set<String>() {
                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean contains(@androidx.annotation.Nullable Object o) {
                    return false;
                }

                @androidx.annotation.NonNull
                @Override
                public Iterator<String> iterator() {
                    return null;
                }

                @androidx.annotation.Nullable
                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @Override
                public <T> T[] toArray(@androidx.annotation.Nullable T[] a) {
                    return null;
                }

                @Override
                public boolean add(String s) {
                    return false;
                }

                @Override
                public boolean remove(@androidx.annotation.Nullable Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(@androidx.annotation.NonNull Collection<?> c) {
                    return false;
                }

                @Override
                public boolean addAll(@androidx.annotation.NonNull Collection<? extends String> c) {
                    return false;
                }

                @Override
                public boolean retainAll(@androidx.annotation.NonNull Collection<?> c) {
                    return false;
                }

                @Override
                public boolean removeAll(@androidx.annotation.NonNull Collection<?> c) {
                    return false;
                }

                @Override
                public void clear() {

                }
            }

        }*/
        set.add(Integer.toString(task));
        set.add(time.toString());
        set.add(description);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(today, set);
        editor.commit();
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
    }

    private void displayContent() throws ParseException {

        TextView dateText = findViewById(R.id.txtSelectedDate);
        DateFormat format = new SimpleDateFormat(String.valueOf(R.string.dateFormat), Locale.GERMAN);
//        Date selectedDate = format.parse(dateText.getText().toString());

        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        Set events = sharedPref.getStringSet(getString(R.string.dateFormat), null);

//        if (events != null) {
        LinearLayout layout = findViewById(R.id.lytContent);
        layout.removeAllViews();
//        for (int i = 1; i <= events.size(); i = i + 3) {
            for (int i = 1; i <= 1; i = i + 3) {
                //ImageView Setup
                ImageView imageView = new ImageView(this);
                imageView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                imageView.setOnClickListener(readDescription());


                imageView.setContentDescription("Fahrradfahren");
                imageView.setBackgroundResource(R.drawable.bike);

                layout.addView(imageView);
            }
//        }


    }

    private View.OnClickListener readDescription(){
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            };
    };


}
