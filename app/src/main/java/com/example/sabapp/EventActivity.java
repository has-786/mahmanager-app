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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
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
    Button b1,b2;
    EditText et1,et2;
    int id=0;
    public static int total=0;
    ArrayList<Counts> arr;
    public  static TextView totalM;
    public static ArrayList<Counts> p;
    public  static RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        getSupportActionBar().setTitle("Invitees");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        b2=findViewById(R.id.b2);
        totalM=findViewById(R.id.TotalM);

    }


    protected void onStart() {
        super.onStart();

        recyclerView=findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String x = intent.getStringExtra("id");
        id = Integer.parseInt(x);
        db = new MyDbHandler(this);
        ArrayList<Counts> arr1=db.getAllPoints1();
        Log.d("myapp8",arr1.size()+"");
        arr = db.getAllPoints(id);
        Log.d("myapp",arr.size()+"");
        total=0;
        for(int i=0;i<arr.size();i++)
            total+=arr.get(i).getCount();
        totalM.setText(total+"");
        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, arr);
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
