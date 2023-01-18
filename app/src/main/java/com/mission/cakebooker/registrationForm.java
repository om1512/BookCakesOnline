package com.mission.cakebooker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class registrationForm extends AppCompatActivity {

    TextInputLayout phone;
    TextInputEditText ph, userName, uEmail;
    String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
        num = getIntent().getStringExtra("phone");

        userName = findViewById(R.id.uname);
        uEmail = findViewById(R.id.uemail);
        phone = findViewById(R.id.phone);
        ph = findViewById(R.id.ph);
        Button submit = findViewById(R.id.submitotpbtn);
        phone.setEnabled(false);

        ph.setText(num);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!userName.getText().toString().isEmpty()){
                    if(!uEmail.getText().toString().isEmpty()){

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("user").child(num);
                        HashMap<String, Object> user = new HashMap<>();
                        user.put("name", Objects.requireNonNull(userName.getText()).toString());
                        user.put("email", Objects.requireNonNull(uEmail.getText()).toString());
                        user.put("phone", num);
                        myRef.setValue(user);
                        Intent i =new Intent(registrationForm.this,homeActivity.class);
                        i.putExtra("phone",num);
                        startActivity(i);

                    }else{
                        Toast.makeText(registrationForm.this, "Add Email", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(registrationForm.this, "Add Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}