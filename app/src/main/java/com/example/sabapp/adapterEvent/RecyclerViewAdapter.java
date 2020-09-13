package com.example.sabapp.adapterEvent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sabapp.Counts;
import com.example.sabapp.EventActivity;
import com.example.sabapp.Events;
import com.example.sabapp.R;
import com.example.sabapp.data.MyDbHandler;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    static int width=0;
    private Context context;
    private ArrayList<Events> arr,constArr;
    MyDbHandler db;
    public RecyclerViewAdapter(Context context, ArrayList<Events>  arr) {
        this.context = context;
        this.arr = arr;
        this.constArr=arr;
        db=new MyDbHandler(context);

    }

    // Where to get the single card as viewholder Object
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowevent, parent, false);
        //  width=view.findViewById(R.id.layout).getLayoutParams().width;

        return new ViewHolder(view);
    }

    // What will happen after we create the viewholder object
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        try {
            Events events = arr.get(position);
            //   Toast.makeText(context,"Title:    "+counts.getTopic()+"fafa",Toast.LENGTH_LONG).show();
            holder.id.setText(Integer.toString(position+1));
            holder.topic.setText("Event:    "+events.getTopic());
            holder.dbid.setText(Integer.toString(events.getId()));
            holder.date.setText(events.getDate()+", "+events.getTime());

        }
        catch(Exception e){}
    }

    // How many items?
    @Override
    public int getItemCount() {
        return arr.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView topic,time,id,dbid,date;
        public Button b1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            topic= itemView.findViewById(R.id.t1);
            date=itemView.findViewById(R.id.t2);
            b1=itemView.findViewById(R.id.b1);
            id=itemView.findViewById(R.id.i);
            dbid=itemView.findViewById(R.id.dbid);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage("Do you want to delete event "+topic.getText().toString());
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id1) {

                                    db.deleteEvent(Integer.parseInt(dbid.getText().toString()));
                                    arr.remove(Integer.parseInt(id.getText().toString())-1);
                                    Toast.makeText(context,"Event Deleted",Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();

                                     dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();


                }
            });





            //Toast.makeText(context,Integer.toString(width),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClick(View view) {

            Intent i=new Intent(context,EventActivity.class);
            i.putExtra("id",id.getText().toString());
            context.startActivity(i);
            Log.d("ClickFromViewHolder", id.getText().toString());

        }

    }



}


