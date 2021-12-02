package com.example.marvelous.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.marvelous.R;
import com.parse.ParseUser;

public class SignupActivity3 extends AppCompatActivity {


    Button btnContinue;
    EditText etBio;
    String bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);

        getSupportActionBar().hide();

        btnContinue = findViewById(R.id.btnContinue);
        etBio = findViewById(R.id.etBio);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bio = etBio.getText().toString();
                ParseUser.getCurrentUser().put("biography", bio);
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}