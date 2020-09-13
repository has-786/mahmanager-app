package com.example.sabapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CalendarContract;
import android.util.Log;

import com.example.sabapp.Counts;
import com.example.sabapp.Events;
import com.example.sabapp.params.Params;

import java.util.ArrayList;

import static com.example.sabapp.params.Params.KEY_ID;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE  IF NOT EXISTS " + Params.TABLE_NAME1 + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ Params.KEY_TOPIC
                + " TEXT, " + Params.KEY_COUNTS
                + " INTEGER, "+Params.KEY_EVENTID
                + " INTEGER, " + Params.KEY_TIME + " TEXT, " +
                Params.KEY_PHONE
                + " TEXT, " + Params.KEY_WHATSAPP + " TEXT"+ ")";
        Log.d("myapp", "Query being run is : "+ create);
        db.execSQL(create);

         create = "CREATE TABLE IF NOT EXISTS " + Params.TABLE_NAME2 + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ Params.KEY_TOPIC
                + " TEXT, " + Params.KEY_DATE+ " TEXT, " +
                Params.KEY_TIME
                + " TEXT"+ ")";
        Log.d("myapp", "Query being run is : "+ create);
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void addPoints(Counts counts,int id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TOPIC, counts.getTopic());
        values.put(Params.KEY_COUNTS, counts.getCount());
        values.put(Params.KEY_EVENTID, id);
        values.put(Params.KEY_TIME, counts.getTime());
        values.put(Params.KEY_PHONE, counts.getPhone());
        values.put(Params.KEY_WHATSAPP, counts.getWhatsapp());

        db.insert(Params.TABLE_NAME1, null, values);
        Log.d("myapp", "Successfully inserted");
        db.close();
    }


    public void addEvents(Events events){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TOPIC, events.getTopic());
        values.put(Params.KEY_TIME, events.getTime());
        values.put(Params.KEY_DATE, events.getDate());

        db.insert(Params.TABLE_NAME2, null, values);
        Log.d("myapp", "Successfully inserted event");
        db.close();
    }

    public ArrayList<Counts> getAllPoints(int id){

        ArrayList<Counts> countsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE_NAME1+" WHERE EVENTID="+id;
        Cursor cursor = db.rawQuery(select, null);
        //Loop through now
        if(cursor.moveToFirst()){
            do{
                Counts counts = new Counts();
                counts.setId(Integer.parseInt(cursor.getString(0)));
                counts.setTopic(cursor.getString(1));
                counts.setCount(Integer.parseInt(cursor.getString(2)));
                counts.setEventId(Integer.parseInt(cursor.getString(3)));
                counts.setTime(cursor.getString(4));
                counts.setPhone(cursor.getString(5));
                counts.setWhatsapp(cursor.getString(6));

                countsList.add(counts);
            }while(cursor.moveToNext());
        }

        Log.d("myapp1",countsList.size()+"");

        return countsList;
    }

    public ArrayList<Events> getAllEvents(){
        ArrayList<Events> eventList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE_NAME2;
        Cursor cursor = db.rawQuery(select, null);
        //Loop through now
        if(cursor.moveToFirst()){
            do{
                Events events = new Events();
                events.setId(Integer.parseInt(cursor.getString(0)));
                events.setTopic(cursor.getString(1));
                events.setDate(cursor.getString(2));
                events.setTime(cursor.getString(3));

                eventList.add(events);
            }while(cursor.moveToNext());
        }

        Log.d("myapp1",eventList.size()+"");

        return eventList;
    }


    public int updateContact(Counts counts){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TOPIC, counts.getTopic());
        values.put(Params.KEY_COUNTS, counts.getCount());
        values.put(Params.KEY_EVENTID, counts.getEventId());
        values.put(Params.KEY_TIME, counts.getTime());
        values.put(Params.KEY_PHONE, counts.getPhone());
        values.put(Params.KEY_WHATSAPP, counts.getWhatsapp());

        //Lets update now
        return db.update(Params.TABLE_NAME1, values, KEY_ID + "=?",
                new String[]{String.valueOf(counts.getId())});

    }

    public int updateEvent(Events events){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TOPIC, events.getTopic());
        values.put(Params.KEY_DATE, events.getDate());
        values.put(Params.KEY_TIME, events.getTime());

        //Lets update now
        return db.update(Params.TABLE_NAME2, values, KEY_ID + "=?",
                new String[]{String.valueOf(events.getId())});

    }
    public int deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String arr[] = new String[10];
        //Lets update now
        return db.delete(Params.TABLE_NAME1, KEY_ID + "=" + id, null);

    }

    public int deleteEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String arr[] = new String[10];
        //Lets update now
        return db.delete(Params.TABLE_NAME2, KEY_ID + "=" + id, null);

    }




}

