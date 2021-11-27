package com.example.marvelous.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.marvelous.R;
import com.example.marvelous.fragments.HomeFragment;
import com.example.marvelous.fragments.LibraryFragment;
import com.example.marvelous.fragments.ProfileFragment;
import com.example.marvelous.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private MenuItem mitLogout;
    private MenuItem mitAdd;
    private BottomNavigationView bottomNavigationView;
    private int menuToChoose = R.menu.menu_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int result = RESULT_OK;

        mitLogout = findViewById(R.id.mitLogout);
        mitAdd = findViewById(R.id.mitAdd);
        bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        //Toast.makeText(MainActivity.this , "Home", Toast.LENGTH_SHORT).show();
                        changeActionBar(R.menu.menu_main, "Home");
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_search:
                        //Toast.makeText(MainActivity.this, "Compose", Toast.LENGTH_SHORT).show();
                        changeActionBar(R.menu.menu_main, "Search");
                        fragment = new SearchFragment();
                        break;
                    case R.id.action_library:
                        //Toast.makeText(MainActivity.this, "Compose", Toast.LENGTH_SHORT).show();
                        changeActionBar(R.menu.menu_library, "My Library");
                        invalidateOptionsMenu();
                        fragment = new LibraryFragment();
                        break;
                    case R.id.action_profile:
                    default:
                        //Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        changeActionBar(R.menu.menu_main, "MARVELous");
                        fragment = new ProfileFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }

    public void changeActionBar(int menu, String title) {
        menuToChoose = menu;
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(menuToChoose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mitLogout){
            Toast.makeText(MainActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
            ParseUser.logOut();
            goLoginActivity();
            return true;
        }

        if (item.getItemId() == R.id.mitSettings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.mitAdd) {
            Toast.makeText(MainActivity.this, "Add Item!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}