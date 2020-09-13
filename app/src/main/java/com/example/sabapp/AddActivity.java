package com.example.sabapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sabapp.adapter.RecyclerViewAdapter;
import com.example.sabapp.data.MyDbHandler;

import java.util.ArrayList;
import java.util.Date;

import static com.example.sabapp.MainActivity.p;
import static com.example.sabapp.MainActivity.recyclerView;

public class AddActivity extends AppCompatActivity {

    private static final int RESULT_PICK_CONTACT = 1001;
    private static final int RESULT_PICK_WHATSAPP = 1002;

    TextView tvContactNumber,tvWhatsappNumber,code,code2;

    String contactNumber="";
    String whatsappNumber="";

    MyDbHandler db;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setTitle("Add An Invitee"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar
        Intent intent=getIntent();
        String x=intent.getStringExtra("id");
        Log.d("myapp1",x);
         id=Integer.parseInt(x);
        tvContactNumber=findViewById(R.id.tvContactNumber);
        tvWhatsappNumber=findViewById(R.id.tvWhatsappNumber);


        if(tvWhatsappNumber.getText().toString().length()==0)tvWhatsappNumber.setAlpha(0);
        if(tvContactNumber.getText().toString().length()==0)tvContactNumber.setAlpha(0);


    }

    public void save(View v)
    {
        EditText et1=findViewById(R.id.et1);
        EditText et2=findViewById(R.id.et2);
        code=findViewById(R.id.code1);
        code2=findViewById(R.id.code2);

        tvContactNumber=findViewById(R.id.tvContactNumber);
        tvWhatsappNumber=findViewById(R.id.tvWhatsappNumber);

        if(et1.getText().toString().equals("")
                || et2.getText().toString().equals("")
                || tvContactNumber.getText().toString().equals("") ||
                tvWhatsappNumber.getText().toString().equals("") ||
                code.getText().toString().equals("") ||  code2.getText().toString().equals("") )
        {  Toast.makeText(this,"Please Fill The Places",Toast.LENGTH_SHORT).show();return;}

        Counts c=new Counts();

        c.setTopic(et1.getText().toString());

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
        //c.setCount(0);
        String week=""; i=0;
      Date d=new Date();
        String s=d.toString();
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


        if(contactNumber.length()==11)contactNumber="+"+code.getText().toString()+" "+contactNumber;
        if(whatsappNumber.length()==11)whatsappNumber="+"+code2.getText().toString()+" "+whatsappNumber;

        c.setPhone(contactNumber);
        c.setWhatsapp(whatsappNumber);


        db=new MyDbHandler(this);
        db.addPoints(c,id);
        Log.d("myapp1",id+"");
        ArrayList<Counts> arr=db.getAllPoints(id);
        ArrayList<Counts> p=new ArrayList<>();

        for ( i=0;i<arr.size();i++)
        {
            p.add(arr.get(i));
            Log.d("myapp", "\nId: " + arr.get(i).getId() + "\n" +
                    "Topic: " + arr.get(i).getTopic() + "\n" +
                    "Count: " + arr.get(i).getCount() + "\n" +
                    "Time: " + arr.get(i).getTime() + "\n");
        }

      //  RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(this,p);
    //    recyclerView.setAdapter(recyclerViewAdapter);
        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
        //Intent intent=new Intent(this,EventActivity.class);
       // intent.putExtra("id",Integer.toString(id));
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        //startActivity(intent);
        finish();
    }



    public void pickContact(View v) {

        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    public void pickWhatsapp(View v) {

        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_WHATSAPP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    Cursor cursor = null;
                    try {

                        Uri uri = data.getData();
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();

                        int phoneIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER);

                        contactNumber = cursor.getString(phoneIndex);
                        tvContactNumber=findViewById(R.id.tvContactNumber);
                        tvContactNumber.setText(contactNumber);
                        tvContactNumber.setAlpha(1);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case RESULT_PICK_WHATSAPP:
                     cursor = null;
                    try {
                        //    String contactNumber = null;
                        Uri uri = data.getData();
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int phoneIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER);

                        whatsappNumber = cursor.getString(phoneIndex);
                        tvWhatsappNumber=findViewById(R.id.tvWhatsappNumber);
                        tvWhatsappNumber.setText(whatsappNumber);
                        tvWhatsappNumber.setAlpha(1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}



