package com.example.whowantstobeamillionare;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Options options = new Options();
    MediaPlayer mainTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        Button button1 = findViewById(R.id.buttonStart);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityStart = new Intent(MainActivity.this,WhoWantsToBeAMillionare.class);
                startActivityForResult(activityStart,1);
               mainTheme.reset();
                mainTheme.release();
                finish();
            }
        });



      Button button3 = findViewById(R.id.buttonOptions);
      button3.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent activityOptions = new Intent(MainActivity.this, Options.class);
              startActivityForResult(activityOptions,1);
              mainTheme.reset();
              mainTheme.release();
              finish();
          }
      });

      Button button4 = findViewById(R.id.buttonExitGame);
      button4.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mainTheme.reset();mainTheme.release();
              finish();
          }
      });

        mainTheme = MediaPlayer.create(this, R.raw.maintheme);
        if (options.getBooleanValue()) {
            mainTheme.start();
        } else {
            mainTheme.reset();
            mainTheme.release();
        }

        mainTheme.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mainTheme.start();
            }
        });
    }

    public void onPause(){
        super.onPause();
        mainTheme.reset();
        mainTheme.release();
    }
    public void onResume(){
        super.onResume();
    }



/*
    private void resetTitle() {
        try {
            int label = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA).labelRes;
            if (label != 0) {
                setTitle(label);
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.setLocale(base));
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }

 */
}
