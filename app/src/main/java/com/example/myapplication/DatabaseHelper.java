package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Connection;

import EasyKnowLib.Day;
import EasyKnowLib.Week;

public class DatabaseHelper extends SQLiteOpenHelper {
    // DB name
    public static final String DATABASE_NAME = "words.db";
    // Table names
    public static final String TABLE_WORDS = "words_table";
    public static final String TABLE_FOLDERS = "folders_table";
    public static final String TABLE_NOTIFICATION_SETTINGS = "notification_settings_table";
    public static final String TABLE_NOTIFICATION_STATUS = "notification_status_table";

    //WORDS_TABLE
    public static final String WORD_COL_1 = "ID";
    public static final String WORD_COL_2 = "WORD";
    public static final String WORD_COL_3 = "MEANING";
    public static final String WORD_COL_4 = "LEARN_STATUS";
    public static final String WORD_COL_5 = "FOLDER_ID";
    public static final String WORD_COL_6 = "LAST_NOTIFICATION_TIME";
    //FOLDERS_TABLE
    public static final String FOLDER_COL_1 = "ID";
    public static final String FOLDER_COL_2 = "FOLDER";
    //NOTIFICATION_STATUS_TABLE
    public static final String NOTIFICATION_SETTINGS_COL1 = "NOTIFICATION_ID";
    public static final String NOTIFICATION_SETTINGS_COL2 = "NOTIFICATION_NUMBER";
    public static final String NOTIFICATION_SETTINGS_COL3 = "MONDAY";
    public static final String NOTIFICATION_SETTINGS_COL4 = "TUESDAY";
    public static final String NOTIFICATION_SETTINGS_COL5 = "WEDNESDAY";
    public static final String NOTIFICATION_SETTINGS_COL6 = "THURSDAY";
    public static final String NOTIFICATION_SETTINGS_COL7 = "FRIDAY";
    public static final String NOTIFICATION_SETTINGS_COL8 = "SATURDAY";
    public static final String NOTIFICATION_SETTINGS_COL9 = "SUNDAY";
    //NOTIFICATION_STATUS_TABLE
    public static final String NOTIFICATION_STATUS_COL1 = "NOTIFICATION_STATUS_ID";
    public static final String NOTIFICATION_STATUS_COL2 = "NOTIFICATION_DATE";
    public static final String NOTIFICATION_STATUS_COL3 = "NUMBER_OF_NOTIFICATIONS_SENT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table FOLDERS
        db.execSQL("create table " + TABLE_FOLDERS + "(" + FOLDER_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FOLDER_COL_2 + " TEXT UNIQUE)");
        // Create table Words
        db.execSQL("create table " + TABLE_WORDS + "(" + WORD_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_COL_2 + " TEXT, " +
                WORD_COL_3 + " TEXT, " +
                WORD_COL_4 + " INTEGER, " +
                WORD_COL_5 + " INTEGER, " +
                WORD_COL_6 + " TEXT)");
        // Create table NOTIFICATION_SETTINGS
        // Boolean values are stored as integers 0 (false) and 1 (true)
        db.execSQL("create table " + TABLE_NOTIFICATION_SETTINGS + "(" + NOTIFICATION_SETTINGS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTIFICATION_SETTINGS_COL2 + " INTEGER, " +
                NOTIFICATION_SETTINGS_COL3 + " INTEGER DEFAULT 0 NOT NULL CHECK (" + NOTIFICATION_SETTINGS_COL3 + " IN (0, 1)), " +
                NOTIFICATION_SETTINGS_COL4 + " INTEGER DEFAULT 0 NOT NULL CHECK (" + NOTIFICATION_SETTINGS_COL4 + " IN (0, 1)), " +
                NOTIFICATION_SETTINGS_COL5 + " INTEGER DEFAULT 0 NOT NULL CHECK (" + NOTIFICATION_SETTINGS_COL5 + " IN (0, 1)), " +
                NOTIFICATION_SETTINGS_COL6 + " INTEGER DEFAULT 0 NOT NULL CHECK (" + NOTIFICATION_SETTINGS_COL6 + " IN (0, 1)), " +
                NOTIFICATION_SETTINGS_COL7 + " INTEGER DEFAULT 0 NOT NULL CHECK (" + NOTIFICATION_SETTINGS_COL7 + " IN (0, 1)), " +
                NOTIFICATION_SETTINGS_COL8 + " INTEGER DEFAULT 0 NOT NULL CHECK (" + NOTIFICATION_SETTINGS_COL8 + " IN (0, 1)), " +
                NOTIFICATION_SETTINGS_COL9 + " INTEGER DEFAULT 0 NOT NULL CHECK (" + NOTIFICATION_SETTINGS_COL9 + " IN (0, 1)))");
        // Create Table NOTIFICATION_STATUS
        db.execSQL("create table " + TABLE_NOTIFICATION_STATUS + "(" + NOTIFICATION_STATUS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTIFICATION_SETTINGS_COL2 + " TEXT UNIQUE, " +
                NOTIFICATION_STATUS_COL3 + " INTEGER DEFAULT 0 NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION_STATUS);
        onCreate(db);
    }

    public boolean insertNewFolder(String folder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FOLDER_COL_2, folder);
        long result = db.insert(TABLE_FOLDERS, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertNewNotificationSettings(int notificationNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTIFICATION_SETTINGS_COL2, notificationNumber);
        long result = db.insert(TABLE_NOTIFICATION_SETTINGS, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertNewWord(String word, String meaning, int learn_status, String folder_id, String last_notification_time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORD_COL_2, word);
        contentValues.put(WORD_COL_3, meaning);
        contentValues.put(WORD_COL_4, learn_status);
        contentValues.put(WORD_COL_5, folder_id);
        contentValues.put(WORD_COL_6, last_notification_time);
        long result = db.insert(TABLE_WORDS, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllWords() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_WORDS,null);
        return res;
    }

    public Cursor getAllFolders() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_FOLDERS,null);
        return res;
    }

    public Cursor getFolderId(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+FOLDER_COL_1+" from "+TABLE_FOLDERS +" where "+FOLDER_COL_2 + " = ?",new String[]{name});
        return res;
    }

    public Cursor getNotificationSettings() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NOTIFICATION_SETTINGS,null);
        return res;
    }

    public boolean updateNotificationSettings(int notificationNumber, int monday, int tuesday, int wednesday,
                                            int thursday, int friday, int saturday, int sunday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues res = new ContentValues();
        String id = "1";
        res.put(NOTIFICATION_SETTINGS_COL2,notificationNumber); //These Fields should be your String values of actual column names
        res.put(NOTIFICATION_SETTINGS_COL3,monday);
        res.put(NOTIFICATION_SETTINGS_COL4,tuesday);
        res.put(NOTIFICATION_SETTINGS_COL5,wednesday);
        res.put(NOTIFICATION_SETTINGS_COL6,thursday);
        res.put(NOTIFICATION_SETTINGS_COL7,friday);
        res.put(NOTIFICATION_SETTINGS_COL8,saturday);
        res.put(NOTIFICATION_SETTINGS_COL9,sunday);
        long result = db.update(TABLE_NOTIFICATION_SETTINGS, res, NOTIFICATION_SETTINGS_COL1 + " = " + id, null);
        if(result == -1)
            return false;
        else
            return true;
    }

/*    public Cursor getWord(int folderId, int learnStatus, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_WORDS + "where FOLDER_ID = " + folderId + "and LEARN_STATUS = " + learnStatus +
                "and LAST_NOTIFICATION_TIME <" + time ,null);
        return res;
    }*/
}
