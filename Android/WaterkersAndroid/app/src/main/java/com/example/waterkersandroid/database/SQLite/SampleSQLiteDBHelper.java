package com.example.waterkersandroid.database.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.waterkersandroid.model.SampleSQLite.Gebruiker;

public class SampleSQLiteDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Waterkers";

    public static final String GEBRUIKERS_TABLE_NAME = "gebruikers";
    public static final String GEBRUIKERS_COLUMN_LOGINNAAM = "LoginNaam";
    public static final String GEBRUIKERS_COLUMN_LOGINPASS = "LoginPass";

    public static final String MINMAXWAARDES_TABLE_NAME = "MinMaxWaardes";
    public static final String MINMAXWAARDES_COLUMN_LOCATIE = "locatie";
    public static final String MINMAXWAARDES_COLUMN_MinPH = "MinPH";
    public static final String MINMAXWAARDES_COLUMN_MaxPH = "MaxPH";
    public static final String MINMAXWAARDES_COLUMN_MinGT = "MinGT";
    public static final String MINMAXWAARDES_COLUMN_MaxGT = "MaxGT";
    public static final String MINMAXWAARDES_COLUMN_MinLT = "MinLT";
    public static final String MINMAXWAARDES_COLUMN_MaxLT = "MaxLT";
    public static final String MINMAXWAARDES_COLUMN_MinGV = "MinGV";
    public static final String MINMAXWAARDES_COLUMN_MaxGV = "MaxGV";
    public static final String MINMAXWAARDES_COLUMN_MinLV = "MinLV";
    public static final String MINMAXWAARDES_COLUMN_MaxLV = "MaxLV";

    public static final String ARDUINOLOCATIE_TABLE_NAME = "ArduinoLocatie";
    public static final String ARDUINOLOCATIE_COLUMN_ARDUINOID = "ArduinoID";
    public static final String ARDUINOLOCATIE_COLUMN_LOCATIE = "Locatie";
    public static final String ARDUINOLOCATIE_COLUMN_STATUS="status";

    public static final String REGISTRATIE_TABLE_NAME = "Registratie";
    public static final String REGISTRATIE_COLUMN_ARDUINOID = "ArduinoID";
    public static final String REGISTRATIE_COLUMN_DATUMTIJD = "DatumTijd";
    public static final String REGISTRATIE_COLUMN_PHWAARDE = "PHwaarde";
    public static final String REGISTRATIE_COLUMN_GRONDTEMP = "GrondTemp";
    public static final String REGISTRATIE_COLUMN_LUCHTTEMP = "LuchtTemp";
    public static final String REGISTRATIE_COLUMN_GRONDVOCHT = "GrondVocht";
    public static final String REGISTRATIE_COLUMN_LUCHTVOCHT = "LuchtVocht";

    public static final String BEHEERDARDUINO_TABLE_NAME = "BeheerdArduino";
    public static final String BEHEERDARDUINO_COLUMN_LOGINNAAM = "LoginNaam";
    public static final String BEHEERDARDUINO_COLUMN_ARDUINOID = "ArduinoID";

    public SampleSQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onConfigure(SQLiteDatabase sqLiteDatabase){
        super.onConfigure(sqLiteDatabase);
        sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL
                ("CREATE TABLE "+ GEBRUIKERS_TABLE_NAME
                        + " ("+ GEBRUIKERS_COLUMN_LOGINNAAM + " TEXT NOT NULL, "
                        + GEBRUIKERS_COLUMN_LOGINPASS + " TEXT NOT NULL,"
                        + "CONSTRAINT GEBRUIKERs_PK primary key ('LoginNaam', 'LoginPass')"
                        +")");
        sqLiteDatabase.execSQL
                ("CREATE TABLE "+ MINMAXWAARDES_TABLE_NAME
                + " ("+MINMAXWAARDES_COLUMN_LOCATIE+ " TEXT NOT NULL, "
                + MINMAXWAARDES_COLUMN_MinPH + " REAL,"
                +MINMAXWAARDES_COLUMN_MaxPH + " REAL,"
                +MINMAXWAARDES_COLUMN_MinGT +" REAL,"
                +MINMAXWAARDES_COLUMN_MaxGT + " REAL,"
                +MINMAXWAARDES_COLUMN_MinLT + " REAL,"
                +MINMAXWAARDES_COLUMN_MaxLT + " REAL,"
                +MINMAXWAARDES_COLUMN_MinGV + " REAL,"
                +MINMAXWAARDES_COLUMN_MaxGV + " REAL,"
                +MINMAXWAARDES_COLUMN_MinLV + " REAL,"
                +MINMAXWAARDES_COLUMN_MaxLV + " REAL,"
                +"CONSTRAINT MinMaxWaardes_PK primary key ('locatie')"
                +")");
        sqLiteDatabase.execSQL
                ("CREATE TABLE "+ARDUINOLOCATIE_TABLE_NAME
                + " ("+ARDUINOLOCATIE_COLUMN_ARDUINOID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                +ARDUINOLOCATIE_COLUMN_LOCATIE+ " TEXT NOT NULL, " +
                                ""+ARDUINOLOCATIE_COLUMN_STATUS+" TEXT CHECK("+ ARDUINOLOCATIE_COLUMN_STATUS+ "IN ('Aan','Uit','Defect')) NOT NULL,"
                        +"CONSTRAINT ArduinoLocatie_PK primary key ('ArduinoID'),"
                                +"CONSTRAINT ArduinoLocatie_FK foreign key ('Locatie') REFERENCES 'MinMaxWaardes'('locatie')"
                        +")"
                        );
        sqLiteDatabase.execSQL
                ("CREATE TABLE "+REGISTRATIE_TABLE_NAME
                        +" ("+REGISTRATIE_COLUMN_ARDUINOID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + REGISTRATIE_COLUMN_DATUMTIJD+ " NUMERIC DEFAULT CURRENT_TIMESTAMP NOT NULL, "
                        + REGISTRATIE_COLUMN_PHWAARDE + " REAL,"
                        + REGISTRATIE_COLUMN_GRONDTEMP + " REAL,"
                        + REGISTRATIE_COLUMN_LUCHTTEMP + " REAL,"
                        + REGISTRATIE_COLUMN_GRONDVOCHT + " REAL,"
                        + REGISTRATIE_COLUMN_LUCHTVOCHT + " REAL,"
                        + "CONSTRAINT Registratie_PK primary key ('ArduinoID','DatumTijd'),"
                        + "CONSTRAINT Registratie_FK foreign key ('ArduinoID') REFERENCES 'ArduinoLocatie'('ArduinoID')"
                        + ")"
                );
        sqLiteDatabase.execSQL
                ("CREATE TABLE "+BEHEERDARDUINO_TABLE_NAME
                + " ("+BEHEERDARDUINO_COLUMN_LOGINNAAM+ " TEXT NOT NULL,"
                        + BEHEERDARDUINO_COLUMN_ARDUINOID+ " INTEGER NOT NULL,"
                        + "CONSTRAINT BeheerdArduino_FK1 foreign key ('LoginNaam') REFERENCES 'Gebruiker'('LoginNaam'),"
                        + "CONSTRAINT BeheerdArduino_FK2 foreign key ('ArduinoID') REFERENCES 'ArduinoLocatie'('ArduinoID')"
                        + ")"
                );
    }
//    public void addUser(Users users){
//        SQLiteDatabase waterkersDB = getWritableDatabase();
//        waterkersDB.beginTransaction();
//        try {
//            ContentValues values = new ContentValues();
//            values.put(GEBRUIKERS_COLUMN_LOGINNAAM, users.LoginNaam);
//            values.put(GEBRUIKERS_COLUMN_LOGINPASS, users.LoginPass);
//
//            waterkersDB.insertOrThrow(GEBRUIKERS_TABLE_NAME, null, values);
//            waterkersDB.setTransactionSuccessful();
//        }catch (Exception e) {
//            Log.d(TAG, "Error while trying to add post to database");
//        }finally {
//            waterkersDB.endTransaction();
//        }
//    }
//    public void addOrUpdateUser(Gebruiker gebruiker){
//        SQLiteDatabase waterkersDB = getWritableDatabase();
//        waterkersDB.beginTransaction();
//        try {
//            ContentValues values = new ContentValues();
//            values.put(GEBRUIKERS_COLUMN_LOGINNAAM, gebruiker.LoginNaam);
//            values.put(GEBRUIKERS_COLUMN_LOGINPASS, gebruiker.LoginPass);
//
//            int rows =waterkersDB.update(GEBRUIKERS_TABLE_NAME, values, GEBRUIKERS_COLUMN_LOGINNAAM + "= ?", new String[]{gebruiker.LoginNaam});
//
//            if (rows == 1) {
//                String gebruikersSelectQuery = String.format("SELECT %s FROM %s WHERE %s =?",
//                        GEBRUIKERS_COLUMN_LOGINNAAM, GEBRUIKERS_TABLE_NAME, GEBRUIKERS_COLUMN_LOGINNAAM);
//                Cursor cursor = waterkersDB.rawQuery(gebruikersSelectQuery, new String[]{String.valueOf(gebruiker.LoginNaam)});
//                try {
//                    if (cursor.moveToFirst()) {
//                        waterkersDB.setTransactionSuccessful();
//                    }
//                } finally {
//                    if (cursor != null && !cursor.isClosed()) {
//                        cursor.close();
//                    }
//                }
//            }
//
//        }catch (Exception e) {
//            Log.d(TAG, "Error while trying to add or Update user");
//        }finally {
//            waterkersDB.endTransaction();
//        }
//    }


    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ GEBRUIKERS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+MINMAXWAARDES_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ARDUINOLOCATIE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+REGISTRATIE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+BEHEERDARDUINO_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public void addGebruiker(Gebruiker gebruiker){
        SQLiteDatabase waterkersDB = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GEBRUIKERS_COLUMN_LOGINNAAM, gebruiker.getLoginNaam());
        values.put(GEBRUIKERS_COLUMN_LOGINPASS, gebruiker.getLoginPass());

        waterkersDB.insert(GEBRUIKERS_TABLE_NAME, null, values);
        waterkersDB.close();
    }
//
//    public Boolean checkusernamepassword(String username, String password){
//        SQLiteDatabase waterkersDB = this.getWritableDatabase();
//        Cursor cursor = waterkersDB.rawQuery("SELECT * FROM gebruikers WHERE LoginNaam = ? and LoginPass = ? ", new String[]{username,password});
//        if (cursor.getCount()>0)
//            return true;
//        else
//            return false;
//    }

//    public void insert(String[] LoginNaam, String[]LoginPass){
//        SQLiteDatabase waterkersDB = this.getWritableDatabase();
//        ContentValues vals = new ContentValues();
//
//        vals.put(GEBRUIKERS_COLUMN_LOGINNAAM, "charlotte");
//        vals.put(GEBRUIKERS_COLUMN_LOGINPASS, "charlotte1");
//        long gebruikers = waterkersDB.insert(GEBRUIKERS_TABLE_NAME, null, vals);
//
//        vals.put(MINMAXWAARDES_COLUMN_LOCATIE, "kas1");
//        vals.put(MINMAXWAARDES_COLUMN_MinPH, "6,5");
//        vals.put(MINMAXWAARDES_COLUMN_MaxPH, "7,5");
//        vals.put(MINMAXWAARDES_COLUMN_MinGT, "13");
//        vals.put(MINMAXWAARDES_COLUMN_MaxGT, "17");
//        vals.put(MINMAXWAARDES_COLUMN_MinLT, "10");
//        vals.put(MINMAXWAARDES_COLUMN_MaxLT, "15");
//        vals.put(MINMAXWAARDES_COLUMN_MinGV, "50");
//        vals.put(MINMAXWAARDES_COLUMN_MaxGV, "70");
//        vals.put(MINMAXWAARDES_COLUMN_MinLV, "60");
//        vals.put(MINMAXWAARDES_COLUMN_MaxLV, "100");
//        long minmaxwaardes = waterkersDB.insert(MINMAXWAARDES_TABLE_NAME, null, vals);
//
//        vals.put(ARDUINOLOCATIE_COLUMN_LOCATIE, "kas1");
//        vals.put(ARDUINOLOCATIE_COLUMN_STATUS, "Aan");
//        long arduinolocatie = waterkersDB.insert(ARDUINOLOCATIE_TABLE_NAME, null, vals);
//
//        vals.put(REGISTRATIE_COLUMN_PHWAARDE, "7");
//        vals.put(REGISTRATIE_COLUMN_GRONDTEMP, "16");
//        vals.put(REGISTRATIE_COLUMN_LUCHTTEMP, "15");
//        vals.put(REGISTRATIE_COLUMN_GRONDVOCHT, "40");
//        vals.put(REGISTRATIE_COLUMN_LUCHTVOCHT, "50");
//        long registratie = waterkersDB.insert(REGISTRATIE_TABLE_NAME, null, vals);
//    }
public Cursor getInformation(SampleSQLiteDBHelper dop){
        SQLiteDatabase SQ=dop.getReadableDatabase();
        String[] columnsGebruiker={GEBRUIKERS_COLUMN_LOGINNAAM, GEBRUIKERS_COLUMN_LOGINPASS};
//, MINMAXWAARDES_COLUMN_LOCATIE,MINMAXWAARDES_COLUMN_MinPH,MINMAXWAARDES_COLUMN_MaxPH,MINMAXWAARDES_COLUMN_MinLV,MINMAXWAARDES_COLUMN_MaxLV,MINMAXWAARDES_COLUMN_MinLT,MINMAXWAARDES_COLUMN_MaxLT,MINMAXWAARDES_COLUMN_MinGV,MINMAXWAARDES_COLUMN_MaxGV,MINMAXWAARDES_COLUMN_MinGT,MINMAXWAARDES_COLUMN_MaxGT,
//        ARDUINOLOCATIE_COLUMN_STATUS,ARDUINOLOCATIE_COLUMN_LOCATIE,ARDUINOLOCATIE_COLUMN_ARDUINOID,REGISTRATIE_COLUMN_LUCHTVOCHT,REGISTRATIE_COLUMN_GRONDVOCHT,REGISTRATIE_COLUMN_LUCHTTEMP,REGISTRATIE_COLUMN_GRONDTEMP,REGISTRATIE_COLUMN_PHWAARDE};
        Cursor CR=SQ.query(GEBRUIKERS_TABLE_NAME, columnsGebruiker, null,null, null,null, null);
        return CR;

}

}



