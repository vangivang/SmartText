package com.msgme.msgme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by alonm on 8/13/14.
 */
public class MyDataBase {

    // Database fields
    private SQLiteDatabase mDatabase;
    private MySQLiteOpenHelper mDbHelper;

    private String[] mAllColumnsSelection = {MySQLiteOpenHelper.COLUMN_ID, MySQLiteOpenHelper.COLUMN_TRIGGER_WORD,
            MySQLiteOpenHelper.COLUMN_TRIGGER_WORD_IMAGE_URL};

    public MyDataBase(Context context) {
        mDbHelper = new MySQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public void addTriggerWord(String triggerWord, String triggerWordImageUrl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.COLUMN_TRIGGER_WORD, triggerWord);
        contentValues.put(MySQLiteOpenHelper.COLUMN_TRIGGER_WORD_IMAGE_URL, triggerWordImageUrl);

        long insertId = mDatabase.insert(MySQLiteOpenHelper.TABLE_TRIGGER_WORDS, null, contentValues);
        Cursor cursor = mDatabase.query(MySQLiteOpenHelper.TABLE_TRIGGER_WORDS, mAllColumnsSelection,
                MySQLiteOpenHelper.COLUMN_ID + "=?", new String[]{String.valueOf(insertId)}, null, null, null);

        cursor.moveToFirst();
        Log.d("APP", "trigger word:::" + cursor.getString(0) + " trigger word url:::" + cursor.getString(1));
        cursor.close();

    }
}
