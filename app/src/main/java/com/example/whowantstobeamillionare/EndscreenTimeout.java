package com.example.whowantstobeamillionare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EndscreenTimeout extends Activity {
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.timeout);

        Button button1 = (Button) findViewById(R.id.buttonBackEndscreen);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityStart = new Intent(EndscreenTimeout.this, MainActivity.class);
                startActivityForResult(activityStart, 1);

            }
        });

    }
}

