package com.mission.cakebooker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class myadapter extends RecyclerView.Adapter<myadapter.MyViewHolder> {


    Context context;
    ArrayList<CakeModel> list;
    String num;

    public myadapter(Context context, ArrayList<CakeModel> list, String num) {
        this.context = context;
        this.list = list;
        this.num = num;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cakecard, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CakeModel m = list.get(position);
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
                i.putExtra("stat","co");
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cakeimage);
            name = itemView.findViewById(R.id.cakename);
            price = itemView.findViewById(R.id.cakeprice);
        }
    }
}
