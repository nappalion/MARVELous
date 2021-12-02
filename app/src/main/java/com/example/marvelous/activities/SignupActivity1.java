package com.example.marvelous.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marvelous.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity1 extends AppCompatActivity {

    Button btnCreate;
    EditText etEmail;
    EditText etUsername;
    EditText etPassword;
    EditText etConfirm;

    String email;
    String username;
    String password;

    public static final String TAG = "SignupActivity1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        btnCreate = findViewById(R.id.btnCreate);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirm);

        // gets username typed from last activity.
        etUsername.setText(getIntent().getStringExtra("username"));

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checks if email contains more than one @ and ends with .com, .org, .net
                if (etEmail.getText().toString().indexOf('@') == etEmail.getText().toString().lastIndexOf('@')
                    && (etEmail.getText().toString().endsWith(".com")  ||
                        etEmail.getText().toString().endsWith(".org") ||
                        etEmail.getText().toString().endsWith(".net") ) ) {
                    email = etEmail.getText().toString();
                }
                else {
                    Toast.makeText(SignupActivity1.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // checks if username is empty
                if (!etUsername.getText().toString().isEmpty()) {
                    username = etUsername.getText().toString();
                }
                else {
                    Toast.makeText(SignupActivity1.this, "Invalid username.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // checks if password is equal to confirmed password
                if (etPassword.getText().toString().equals(etConfirm.getText().toString())) {
                    password = etPassword.getText().toString();
                }
                else {
                    Toast.makeText(SignupActivity1.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }
                createAccount(email, username, password);
            }
        });

    }

    private void createAccount(String email, String username, String password) {
        Log.i(TAG, "Attempting to create user account: " + username);
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    goSignupActivity2();
                    Toast.makeText(SignupActivity1.this, "Success!", Toast.LENGTH_SHORT).show();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG, "Issue with Signup.", e);
                    Toast.makeText(SignupActivity1.this, "Issue with Signup.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goSignupActivity2() {
        Intent i = new Intent(this, SignupActivity2.class);
        startActivity(i);
        finish();
    }
}