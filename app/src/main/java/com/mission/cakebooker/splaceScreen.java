package com.mission.cakebooker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splaceScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentUser != null) {
                    Intent i = new Intent(splaceScreen.this, homeActivity.class);
                    i.putExtra("phone",currentUser.getPhoneNumber().substring(3));
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(splaceScreen.this, PhoneVarification.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 4000);
    }
}