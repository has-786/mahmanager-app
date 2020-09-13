package com.example.sabapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sabapp.adapter.RecyclerViewAdapter;
import com.example.sabapp.data.MyDbHandler;
import com.example.sabapp.params.Params;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EventActivity extends AppCompatActivity {
    MyDbHandler db;
    private EditText et1,et2;
    private Button b2;
    int id;
    ArrayList<Counts> arr;
    public static ArrayList<Counts> p;
    public  static RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // startActivity(new Intent(this,PhoneActivity.class));
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        b2=findViewById(R.id.b2);
        getSupportActionBar().setTitle("Invitees");

        Intent intent=getIntent();
        String x=intent.getStringExtra("id");
         id=Integer.parseInt(x);
        db=new MyDbHandler(this);
        arr=db.getAllPoints(id);
        p=new ArrayList<>();
        for (int i=0;i<arr.size();i++)
        {
            p.add(arr.get(i));
            Log.d("myapp", "\nId: " + arr.get(i).getId() + "\n" +
                    "Topic: " + arr.get(i).getTopic() + "\n" +
                    "Count: " + arr.get(i).getCount() + "\n" +
                    "Time: " + arr.get(i).getTime() + "\n");
        }

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, p);
        recyclerView.setAdapter(recyclerViewAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent i=new Intent(EventActivity.this,AddActivity.class);
                i.putExtra("id",Integer.toString(id));
                startActivity(i);
              //  finish();
            }
        });





    }


    protected void onStart() {
        super.onStart();
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // startActivity(new Intent(this,PhoneActivity.class));
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        b2 = findViewById(R.id.b2);
        getSupportActionBar().setTitle("Invitees");

        Intent intent = getIntent();
        String x = intent.getStringExtra("id");
        id = Integer.parseInt(x);
        db = new MyDbHandler(this);
        arr = db.getAllPoints(id);
        p = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            p.add(arr.get(i));
            Log.d("myapp", "\nId: " + arr.get(i).getId() + "\n" +
                    "Topic: " + arr.get(i).getTopic() + "\n" +
                    "Count: " + arr.get(i).getCount() + "\n" +
                    "Time: " + arr.get(i).getTime() + "\n");
        }

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, p);
        recyclerView.setAdapter(recyclerViewAdapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(EventActivity.this,AddActivity.class);
                i.putExtra("id",Integer.toString(id));
                startActivity(i);
                //  finish();
            }
        });
    }

}
