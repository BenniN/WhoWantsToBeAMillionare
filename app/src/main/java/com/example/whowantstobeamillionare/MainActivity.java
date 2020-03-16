package com.example.whowantstobeamillionare;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mainTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        Button startGame = findViewById(R.id.buttonStart);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityStart = new Intent(MainActivity.this, WhoWantsToBeAMillionaire.class);
                startActivityForResult(activityStart, 1);
                mainTheme.pause();
                finish();
            }
        });
        Button gotoSettings = findViewById(R.id.buttonOptions);
        gotoSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityOptions = new Intent(MainActivity.this, Options.class);
                startActivityForResult(activityOptions, 1);
                mainTheme.pause();
                finish();
            }
        });
        Button exitGame = findViewById(R.id.buttonExitGame);
        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainTheme.pause();
                finish();
            }
        });
        mainTheme = MediaPlayer.create(this, R.raw.maintheme);
        if (Options.isMusicOn()) {
            mainTheme.start();
        } else {
            mainTheme.pause();
        }
        mainTheme.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mainTheme.start();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mainTheme.pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        mainTheme.start();
    }
}
