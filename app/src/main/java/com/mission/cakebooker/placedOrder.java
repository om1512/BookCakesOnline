package com.mission.cakebooker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class placedOrder extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    orderadapter myadapter;
    ArrayList<orderModel> list;
    String num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        Glide.with(this).load("http://goo.gl/gEgYUd").into(img1);


        num = getIntent().getStringExtra("phone");

        databaseReference = FirebaseDatabase.getInstance().getReference("user/"+num+"/Order");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myadapter = new orderadapter(this, list, num,placedOrder.this);
        recyclerView.setAdapter(myadapter);
        ProgressDialog pg = new ProgressDialog(placedOrder.this);
        pg.setMessage("Please Wait");
        pg.show();
        databaseReference.addValueEventListener(new ValueEventListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    orderModel m = dataSnapshot.getValue(orderModel.class);
                    list.add(m);
                }
                pg.dismiss();
                myadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}