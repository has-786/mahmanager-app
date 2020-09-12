package com.example.sabapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sabapp.Counts;
import com.example.sabapp.params.Params;

import java.util.ArrayList;

import static com.example.sabapp.params.Params.KEY_ID;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ Params.KEY_TOPIC
                + " TEXT, " + Params.KEY_COUNTS
                + " INTEGER, " + Params.KEY_TIME + " TEXT" + ")";
        Log.d("myapp", "Query being run is : "+ create);
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addPoints(Counts counts){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TOPIC, counts.getTopic());
        values.put(Params.KEY_COUNTS, counts.getCount());
        values.put(Params.KEY_TIME, counts.getTime());

        db.insert(Params.TABLE_NAME, null, values);
        Log.d("myapp", "Successfully inserted");
        db.close();
    }

    public ArrayList<Counts> getAllPoints(){
        ArrayList<Counts> countsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);

        //Loop through now
        if(cursor.moveToFirst()){
            do{
                Counts counts = new Counts();
                counts.setId(Integer.parseInt(cursor.getString(0)));
                counts.setTopic(cursor.getString(1));
                counts.setCount(Integer.parseInt(cursor.getString(2)));
                counts.setTime(cursor.getString(3));

                countsList.add(counts);
            }while(cursor.moveToNext());
        }
        return countsList;
    }

    public int updateContact(Counts counts){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TOPIC, counts.getTopic());
        values.put(Params.KEY_COUNTS, counts.getCount());
        values.put(Params.KEY_TIME, counts.getTime());

        //Lets update now
        return db.update(Params.TABLE_NAME, values, KEY_ID + "=?",
                new String[]{String.valueOf(counts.getId())});


    }
    public int deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String arr[] = new String[10];
        //Lets update now
        return db.delete(Params.TABLE_NAME, KEY_ID + "=" + id, null);


    }




}

