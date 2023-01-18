package com.mission.cakebooker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class homeActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    CheckBox birth, wedding, anniver, fifth, party, win, other;
    String num;

    boolean selectSatatus;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        selectSatatus = false;
        birth = findViewById(R.id.birthday);
        wedding = findViewById(R.id.wedding);
        anniver = findViewById(R.id.anniver);
        fifth = findViewById(R.id.party);
        party = findViewById(R.id.cel);
        win = findViewById(R.id.win);
        other = findViewById(R.id.other);
        num = getIntent().getStringExtra("phone");
        birth.setOnCheckedChangeListener(this);
        wedding.setOnCheckedChangeListener(this);
        anniver.setOnCheckedChangeListener(this);
        fifth.setOnCheckedChangeListener(this);
        party.setOnCheckedChangeListener(this);
        win.setOnCheckedChangeListener(this);
        other.setOnCheckedChangeListener(this);


        findViewById(R.id.nextbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectSatatus) {
                    Intent i = new Intent(homeActivity.this, cakes.class);
                    i.putExtra("phone", num);
                    i.putExtra("ct", category);
                    startActivity(i);
                } else {
                    Toast.makeText(homeActivity.this, "Select Any Category", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.birthday:
                selectSatatus = true;
                category = "Birthday";
                wedding.setChecked(false);
                anniver.setChecked(false);
                party.setChecked(false);
                fifth.setChecked(false);
                other.setChecked(false);
                win.setChecked(false);

                break;
            case R.id.wedding:
                selectSatatus = true;
                category = "Wedding";
                birth.setChecked(false);
                anniver.setChecked(false);
                party.setChecked(false);
                win.setChecked(false);
                fifth.setChecked(false);
                other.setChecked(false);
                break;
            case R.id.anniver:
                selectSatatus = true;
                category = "anniver";
                birth.setChecked(false);
                wedding.setChecked(false);
                party.setChecked(false);
                win.setChecked(false);
                fifth.setChecked(false);
                other.setChecked(false);
                break;

            case R.id.party:
                selectSatatus = true;
                category = "Party";
                birth.setChecked(false);
                wedding.setChecked(false);
                anniver.setChecked(false);
                win.setChecked(false);
                party.setChecked(false);
                other.setChecked(false);
                break;
            case R.id.win:
                selectSatatus = true;
                category = "win";
                birth.setChecked(false);
                wedding.setChecked(false);
                anniver.setChecked(false);
                party.setChecked(false);
                fifth.setChecked(false);
                other.setChecked(false);
                break;

            case R.id.cel:
                selectSatatus = true;
                category = "Party";
                birth.setChecked(false);
                wedding.setChecked(false);
                anniver.setChecked(false);
                win.setChecked(false);
                fifth.setChecked(false);
                other.setChecked(false);
                break;


            case R.id.other:
                selectSatatus = true;
                category = "other";
                birth.setChecked(false);
                wedding.setChecked(false);
                anniver.setChecked(false);
                party.setChecked(false);
                fifth.setChecked(false);
                win.setChecked(false);
                break;
        }
    }
}