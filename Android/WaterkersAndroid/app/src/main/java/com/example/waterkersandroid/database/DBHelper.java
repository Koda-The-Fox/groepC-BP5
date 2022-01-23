package com.example.waterkersandroid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Waterkers.db";

    public DBHelper(Context context) {
        super(context, "Waterkers.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Gebruiker(gebruikersnaam TEXT primary key, password TEXT)");
        db.execSQL("create Table MinMaxWaardes");
        db.execSQL("create Table ArduinoLocatie");
        db.execSQL("create Table Registratie");
        db.execSQL("create Table BeheerdArduino");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

