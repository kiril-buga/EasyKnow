package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "words.db";
    public static final String TABLE_WORDS = "words_table";
    public static final String TABLE_FOLDERS = "folders_table";
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
    public static final String NOTIFICATION_STATUS_COL1 = "NOTIFICATION_NUMBER";
    public static final String NOTIFICATION_STATUS_COL2 = "NUMBER_OF_NOTIFICATIONS_SENT";
    public static final String NOTIFICATION_STATUS_COL3 = "MONDAY";
    public static final String NOTIFICATION_STATUS_COL4 = "TUESDAY";
    public static final String NOTIFICATION_STATUS_COL5 = "WEDNESDAY";
    public static final String NOTIFICATION_STATUS_COL6 = "THURSDAY";
    public static final String NOTIFICATION_STATUS_COL7 = "FRIDAY";
    public static final String NOTIFICATION_STATUS_COL8 = "SATURDAY";
    public static final String NOTIFICATION_STATUS_COL9 = "SUNDAY";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_FOLDERS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FOLDER TEXT)");
        db.execSQL("create table " + TABLE_WORDS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,WORD TEXT, MEANING TEXT, LEARN_STATUS INTEGER," +
                " FOLDER_ID INTEGER, LAST_NOTIFICATION_TIME TEXT)");
        // Boolean values are stored as integers 0 (false) and 1 (true)
        db.execSQL("create table " + TABLE_NOTIFICATION_STATUS + "(NOTIFICATION_NUMBER INTEGER, NUMBER_OF_NOTIFICATIONS_SENT INTEGER, MONDAY INTEGER DEFAULT 0 NOT NULL CHECK (TUESDAY IN (0, 1)), " +
                "TUESDAY INTEGER DEFAULT 0 NOT NULL CHECK (TUESDAY IN (0, 1)), WEDNESDAY INTEGER DEFAULT 0 NOT NULL CHECK (WEDNESDAY IN (0, 1)), THURSDAY INTEGER DEFAULT 0 NOT NULL CHECK (THURSDAY IN (0, 1)), " +
                "FRIDAY INTEGER DEFAULT 0 NOT NULL CHECK (FRIDAY IN (0, 1)), SATURDAY INTEGER DEFAULT 0 NOT NULL CHECK (SATURDAY IN (0, 1)), SUNDAY INTEGER DEFAULT 0 NOT NULL CHECK (SUNDAY IN (0, 1)))");
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

/*    public Cursor getWord(int folderId, int learnStatus, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_WORDS + "where FOLDER_ID = " + folderId + "and LEARN_STATUS = " + learnStatus +
                "and LAST_NOTIFICATION_TIME <" + time ,null);
        return res;
    }*/
}
