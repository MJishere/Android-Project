package com.example.blooddonor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DonorsDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BLOODDONORS";
    public static final int DATABASE_VERSION = 9;
    public static final String TABLE_NAME ="DONORS";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONENUMBER = "phonenumber";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_BG = "bg";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_PASSWORD = "password";


    public DonorsDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
        String query = " CREATE TABLE "+ TABLE_NAME +"("+ COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NAME +" TEXT,"+ COLUMN_PHONENUMBER +" TEXT,"+ COLUMN_AGE +" TEXT,"+COLUMN_BG+" TEXT,"+ COLUMN_CITY +" TEXT,"+ COLUMN_PASSWORD +" TEXT);";
        db.execSQL(query);
        }
        catch (Exception e){
        e.printStackTrace();
            Log.d("table","Table not created");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addDonors(String name, String phonenumber, String age, String bg, String city, String password)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_PHONENUMBER,phonenumber);
        values.put(COLUMN_AGE,age);
        values.put(COLUMN_BG,bg);
        values.put(COLUMN_CITY,city);
        values.put(COLUMN_PASSWORD,password);
        db.insert(TABLE_NAME,null,values);
        db.close();

    }

    public Boolean getMatched(String phonenumber, String password) {

        SQLiteDatabase db = getReadableDatabase();
        String Query = "SELECT NAME FROM " + TABLE_NAME + " WHERE "
                + COLUMN_PHONENUMBER + " =? AND " + COLUMN_PASSWORD + " =?";

        Cursor c = db.rawQuery(Query, new String[]{phonenumber,
                password});

        if (c.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public  void deleteAccount(String phonenumber)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_PHONENUMBER + "= '" + phonenumber + "'");
        db.close();

    }

    public List<String> getData(String phonenumber)
    {
       SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {phonenumber};

        List<String> list = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME,null,"phonenumber=?",selectionArgs,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {

                list.add(cursor.getString(cursor.getColumnIndex("name")));
                list.add(cursor.getString(cursor.getColumnIndex("phonenumber")));
                list.add(cursor.getString(cursor.getColumnIndex("age")));
                list.add(cursor.getString(cursor.getColumnIndex("bg")));
                list.add(cursor.getString(cursor.getColumnIndex("city")));
                list.add(cursor.getString(cursor.getColumnIndex("password")));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public  ArrayList<SingleRow> getDonors(String city,String bg)

    {
        ArrayList<SingleRow> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {city, bg};
        String selection = COLUMN_CITY + " =?" + " and " + COLUMN_BG + " =?";
        Cursor cursor = db.query(TABLE_NAME,null, selection, selectionArgs,null,null,null);
        if (cursor.moveToFirst()){
            do {
               String name = cursor.getString(1);
               String phonenumber = cursor.getString(2);
               Log.d("name",name);
               SingleRow singleRow = new SingleRow(name,phonenumber);
               list.add(singleRow);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean update(String oldPhonenumber, String newPhonenumber, String name, String age, String bg, String city , String password)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_PHONENUMBER,newPhonenumber);
        contentValues.put(COLUMN_AGE,age);
        contentValues.put(COLUMN_BG,bg);
        contentValues.put(COLUMN_CITY,city);
        contentValues.put(COLUMN_PASSWORD,password);

        db.update(TABLE_NAME,contentValues,"phonenumber = ?" , new String[] { oldPhonenumber } );
        return  true;

    }

}
