package com.msgme.msgme;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by alonm on 8/30/14.
 */
public class MainApplication extends Application {

    private SharedPreferences mPref;

    @Override
    public void onCreate() {
        super.onCreate();
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public SharedPreferences getPref() {
        return mPref;
    }
}
