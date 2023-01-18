package com.mission.cakebooker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class orderadapter extends RecyclerView.Adapter<orderadapter.MyViewHolder> {


    Context context;
    ArrayList<orderModel> list;
    String num;
    Activity activity;

    public orderadapter(Context context, ArrayList<orderModel> list, String num, Activity activity) {
        this.context = context;
        this.list = list;
        this.num = num;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orderedcakecard, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.img);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice() + " ₹");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, cakeOrder.class);
                i.putExtra("image", list.get(position).getImage());
                i.putExtra("name", list.get(position).getName());
                i.putExtra("price", list.get(position).getPrice());
                i.putExtra("desc", list.get(position).getDesc());
                i.putExtra("phone", num);
                i.putExtra("stat","po");
                context.startActivity(i);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, cakeOrder.class);
                i.putExtra("image", list.get(position).getImage());
                i.putExtra("name", list.get(position).getName());
                i.putExtra("price", list.get(position).getPrice());
                i.putExtra("desc", list.get(position).getDesc());
                i.putExtra("phone", num);
                i.putExtra("stat","co");
                context.startActivity(i);
                activity.finish();
            }
        });

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = list.get(position).getName();
                removeAt(position,name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price,edit;
        ImageView img;
        MaterialButton cancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cakeimage);
            name = itemView.findViewById(R.id.cakename);
            price = itemView.findViewById(R.id.cakeprice);
            cancel = itemView.findViewById(R.id.cancelbtn);
            edit = itemView.findViewById(R.id.edit);
        }
    }
    private void removeAt(int position,String name) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
        delete(name);
    }



    private void delete(String name) {
        // creating a variable for our Database
        // Reference for Firebase.
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference("user").child(num).child("Order");
        Query query=dbref.child(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // remove the value at reference
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
