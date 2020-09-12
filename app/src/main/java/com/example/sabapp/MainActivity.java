package com.example.sabapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sabapp.adapter.RecyclerViewAdapter;
import com.example.sabapp.data.MyDbHandler;
import com.example.sabapp.params.Params;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    MyDbHandler db;
    private EditText et1,et2;
    private Button b2;
    ArrayList<Counts> arr;
    public static ArrayList<Counts> p;
    public  static RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         recyclerView=findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        b2=findViewById(R.id.b2);

        db=new MyDbHandler(this);
        arr=db.getAllPoints();
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

                Intent i=new Intent(MainActivity.this,AddActivity.class);
                startActivity(i);
               finish();

                //  points = 0;

            }
        });





    }

}
