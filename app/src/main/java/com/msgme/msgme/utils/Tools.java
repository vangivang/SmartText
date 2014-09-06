package com.msgme.msgme.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alonm on 9/6/14.
 */
public class Tools {


    /**
     * Get current date using device set locale using a Date pattern. Ie:
     * String name = Tools.getDate(new Date().getTime(), "yyyy-MM-dd-hh-mm-ss");
     * @param milliseconds Ms time to convert to human time
     * @param pattern The structure of the presented time using this format: "yyyy-MM-dd-hh-mm-ss"
     * @return The current time in human readable fashion
     */
    public static String getDate(long milliseconds, String pattern) {

        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(new Date(milliseconds));
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null);
    }
}
