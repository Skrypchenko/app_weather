package com.task.test.weatherapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Locale;


public class Settings {

    private Context mContext;

    public Settings(Context context) {
        mContext = context;
        setApiKey("214198389284d1fbd19cfd0fc21b2042");
    }

    private static final String PREF_API_KEY = "pref.api.key";

    public String getApiKey() {
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getString(PREF_API_KEY,"");
    }

    public void setApiKey(String apiKey) {
        PreferenceManager.getDefaultSharedPreferences(mContext)
                .edit()
                .putString(PREF_API_KEY, apiKey)
                .apply();
    }

}

