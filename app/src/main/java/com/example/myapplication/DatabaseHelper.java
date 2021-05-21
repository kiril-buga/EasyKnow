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

    // WORDS_TABLE
    public static final String WORD_COL_1 = "ID";
    public static final String WORD_COL_2 = "WORD";
    public static final String WORD_COL_3 = "MEANING";
    public static final String WORD_COL_4 = "LEARN_STATUS";
    public static final String WORD_COL_5 = "FOLDER_ID";
    public static final String WORD_COL_6 = "LAST_NOTIFICATION_TIME";
    //FOLDERS_TABLE
    public static final String FOLDER_COL_1 = "ID";
    public static final String FOLDER_COL_2 = "FOLDER";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_FOLDERS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FOLDER TEXT)");
        db.execSQL("create table " + TABLE_WORDS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,WORD TEXT, MEANING TEXT, LEARN_STATUS INTEGER," +
                " FOLDER_ID INTEGER, LASTNOTIFICATIONTIME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
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
}
