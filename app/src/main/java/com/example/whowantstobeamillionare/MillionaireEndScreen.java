package com.example.whowantstobeamillionare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MillionaireEndScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.millionare);
        Button button1 = findViewById(R.id.buttonendscreen);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityStart = new Intent(MillionaireEndScreen.this, MainActivity.class);
                startActivity(activityStart);
                finish();

            }
        });
    }
}
