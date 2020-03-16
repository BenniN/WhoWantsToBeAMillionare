package com.example.whowantstobeamillionare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Options extends Activity {

    private static boolean musicOnOrOff = false;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.setting_screen);

        Button button1 = findViewById(R.id.buttonSettingsBack);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityStart = new Intent(Options.this, MainActivity.class);
                startActivityForResult(activityStart, 1);
                finish();
            }
        });

        Switch switch1 = findViewById(R.id.switchMusic);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                musicOnOrOff = isChecked;
            }
        });

        switch1.setChecked(musicOnOrOff);
    }

    public static boolean isMusicOn() {
        return musicOnOrOff;
    }
}

