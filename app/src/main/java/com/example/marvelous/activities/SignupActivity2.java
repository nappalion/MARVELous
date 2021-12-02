package com.example.marvelous.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.marvelous.R;

public class SignupActivity2 extends AppCompatActivity {

    ImageButton btnProfile;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        getSupportActionBar().hide();

        btnContinue = findViewById(R.id.btnContinue);
        btnProfile = findViewById(R.id.btnProfile);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set profile to back4app user
                goSignupActivity3();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity2.this , "Add profile picture.", Toast.LENGTH_SHORT).show();
                // set up adding user profile
            }
        });
    }



    private void goSignupActivity3() {
        Intent i = new Intent(this, SignupActivity3.class);
        startActivity(i);
        finish();
    }
}