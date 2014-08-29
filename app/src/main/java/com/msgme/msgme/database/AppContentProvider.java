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

import java.util.ArrayList;

/**
 * Created by alonm on 8/22/14.
 */
public class AppContentProvider extends ContentProvider {

    // database creation stuff
    private SQLiteDatabase database;
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "trigger_words.db";

    // DB tables
    public static final String TABLE_TRIGGER_WORDS_ENGLISH = "trigger_words_english";
    public static final String TABLE_TRIGGER_WORDS_PORTUGUESE = "trigger_words_portuguese";
    public static final String TABLE_TRIGGER_WORDS_SPANISH = "trigger_words_spanish";
    public static final String TABLE_TRIGGER_WORDS_ARABIC = "trigger_words_arabic";
    public static final String TABLE_TRIGGER_WORDS_CHINESE = "trigger_words_chinese";
    public static final String ALL_TABLES = "all_tables";

    // DB columns
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TRIGGER_WORD = "trigger_word";
    public static final String COLUMN_TRIGGER_WORD_IMAGE_URL = "trigger_word_image_url";
    public static final String COLUMN_COUPON_TEXT = "coupon_text";

    // DB creation strings
    private static final String CREATE_TABLE_ENGLISH = "create table "
            + TABLE_TRIGGER_WORDS_ENGLISH
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TRIGGER_WORD + " text not null, "
            + COLUMN_TRIGGER_WORD_IMAGE_URL + " text not null, "
            + COLUMN_COUPON_TEXT + " text not null"
            + ");";
    private static final String CREATE_TABLE_PORTUGUESE = "create table "
            + TABLE_TRIGGER_WORDS_PORTUGUESE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TRIGGER_WORD + " text not null, "
            + COLUMN_TRIGGER_WORD_IMAGE_URL + " text not null, "
            + COLUMN_COUPON_TEXT + " text not null"
            + ");";
    private static final String CREATE_TABLE_SPANISH = "create table "
            + TABLE_TRIGGER_WORDS_SPANISH
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TRIGGER_WORD + " text not null, "
            + COLUMN_TRIGGER_WORD_IMAGE_URL + " text not null, "
            + COLUMN_COUPON_TEXT + " text not null"
            + ");";
    private static final String CREATE_TABLE_ARABIC = "create table "
            + TABLE_TRIGGER_WORDS_ARABIC
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TRIGGER_WORD + " text not null, "
            + COLUMN_TRIGGER_WORD_IMAGE_URL + " text not null, "
            + COLUMN_COUPON_TEXT + " text not null"
            + ");";
    private static final String CREATE_TABLE_CHINESE = "create table "
            + TABLE_TRIGGER_WORDS_CHINESE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TRIGGER_WORD + " text not null, "
            + COLUMN_TRIGGER_WORD_IMAGE_URL + " text not null, "
            + COLUMN_COUPON_TEXT + " text not null"
            + ");";

    // Fields for my content provider
    private static final String AUTHORITY = "com.msgme.msgme.database.AppContentProvider";
    private static final String URL = "content://" + AUTHORITY + "/%s";

    // Content Uris for content provider calls
    public static final Uri CONTENT_URI_ENGLISH = formUri(TABLE_TRIGGER_WORDS_ENGLISH);
    public static final Uri CONTENT_URI_PORTUGUESE = formUri(TABLE_TRIGGER_WORDS_PORTUGUESE);
    public static final Uri CONTENT_URI_SPANISH = formUri(TABLE_TRIGGER_WORDS_SPANISH);
    public static final Uri CONTENT_URI_ARABIC = formUri(TABLE_TRIGGER_WORDS_ARABIC);
    public static final Uri CONTENT_URI_CHINESE = formUri(TABLE_TRIGGER_WORDS_CHINESE);
    public static final Uri CONTENT_URI_ALL_TABLES = formUri(ALL_TABLES);

    // Integer values used in content URI and matcher
    private static final int TRIGGER_WORDS = 1;
    private static final int TRIGGER_WORD_ID = 2;
    private static final int MATCHER_TRIGGER_WORDS_TABLE_ENGLISH = 3;
    private static final int MATCHER_TRIGGER_WORDS_TABLE_PORTUGUESE = 4;
    private static final int MATCHER_TRIGGER_WORDS_TABLE_SPANISH = 5;
    private static final int MATCHER_TRIGGER_WORDS_TABLE_ARABIC = 6;
    private static final int MATCHER_TRIGGER_WORDS_TABLE_CHINESE = 7;
    private static final int MATCHER_TRIGGER_WORDS_ALL_TABLES = 8;

    // Map content URI "patterns" to the integer values that were set above
    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, CONTENT_URI_ENGLISH.getPath().substring(1),
                MATCHER_TRIGGER_WORDS_TABLE_ENGLISH);
        uriMatcher.addURI(AUTHORITY, CONTENT_URI_PORTUGUESE.getPath().substring(1),
                MATCHER_TRIGGER_WORDS_TABLE_PORTUGUESE);
        uriMatcher.addURI(AUTHORITY, CONTENT_URI_SPANISH.getPath().substring(1),
                MATCHER_TRIGGER_WORDS_TABLE_SPANISH);
        uriMatcher.addURI(AUTHORITY, CONTENT_URI_ARABIC.getPath().substring(1), MATCHER_TRIGGER_WORDS_TABLE_ARABIC);
        uriMatcher.addURI(AUTHORITY, CONTENT_URI_CHINESE.getPath().substring(1),
                MATCHER_TRIGGER_WORDS_TABLE_CHINESE);
        uriMatcher.addURI(AUTHORITY, CONTENT_URI_ALL_TABLES.getPath().substring(1), MATCHER_TRIGGER_WORDS_ALL_TABLES);
    }

    public static ArrayList<Uri> mContracts;

    @Override
    public boolean onCreate() {

        DBHelper dbHelper = new DBHelper(getContext());
        database = dbHelper.getWritableDatabase();

        mContracts = new ArrayList<Uri>();
        mContracts.add(CONTENT_URI_ENGLISH);
        mContracts.add(CONTENT_URI_PORTUGUESE);
        mContracts.add(CONTENT_URI_SPANISH);
        mContracts.add(CONTENT_URI_ARABIC);
        mContracts.add(CONTENT_URI_CHINESE);

        return database != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // the TABLE_NAME to query on
        queryBuilder.setTables(TABLE_TRIGGER_WORDS_ENGLISH);
        if (TextUtils.isEmpty(sortOrder)) {
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
        switch (uriMatcher.match(uri)) {
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
        int uriType = uriMatcher.match(uri);
        long row = 0;
        Uri newUri = null;

        switch (uriType) {
            case MATCHER_TRIGGER_WORDS_TABLE_ENGLISH:
                row = database.insertWithOnConflict(TABLE_TRIGGER_WORDS_ENGLISH, null, values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                newUri = ContentUris.withAppendedId(CONTENT_URI_ENGLISH, row);
                break;
            case MATCHER_TRIGGER_WORDS_TABLE_PORTUGUESE:
                row = database.insertWithOnConflict(TABLE_TRIGGER_WORDS_PORTUGUESE, null, values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                newUri = ContentUris.withAppendedId(CONTENT_URI_PORTUGUESE, row);
                break;
            case MATCHER_TRIGGER_WORDS_TABLE_SPANISH:
                row = database.insertWithOnConflict(TABLE_TRIGGER_WORDS_SPANISH, null, values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                newUri = ContentUris.withAppendedId(CONTENT_URI_SPANISH, row);
                break;
            case MATCHER_TRIGGER_WORDS_TABLE_ARABIC:
                row = database.insertWithOnConflict(TABLE_TRIGGER_WORDS_ARABIC, null, values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                newUri = ContentUris.withAppendedId(CONTENT_URI_ARABIC, row);
                break;
            case MATCHER_TRIGGER_WORDS_TABLE_CHINESE:
                row = database.insertWithOnConflict(TABLE_TRIGGER_WORDS_CHINESE, null, values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                newUri = ContentUris.withAppendedId(CONTENT_URI_CHINESE, row);
                break;
        }

        // If record is added successfully
        if (row > 0) {
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int uriType = uriMatcher.match(uri);
        int rowsDeleted = 0;

        switch (uriType) {
            case MATCHER_TRIGGER_WORDS_TABLE_ENGLISH:
                rowsDeleted = database.delete(TABLE_TRIGGER_WORDS_ENGLISH, selection, selectionArgs);
                break;
            case MATCHER_TRIGGER_WORDS_TABLE_PORTUGUESE:
                rowsDeleted = database.delete(TABLE_TRIGGER_WORDS_PORTUGUESE, selection, selectionArgs);
                break;
            case MATCHER_TRIGGER_WORDS_TABLE_SPANISH:
                rowsDeleted = database.delete(TABLE_TRIGGER_WORDS_SPANISH, selection, selectionArgs);
                break;
            case MATCHER_TRIGGER_WORDS_TABLE_ARABIC:
                rowsDeleted = database.delete(TABLE_TRIGGER_WORDS_ARABIC, selection, selectionArgs);
                break;
            case MATCHER_TRIGGER_WORDS_TABLE_CHINESE:
                rowsDeleted = database.delete(TABLE_TRIGGER_WORDS_CHINESE, selection, selectionArgs);
                break;
            case MATCHER_TRIGGER_WORDS_ALL_TABLES:

                database.beginTransaction();

                try {
                    for (Uri tableUri : mContracts) {
                        rowsDeleted += delete(tableUri, selection, selectionArgs);
                    }
                    database.setTransactionSuccessful();
                } catch (Exception e) {

                } finally {
                    database.endTransaction();
                }

                break;
        }
        return rowsDeleted;
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
            db.execSQL(CREATE_TABLE_ENGLISH);
            db.execSQL(CREATE_TABLE_PORTUGUESE);
            db.execSQL(CREATE_TABLE_SPANISH);
            db.execSQL(CREATE_TABLE_ARABIC);
            db.execSQL(CREATE_TABLE_CHINESE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DBHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ". Old data will be destroyed");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIGGER_WORDS_ENGLISH);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIGGER_WORDS_PORTUGUESE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIGGER_WORDS_SPANISH);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIGGER_WORDS_ARABIC);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIGGER_WORDS_CHINESE);
            onCreate(db);
        }
    }

    protected static Uri formUri(String tableName) {
        // Parse the CONTENT_URI for this entity
        return Uri.parse(String.format(URL, tableName));
    }
}
