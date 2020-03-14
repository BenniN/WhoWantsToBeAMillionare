package com.example.whowantstobeamillionare;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class SettingsManager {

    public static void loadLocale(Activity activity) {

        SharedPreferences prefs =activity.getSharedPreferences("Settings",Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","en");
        Locale locale =new Locale (language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale =locale;
        activity.getBaseContext().getResources().updateConfiguration(config,activity.getBaseContext().getResources().getDisplayMetrics());

    }

}
