package com.example.meinbilderkalender;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    public static String txtPhn = "01631694010";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configuration of HelpButton
        Button btnHelp = findViewById(R.id.btnHelp);
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

        //set current Date
        TextView txtSelectedDate = findViewById(R.id.txtSelectedDate);
        txtSelectedDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        //set Plus Button
        Button btnPlus = findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, PlusEventActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

    }

    private void switchDate(Integer offset) throws ParseException {

        System.out.println("Button pressed " + offset);


        TextView dateText = findViewById(R.id.txtSelectedDate);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMAN);
        Date oldDate = format.parse(dateText.getText().toString());
        //Date newDate = new DateTime(date).minusDays(300).toDate();.
        Calendar cal = Calendar.getInstance();
        cal.setTime(oldDate);
        cal.add(Calendar.DATE, offset);
        dateText.setText(format.format(cal.getTime()));
    }


}
