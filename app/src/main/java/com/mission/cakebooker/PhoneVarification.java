package com.mission.cakebooker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneVarification extends AppCompatActivity {


    AppCompatButton getotpbutton;
    TextInputEditText enternumber;
    LinearProgressIndicator progressIndicator;
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_varification);
        enternumber = findViewById(R.id.phone);
        getotpbutton = findViewById(R.id.btn);
        progressIndicator = findViewById(R.id.progressBar);
        text1 = findViewById(R.id.sendingotp);

        getotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!enternumber.getText().toString().trim().isEmpty()) {
                    if ((enternumber.getText().toString().trim()).length() == 10) {

                        progressIndicator.setVisibility(View.VISIBLE);
                        getotpbutton.setVisibility(View.INVISIBLE);
                        text1.setVisibility(View.VISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enternumber.getText().toString(), 60, TimeUnit.SECONDS, PhoneVarification.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressIndicator.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                        text1.setVisibility(View.GONE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressIndicator.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                        text1.setVisibility(View.GONE);

                                        Toast.makeText(PhoneVarification.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressIndicator.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                        text1.setVisibility(View.GONE);

                                        Intent intent = new Intent(PhoneVarification.this, otp_screen.class);
                                        intent.putExtra("backendotp", backendotp);
                                        intent.putExtra("phone",enternumber.getText().toString());
                                        startActivity(intent);
                                    }
                                }
                        );
                        //   Intent intent=new Intent(MainActivity.this,varificationotp.class);
                        // intent.putExtra("mobile",enternumber.getText().toString());
                        // startActivity(intent);
                    } else {
                        Toast.makeText(PhoneVarification.this, "please enter the correct number", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(PhoneVarification.this, "Fill Detail", Toast.LENGTH_LONG).show();
                }
            }

        });



    }
}