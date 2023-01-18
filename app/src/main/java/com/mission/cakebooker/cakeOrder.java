package com.mission.cakebooker;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class cakeOrder extends AppCompatActivity {


    ImageView imageView;
    TextView name, price, desc;
    MaterialButton btn;
    String img, n, d, text;
    TextInputEditText textFieeld;
    int p;
    String num;
    String stat;
    MaterialCardView card4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake_order);

        imageView = findViewById(R.id.image);
        name = findViewById(R.id.cname);
        price = findViewById(R.id.cprice);
        desc = findViewById(R.id.desc);
        textFieeld = findViewById(R.id.textFieeld);
        btn = findViewById(R.id.orderbtn);
        card4 = findViewById(R.id.card4);
        img = getIntent().getStringExtra("image");
        n = getIntent().getStringExtra("name");
        d = getIntent().getStringExtra("desc");
        p = getIntent().getIntExtra("price", 0);
        stat = getIntent().getStringExtra("stat");
        if(!stat.equals("co")) {
            btn.setVisibility(View.GONE);
            textFieeld.setVisibility(View.GONE);
            card4.setVisibility(View.GONE);
        }
        Glide.with(this).load(img).into(imageView);
        name.setText(n);
        price.setText(String.valueOf(p) + " ₹");
        desc.setText(d);
        num = getIntent().getStringExtra("phone");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(cakeOrder.this)
                        .setMessage("Are You Sure You Want To Place An Order?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ProgressDialog pg = new ProgressDialog(cakeOrder.this);
                                pg.setMessage("Please Wait");
                                pg.show();
                                text = textFieeld.getText().toString();
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user/" + num);
                                HashMap<String, Object> cake = new HashMap<>();
                                cake.put("name", n);
                                cake.put("price", p);
                                cake.put("desc", d);
                                cake.put("image", img);
                                cake.put("text", text);
                                cake.put("status", 1);
                                databaseReference.child("Order").child(n).setValue(cake);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        pg.dismiss();
                                        btn.setVisibility(View.GONE);
                                        new AlertDialog.Builder(cakeOrder.this)
                                                .setTitle("Order Placed")
                                                .setMessage("Your Order is notified you will get response with in 4 hours")

                                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                                // The dialog is automatically dismissed when a dialog button is clicked.
                                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Continue with delete operationd
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .show();
                                    }
                                }, 1500);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();

            }
        });


    }


}