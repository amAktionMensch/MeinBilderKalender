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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;



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

        //set current Date
        TextView txtSelectedDate = findViewById(R.id.txtSelectedDate);
        txtSelectedDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        Intent myIntent = new Intent(MainActivity.this, PlusEventActivity.class);
        MainActivity.this.startActivity(myIntent);


    }


}
