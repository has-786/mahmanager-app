package com.example.sabapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sabapp.Counts;
import com.example.sabapp.R;
import com.example.sabapp.data.MyDbHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    static int width=0;
    private Context context;
    private ArrayList<Counts> arr,constArr;
    MyDbHandler db;
    public RecyclerViewAdapter(Context context, ArrayList<Counts>  arr) {
        this.context = context;
        this.arr = arr;
        this.constArr=arr;
        db=new MyDbHandler(context);

    }

    // Where to get the single card as viewholder Object
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        //  width=view.findViewById(R.id.layout).getLayoutParams().width;

        return new ViewHolder(view);
    }

    // What will happen after we create the viewholder object
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        try {
            Counts counts = arr.get(position);
         //   Toast.makeText(context,"Title:    "+counts.getTopic()+"fafa",Toast.LENGTH_LONG).show();
            holder.id.setText(Integer.toString(position+1));
            holder.topic.setText("Topic:    "+counts.getTopic());
            holder.count.setText(Integer.toString(counts.getCount()));
            holder.timestamp.setText(counts.getTime());
            holder.dbid.setText(Integer.toString(counts.getId()));






        }
        catch(Exception e){}
    }

    // How many items?
    @Override
    public int getItemCount() {
        return arr.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView topic;
        public TextView count,timestamp,id,dbid;
        public Button b1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            topic= itemView.findViewById(R.id.t1);
            count = itemView.findViewById(R.id.t2);
            timestamp=itemView.findViewById(R.id.t3);
            b1=itemView.findViewById(R.id.b1);
            id=itemView.findViewById(R.id.i);
            dbid=itemView.findViewById(R.id.dbid);

           // final int i=getAdapterPosition();
          //  Toast.makeText(context,Integer.toString(i),Toast.LENGTH_SHORT).show();

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                int j=0;
                    db.deleteContact(Integer.parseInt(dbid.getText().toString()));
                    arr.remove(Integer.parseInt(id.getText().toString())-1);
                    Toast.makeText(context,"Record Deleted",Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });


            CardView inc=itemView.findViewById(R.id.card2);
            inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int j=Integer.parseInt(id.getText().toString())-1;
                    Counts c=arr.get(j);
                    c.setCount(c.getCount()+1);
                    arr.set(j,c);
                    notifyDataSetChanged();
                    db.updateContact(c);


                }
            });

            CardView dec=itemView.findViewById(R.id.card3);
            dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int j=Integer.parseInt(id.getText().toString())-1;
                    Counts c=arr.get(j);
                    if(c.getCount()==0){
                        Toast.makeText(context,"Count is already 0",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    c.setCount(c.getCount()-1);
                    arr.set(j,c);
                    notifyDataSetChanged();
                    db.updateContact(c);


                }
            });

            //Toast.makeText(context,Integer.toString(width),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClick(View view) {
            Log.d("ClickFromViewHolder", "Clicked");



        }

    }



}


