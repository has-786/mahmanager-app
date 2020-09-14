package com.example.sabapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sabapp.adapterEvent.RecyclerViewAdapter;
import com.example.sabapp.data.MyDbHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MyDbHandler db;
    public static int up=0,com=0,tot=0;
    public static TextView total,upcoming,completed;


    private EditText et1,et2;
    private Button b2;
    ArrayList<Events> arr;
    public static ArrayList<Events> p;
    public  static RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         recyclerView=findViewById(R.id.recycle);

        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        b2=findViewById(R.id.b2);
        total=findViewById(R.id.Total);
        upcoming=findViewById(R.id.Upcoming);
        completed=findViewById(R.id.Completed);


    }

    protected void onStart() {
        super.onStart();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db=new MyDbHandler(this);

        arr=db.getAllEvents();

        tot=arr.size(); up=0; com=0;
        Long mili=System.currentTimeMillis();
            for (int i = 0; i < arr.size(); i++) {
                long x = Long.parseLong(arr.get(i).getMili());
                if (x > mili) up++;
                else com++;
            }

        total.setText(Integer.toString(tot));
        upcoming.setText(Integer.toString(up));
        completed.setText(Integer.toString(com));

        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, arr);
        recyclerView.setAdapter(recyclerViewAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity.this,AddEventActivity.class);
                startActivity(i);
            }
        });

        final SearchView searchView = findViewById(R.id.search);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setClickable(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });


    }

}
