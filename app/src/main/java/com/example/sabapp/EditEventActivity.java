package com.example.sabapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sabapp.data.MyDbHandler;

import java.util.ArrayList;
import java.util.Calendar;

public class EditEventActivity extends AppCompatActivity {
    Button dateButton, timeButton;
    TextView dateTextView, timeTextView;
    MyDbHandler db; int id;
    String eventTime,eventDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        Intent i=getIntent();
        id=Integer.parseInt(i.getStringExtra("id"));
        db=new MyDbHandler(this);
        Events events=db.getOneEvent(id);
        dateButton = findViewById(R.id.date);
        timeButton = findViewById(R.id.time);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);

Log.d("myapp",events.toString()+"");
        dateTextView.setText(events.getDate());
        timeTextView.setText(events.getTime());
Toast.makeText(this,events.getTopic(),Toast.LENGTH_SHORT).show();
        EditText et1=findViewById(R.id.et1);
        et1.setText(events.getTopic());
        eventDate=events.getDate();
        eventTime=events.getTime();
    }

    public void save(View v)
    {
        Events events=new Events();
        EditText et1=findViewById(R.id.et1);
        events.setTopic(et1.getText().toString());
        if(et1.getText().toString().equals("")
                || dateTextView.getText().toString().equals("")
                || timeTextView.getText().toString().equals("")
        ) {  Toast.makeText(this,"Please Fill The Places",Toast.LENGTH_SHORT).show();return;}


        events.setDate(eventDate);
        events.setTime(eventTime);
        events.setId(id);

        db=new MyDbHandler(this);
        db.updateEvent(events);

        ArrayList<Events> arr=db.getAllEvents();
        ArrayList<Events> p=new ArrayList<>();
        for (int i=0;i<arr.size();i++)
        {
            p.add(arr.get(i));
            Log.d("myapp", "\nId: " + arr.get(i).getId() + "\n" +
                    "Topic: " + arr.get(i).getTopic() + "\n" +
                    "Date: " + arr.get(i).getDate() + "\n" +
                    "Time: " + arr.get(i).getTime() + "\n");
        }

        // RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(this,p);
        // recyclerView.setAdapter(recyclerViewAdapter);
        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();

        //Intent intent=new Intent(this,MainActivity.class);
        //startActivity(intent);

        finish();
    }

    public void handleTimeButton(View v) {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.d("tag", "onTimeSet: " + hour + minute);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
                timeTextView.setText(dateText);
                eventTime=dateText;
            }
        }, HOUR, MINUTE, false);

        timePickerDialog.show();

    }

    public void handleDateButton(View v) {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();

                dateTextView.setText(dateText);
                eventDate=dateText;
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();




    }

}
