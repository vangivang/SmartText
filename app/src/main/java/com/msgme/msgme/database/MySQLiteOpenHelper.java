package com.msgme.msgme.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by alonm on 8/12/14.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_TRIGGER_WORDS = "trigger_words_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TRIGGER_WORD = "trigger_word";
    public static final String COLUMN_TRIGGER_WORD_IMAGE_URL = "trigger_word_image_url";

    private static final String DATABASE_NAME = "trigger_words.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_TRIGGER_WORDS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TRIGGER_WORD + " text not null, "
            + COLUMN_TRIGGER_WORD_IMAGE_URL + "text not null);";

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIGGER_WORDS);
        onCreate(db);
    }
}
