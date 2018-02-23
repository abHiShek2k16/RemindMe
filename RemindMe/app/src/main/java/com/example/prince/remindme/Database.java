package com.example.prince.remindme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prince on 29/10/17.
 */
public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="Schedules";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Demo(_id INTEGER PRIMARY KEY AUTOINCREMENT,day INT,class_name VARCHAR,batch_name VARCHAR,location VARCHAR, start_time VARCHAR, end_time VARCHAR,enable INTEGER);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Demo(_id INTEGER PRIMARY KEY AUTOINCREMENT,day INT,class_name VARCHAR,batch_name VARCHAR,location VARCHAR, start_time VARCHAR, end_time VARCHAR,enable INT);");
    }
}