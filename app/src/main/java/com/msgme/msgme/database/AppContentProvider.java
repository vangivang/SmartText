package com.msgme.msgme.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;

/**
 * Created by alonm on 8/22/14.
 */
public class AppContentProvider extends ContentProvider {

    // database creation stuff
    private SQLiteDatabase database;
    private static final String DATABASE_NAME = "trigger_words.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_TRIGGER_WORDS = "trigger_words_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TRIGGER_WORD = "trigger_word";
    public static final String COLUMN_TRIGGER_WORD_IMAGE_URL = "trigger_word_image_url";
    private static final String CREATE_TABLE = "create table "
            + TABLE_TRIGGER_WORDS
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TRIGGER_WORD + " text not null unique, "
            + COLUMN_TRIGGER_WORD_IMAGE_URL + " text not null unique"
            + ");";

    // fields for my content provider
    private static final String PROVIDER_NAME = "com.msgme.msgme.database.AppContentProvider";
    private static final String URL = "content://" + PROVIDER_NAME + "/%s";
    public static final Uri CONTENT_URI = formUri(TABLE_TRIGGER_WORDS);
//    public static final Uri CONTENT_URI = Uri.parse(URL);

    // integer values used in content URI
    private static final int TRIGGER_WORDS = 1;
    private static final int TRIGGER_WORD_ID = 2;

    // projection map for a query
    private static HashMap<String, String> WordsMap;

    // maps content URI "patterns" to the integer values that were set above
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, CONTENT_URI.getPath().substring(1), TRIGGER_WORDS);
    }

    @Override
    public boolean onCreate() {

        DBHelper dbHelper = new DBHelper(getContext());
        database = dbHelper.getWritableDatabase();

        return database != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // the TABLE_NAME to query on
        queryBuilder.setTables(TABLE_TRIGGER_WORDS);

//        switch (uriMatcher.match(uri)) {
//
//            // maps all database column names
//            case TRIGGER_WORDS:
//
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown URI " + uri);
//        }

        if (TextUtils.isEmpty(sortOrder)){
            // No sorting-> sort on names by default
            sortOrder = COLUMN_TRIGGER_WORD;
        }

        Cursor cursor = queryBuilder.query(database, projection, selection,
                selectionArgs, null, null, sortOrder);


        /**
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            // Get all friend-birthday records
            case TRIGGER_WORDS:
                return "vnd.android.cursor.dir/vnd.example.trigger_words";
            // Get a particular friend
            case TRIGGER_WORD_ID:
                return "vnd.android.cursor.item/vnd.example.trigger_words";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = database.insertWithOnConflict(TABLE_TRIGGER_WORDS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        // If record is added successfully
        if(row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("This operation is currently unneeded and thus, unavailable");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("This operation is currently unneeded and thus, unavailable");
    }

    // class that creates and manages the provider's database
    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DBHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ". Old data will be destroyed");
            db.execSQL("DROP TABLE IF EXISTS " +  TABLE_TRIGGER_WORDS);
            onCreate(db);
        }
    }

    protected static Uri formUri(String tableName) {
        // Parse the CONTENT_URI for this entity
        return Uri.parse(String.format(URL, tableName));
    }
}
