package com.mission.cakebooker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class otp_screen extends AppCompatActivity {


    TextInputEditText otp;
    AppCompatButton btn;
    String finalOtp;
    String getOtpbackend, phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);

        otp = findViewById(R.id.otp);
        btn = findViewById(R.id.btn);

        getOtpbackend = getIntent().getStringExtra("backendotp");
        phone = getIntent().getStringExtra("phone");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalOtp = otp.getText().toString();
                if (!finalOtp.isEmpty()) {
                    if (getOtpbackend != null) {
                        btn.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getOtpbackend, finalOtp);
                        signInTheUserByCredential(phoneAuthCredential);
                    }
                } else {
                    Toast.makeText(otp_screen.this, "Enter OTP", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void signInTheUserByCredential(PhoneAuthCredential credential) {
        ProgressDialog pg = new ProgressDialog(otp_screen.this);
        pg.setMessage("Please Wait");
        pg.show();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                btn.setVisibility(View.VISIBLE);
                if (task.isSuccessful()) {
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user");
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(phone)) {
                                pg.dismiss();
                                Intent intent = new Intent(getApplicationContext(), homeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                pg.dismiss();
                                Intent intent = new Intent(getApplicationContext(), registrationForm.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("phone",phone);
                                startActivity(intent);
                                Toast.makeText(otp_screen.this, "register", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            pg.dismiss();
                        }
                    });

                } else {
                    pg.dismiss();
                    Toast.makeText(otp_screen.this, "Wrong OTP", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}