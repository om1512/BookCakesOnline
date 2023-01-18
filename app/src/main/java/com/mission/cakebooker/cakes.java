package com.mission.cakebooker;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class cakes extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    myadapter myadapter;
    ArrayList<CakeModel> list;
    String num;
    FloatingActionButton btn;

    String ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cakes);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        Glide.with(this).load("http://goo.gl/gEgYUd").into(img1);


        num = getIntent().getStringExtra("phone");
        ct = getIntent().getStringExtra("ct");
        btn = findViewById(R.id.order);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(cakes.this, placedOrder.class);
                i.putExtra("phone", num);
                startActivity(i);
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Cakes/"+ct);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myadapter = new myadapter(this, list, num);
        recyclerView.setAdapter(myadapter);
        ProgressDialog pg = new ProgressDialog(cakes.this);
        pg.setMessage("Please Wait");
        pg.show();
        databaseReference.addValueEventListener(new ValueEventListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CakeModel m = dataSnapshot.getValue(CakeModel.class);
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