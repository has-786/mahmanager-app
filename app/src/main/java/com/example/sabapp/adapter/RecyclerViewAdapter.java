package com.example.sabapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sabapp.Counts;
import com.example.sabapp.EditEventActivity;
import com.example.sabapp.Events;
import com.example.sabapp.MainActivity;
import com.example.sabapp.R;
import com.example.sabapp.data.MyDbHandler;

import java.util.ArrayList;

import static com.example.sabapp.EventActivity.total;
import static com.example.sabapp.EventActivity.totalM;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

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
            holder.topic.setText(counts.getTopic());
            holder.count.setText(Integer.toString(counts.getCount()));
            holder.dbid.setText(Integer.toString(counts.getId()));
            holder.phoneNo.setText(counts.getPhone());
            holder.whatsappNo.setText(counts.getWhatsapp());

        }
        catch(Exception e){}
    }

    // How many items?
    @Override
    public int getItemCount() {
        return arr.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView topic,count,timestamp,id,dbid,phone,whatsapp,phoneNo,whatsappNo;
        public Button b1,b2;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            topic= itemView.findViewById(R.id.t1);
            count = itemView.findViewById(R.id.t2);
            b1=itemView.findViewById(R.id.b1);
            b2=itemView.findViewById(R.id.b2);

            phoneNo=itemView.findViewById(R.id.phoneNo);
            whatsappNo=itemView.findViewById(R.id.whatsappNo);
            phone=itemView.findViewById(R.id.phone);
            whatsapp=itemView.findViewById(R.id.whatsapp);
            id=itemView.findViewById(R.id.i);
            dbid=itemView.findViewById(R.id.dbid);


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage("Do you want to delete the invitee "+topic.getText().toString());
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id1) {

                                    total-=arr.get(Integer.parseInt(id.getText().toString())-1).getCount();
                                    totalM.setText(""+total);
                                    db.deleteContact(Integer.parseInt(dbid.getText().toString()));
                                    arr.remove(Integer.parseInt(id.getText().toString())-1);
                                    Toast.makeText(context,"Invitee Deleted",Toast.LENGTH_SHORT).show();
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
                    total++;
                    totalM.setText(Integer.toString(total));
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
                    total--;
                    totalM.setText(Integer.toString(total));
                    c.setCount(c.getCount()-1);
                    arr.set(j,c);
                    notifyDataSetChanged();
                    db.updateContact(c);


                }
            });

phone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String temp = "tel:" + phoneNo.getText().toString();
        intent.setData(Uri.parse(temp));

        context.startActivity(intent);
    }
});

            whatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url = "https://api.whatsapp.com/send?phone="+whatsappNo.getText().toString();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });

        }

        @Override
        public void onClick(View view) {
            Log.d("ClickFromViewHolder", "Clicked");



        }

    }



    @Override
    public Filter getFilter() {

        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Counts> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList=constArr;
            } else {
                try{  String filterPattern = constraint.toString().toLowerCase().trim();
                    String[] p=filterPattern.split(" ");
                    for (int j=0;j<p.length;j++)
                    {
                        for (int i=0;i<constArr.size();i++) {

                            if (constArr.get(i).getTopic().toLowerCase().contains(p[j])) {
                                filteredList.add(constArr.get(i));}
                        }
                    }

                }catch (Exception e){}
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arr=new ArrayList<>();
            arr=(ArrayList<Counts>) results.values;
            notifyDataSetChanged();
        }
    };


}


