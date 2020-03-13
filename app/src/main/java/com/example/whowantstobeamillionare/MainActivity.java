package com.example.whowantstobeamillionare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        Button button1 = (Button) findViewById(R.id.buttonStart);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityStart = new Intent(MainActivity.this,WhoWantsToBeAMillionare.class);
                startActivityForResult(activityStart,1);
            }
        });

      Button button2 = (Button) findViewById(R.id.buttonLoad);
      button2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

                      Intent activityStart = new Intent(MainActivity.this,MillionareEndsceen.class);
                      startActivityForResult(activityStart,1);


              Toast.makeText(getBaseContext(),"Need to load last Game!",Toast.LENGTH_LONG).show();
          }
      });

      Button button3 = (Button) findViewById(R.id.buttonOptions);
      button3.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent activityOptions = new Intent(MainActivity.this, Options.class);
              startActivityForResult(activityOptions,1);
          }
      });

      Button button4 = (Button) findViewById(R.id.buttonExitGame);
      button4.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
      });
    }



}
