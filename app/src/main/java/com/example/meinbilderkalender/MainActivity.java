package com.example.meinbilderkalender;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configuration of HelpButton
        Button btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "42", Toast.LENGTH_SHORT).show();
            }
        });

        //set current Date
        TextView txtSelectedDate = findViewById(R.id.txtSelectedDate);
        txtSelectedDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }


}
