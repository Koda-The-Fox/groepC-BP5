//package com.example.wandelmaatjes.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DataDBHelper  extends SQLiteOpenHelper {
//
//    private static final int DATABASE_VERSION = 1;
//    private static final String DATABASE_NAME ="WandelMaatjes.db";
//
//    private static final String SQL_CREATE_USERS = "create table " + DataContract.Users.TABLE_NAME + " ("
//            + DataContract.Users.COLUMN_NAME_USERNAME + "text unique primary key, "
//            + DataContract.Users.COLUMN_NAME_FIRSTNAME + "text not null, "
//            + DataContract.Users.COLUMN_NAME_LASTNAME + "text not null, "
//            + DataContract.Users.COLUMN_NAME_EMAIL + "text not null, "
//            + DataContract.Users.COLUMN_NAME_PASSWORD + "text unique not null)";
//
//    //private static final String SQL_CREATE_USERS_HAS_WANDELGROEPEN = "create table " + DataContract.Users_has_Wandelgroep.TABLE_NAME + " ("
//
//
//    private static final String SQL_CREATE_WANDELGROEPEN = "create table " + DataContract.Wandelgroepen.TABLE_NAME + " ("
//            + DataContract.Wandelgroepen.COLUMN_NAME_WANDELGROEP + "text primary key)";
//
//    private static final String SQL_CREATE_WANDELROUTE_HAS_WANDELGROEP = "create table " + DataContract.Wandelroute_has_Wandelgroep.TABLE_NAME + " ("
//            + DataContract.Wandelroute_has_Wandelgroep.COLUMN_NAME_WANDELROUTE + "text primary key, "
//            + DataContract.Wandelroute_has_Wandelgroep.COLUMN_NAME_WANDELGROEP + "text not null, "
//            + DataContract.Wandelroute_has_Wandelgroep.COLUMN_NAME_DATE + "date not null, "
//            + DataContract.Wandelroute_has_Wandelgroep.COLUMN_NAME_TIME + "time not null)";
//
//    private static final String SQL_CREATE_WANDELROUTE ="create table " + DataContract.Wandelroute.TABLE_NAME + " ("
//            + DataContract.Wandelroute.COLUMN_NAME_WANDELROUTE + "text primary key, "
//            + DataContract.Wandelroute.COLUMN_NAME_WANDELGROEP + "text not null, "
//            + DataContract.Wandelroute.COLUMN_NAME_STARTLOCATIE + "text, " //locatie??
//            + DataContract.Wandelroute.COLUMN_NAME_EINDLOCATIE + "text, " //locatie??
//            + DataContract.Wandelroute.COLUMN_NAME_DUUR + "text, " // tijd in minuten?
//            + DataContract.Wandelroute.COLUMN_NAME_WANDELTYPE + "text not null, "
//            + DataContract.Wandelroute.COLUMN_NAME_WAARDERING + "integer)"; //integer?
//
//
//
//    public DataDBHelper(Context context){
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SQL_CREATE_USERS);
//        //db.execSQL(SQL_CREATE_WANDELGROEPEN);
//        //db.execSQL(SQL_CREATE_WANDELROUTE_HAS_WANDELGROEP);
//        //db.execSQL(SQL_CREATE_WANDELROUTE);
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table if exists users");
//       //db.execSQL("drop table if exists gebruikers_bij_wandelgroep");
//       // db.execSQL("drop table if exists wandelgroepen");
//        //db.execSQL("drop table if exists wandelroute_wandelgroep");
//        //db.execSQL("drop table if exists wandelroutes");
//    }
//// vanaf hier weet ik niet meer zeker wat ik doe...youtube filmpje "Login and Register From using SQLite Database in Android Studio | login registration android studio"
//    public Boolean insertData (String gebruikersnaam, String wachtwoord, String voornaam, String achternaam, String email){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues= new ContentValues();
//        contentValues.put("gebruikersnaam", gebruikersnaam);
//        contentValues.put("wachtwoord", wachtwoord);
//        contentValues.put("voornaam", voornaam);
//        contentValues.put("achternaam", achternaam);
//        contentValues.put("e-mailadres", email);
//        long result = db.insert("users", null, contentValues);
//        if(result==-1) return false;
//        else
//            return true;
//    }
//
//    public Boolean checkusername(String username){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("Select * from users where gebruikersnaam =?", new String[]{username} );
//        if(cursor.getCount()>0)
//            return true;
//        else
//            return false;
//    }
//
//    public Boolean checkusernamepassword(String username, String password){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("Select * from users where gebruikersnaam =? and wachtwoord = ?", new String[]{username, password});
//        if (cursor.getCount()>0)
//            return true;
//        else
//            return false;
//
//
//    }
//
//
//
//}
