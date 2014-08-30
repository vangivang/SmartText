package com.msgme.msgme.BaseClasses;

import android.app.Activity;
import android.os.Bundle;

import com.msgme.msgme.MainApplication;

/**
 * Created by alonm on 8/30/14.
 */
public class BaseActivity extends Activity {

    protected MainApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (MainApplication) getApplication();
    }
}
