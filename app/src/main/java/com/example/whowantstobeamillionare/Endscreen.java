package com.example.whowantstobeamillionare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Endscreen extends Activity {
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.endscreen);

        Button button1 = findViewById(R.id.buttonBackEndscreen);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityStart = new Intent(Endscreen.this, MainActivity.class);
                startActivityForResult(activityStart, 1);
                finish();
            }
        });

    }
}

