package com.example.whowantstobeamillionare;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LocaleManager {
/*
    public static void setLocale(Activity activity) {
        SharedPreferences prefs =activity.getSharedPreferences("Settings",Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","en");
        Locale locale =new Locale (language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale =locale;
        activity.getBaseContext().getResources().updateConfiguration(config,activity.getBaseContext().getResources().getDisplayMetrics());
    }



    public static void setNewLocale(Context c, String language) {
        persistLanguage(c, language);
        updateResources(c, language);
    }

    public String getLanguage(Context c) {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","en");
        setLocale(language);
    }

    private static void persistLanguage(Context c, String language) { ... }



        private static Context updateResources(Context context, String language) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Resources res = context.getResources();
            Configuration config = new Configuration(res.getConfiguration());
            if (Build.VERSION.SDK_INT >= 17) {
                config.setLocale(locale);
                context = context.createConfigurationContext(config);
            } else {
                config.locale = locale;
                res.updateConfiguration(config, res.getDisplayMetrics());
            }
            return context;
        }


 */
}