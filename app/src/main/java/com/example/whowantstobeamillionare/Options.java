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
/*
        Button button = findViewById(R.id.buttonSelectLanguage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });

*/

        Switch switch1 = findViewById(R.id.switchMusic);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                musicOnOrOff = isChecked;
            }
        });

        switch1.setChecked(musicOnOrOff);
    }

    public boolean getBooleanValue() {
        return musicOnOrOff;
    }
/*
    private void showChangeLanguageDialog(){
        final String[] listItems ={"English","Deutsch"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Options.this);
        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("en");
                    recreate();
                } else if (which == 1) {

                    setLocale("de");
                    recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog mDialog =mBuilder.create();
        mDialog.show();

    }
    public void setLocale(String lang){
        Locale locale =new Locale (lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale =locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        //save data to shared preffernces
        SharedPreferences.Editor editor= getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }



    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings",Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }

 */
}

