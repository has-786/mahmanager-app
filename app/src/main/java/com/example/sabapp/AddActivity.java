package com.example.sabapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sabapp.adapter.RecyclerViewAdapter;
import com.example.sabapp.data.MyDbHandler;

import java.util.ArrayList;
import java.util.Date;

import static com.example.sabapp.MainActivity.p;
import static com.example.sabapp.MainActivity.recyclerView;

public class AddActivity extends AppCompatActivity {


    MyDbHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setTitle("Add Your Count"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

    }

    public void save(View v)
    {

        Counts c=new Counts();
        EditText et1=findViewById(R.id.et1);
        EditText et2=findViewById(R.id.et2);
        c.setTopic(et1.getText().toString());
        if(et1.getText().toString()==null || et1.getText().toString()=="" || et2.getText().toString()==null || et2.getText().toString()==""
        ) {  Toast.makeText(this,"Please Fill The Places",Toast.LENGTH_SHORT).show();return;}
        String x=et2.getText().toString();
        int i=0;
        while(i<x.length())
        {
            if(x.charAt(i)<'0' || x.charAt(i)>'9'){
                Toast.makeText(this,"Enter A Valid Number",Toast.LENGTH_SHORT).show();return;
            }
            i++;
        }
        c.setCount(Integer.parseInt(x));

        Date d=new Date();
        String s=d.toString();
        String week="";  i=0;
        while(i<s.length() && s.charAt(i)!=' '){week+=s.charAt(i); i++; }i++;
        String month="";
        while(i<s.length() && s.charAt(i)!=' '){month+=s.charAt(i);i++; }i++;
        String date="";
        while(i<s.length() && s.charAt(i)!=' '){date+=s.charAt(i);i++; }i++;
        String time="";
        while(i<s.length() && s.charAt(i)!=' '){time+=s.charAt(i);i++; }i++;
        while(i<s.length() && s.charAt(i)!=' '){i++; }i++;
        String year="";
        while(i<s.length() && s.charAt(i)!=' '){year+=s.charAt(i);i++; }i++;
        week="Added on "+week;
        week+=" , "+month+" "+date+" , "+year+" at "+time;
        c.setTime(week);

       // p.add(c);
        db=new MyDbHandler(this);
        ArrayList<Counts> arr=db.getAllPoints();
        ArrayList<Counts> p=new ArrayList<>();
        for ( i=0;i<arr.size();i++)
        {
            p.add(arr.get(i));
            Log.d("myapp", "\nId: " + arr.get(i).getId() + "\n" +
                    "Topic: " + arr.get(i).getTopic() + "\n" +
                    "Count: " + arr.get(i).getCount() + "\n" +
                    "Time: " + arr.get(i).getTime() + "\n");
        }

        db.addPoints(c);
        p.add(c);
        RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(this,p);
        recyclerView.setAdapter(recyclerViewAdapter);
        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }


}
