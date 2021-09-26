package com.example.votingapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

//import androidx.annotation.Nullable;

public class DBEvent extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "eventInfo.db";

    public DBEvent(Context context) {super(context, DATABASE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase MyDB) {

        //events table
        MyDB.execSQL("create Table events(name TEXT, description TEXT, noOfVotes TEXT, date TEXT, startTime TEXT, endTime TEXT, eventID TEXT primary key, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        //events table
        MyDB.execSQL("drop Table if exists events");
    }

    //events table
    //insert data
    public Boolean addEvents(String name, String description, String noOfVotes, String date, String startTime, String endTime, String eventID, String password) {

        SQLiteDatabase MyDB = this.getWritableDatabase();

        ContentValues eventValues = new ContentValues();

        eventValues.put("name", name);
        eventValues.put("description", description);
        eventValues.put("noOfVotes", noOfVotes);
        eventValues.put("date", date);
        eventValues.put("startTime", startTime);
        eventValues.put("endTime", endTime);
        eventValues.put("eventID", eventID);
        eventValues.put("password", password);

        long result = MyDB.insert("events", null, eventValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //update
    public Boolean updateEvents(String name, String description, String noOfVotes, String date, String startTime, String endTime, String eventID, String password) {
        SQLiteDatabase MyDB = this.getReadableDatabase();

        ContentValues eventValues = new ContentValues();

        eventValues.put("name", name);
        eventValues.put("description", description);
        eventValues.put("noOfVotes", noOfVotes);
        eventValues.put("date", date);
        eventValues.put("startTime", startTime);
        eventValues.put("endTime", endTime);
        eventValues.put("password", password);


        Cursor cursor = MyDB.rawQuery("select * from events where eventID = ?", new String[] {eventID});

        if (cursor.getCount()>0) {
            long result = MyDB.update("events", eventValues, "eventID = ?", new String[] {eventID});
            if (result == -1) {
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }

    //delete
    public Boolean deleteEvents(String eventID) {
        SQLiteDatabase MyDB = this.getReadableDatabase();

        Cursor cursor = MyDB.rawQuery("select * from events where eventID = ?", new String[] {eventID});
        if (cursor.getCount()>0) {
            long result = MyDB.delete("events", "eventID = ?", new String[] {eventID});
            if (result == -1) {
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }

    //view data
    public Cursor getEvents() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from events", null);
        return cursor;
    }

    //view one
    public Cursor getOneEvent(String eventID) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from events where eventID = ?", new String[] {eventID});
        return cursor;
    }



    //event table
    public Boolean checkEventID(String eventID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from events where eventID = ?", new String[] {eventID});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

}
