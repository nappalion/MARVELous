package com.example.marvelous.fragments;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import androidx.preference.EditTextPreference;

import android.view.View;
import android.widget.Toast;

import com.example.marvelous.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static final int PICK_IMAGE = 1;
    private EditTextPreference usernamePref;
    private Preference passwordPref;
    private EditTextPreference bioPref;
    private Preference pfpPref;
    ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usernamePref = (EditTextPreference) findPreference("etUsername");
        passwordPref = (Preference) findPreference("etPassword");
        bioPref = (EditTextPreference) findPreference("etBio");
        pfpPref = (Preference) findPreference("editPfP");

        usernamePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                currentUser.put("username",(String)newValue);
                saveUserChanges(currentUser);
                return true;
            }
        });

        bioPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue.toString().length() <= 150){
                    currentUser.put("biography", (String)newValue);
                    saveUserChanges(currentUser);
                    return true;
                }
                else{
                    Toast.makeText(getContext(), "Cannot be longer than 150 characters", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });

        passwordPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Request password change?");
                dialog.setMessage("You can change your password for security reasons or reset it if you forget it. A request will be sent to your email.");
                dialog.setCancelable(true);
                dialog.setPositiveButton("Request", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        passwordReset();
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dlg, int which)
                    {
                        dlg.cancel();
                    }
                });

                AlertDialog al = dialog.create();
                al.show();
                return false;
            }
        });

        pfpPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                return true;
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            byte[] imageBytes = convertImageToByte(imageUri);
            ParseFile imageFile = new ParseFile("profile.png",imageBytes);

            imageFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    // If successful add file to user
                    if(null == e)
                        currentUser.put("profilePic", imageFile);
                        saveUserChanges(currentUser);
                }
            });
        }
    }


    public byte[] convertImageToByte(Uri uri){
        byte[] data = null;
        try {
            ContentResolver cr = getContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void saveUserChanges(ParseUser user){
        user.saveInBackground(e -> {
            if(e==null){
                //Save successfull
                Toast.makeText(getContext(), "Save Successful", Toast.LENGTH_SHORT).show();
            }else{
                // Something went wrong while saving
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void passwordReset() {
        // An e-mail will be sent with further instructions
        currentUser.requestPasswordResetInBackground(currentUser.getEmail(), e -> {
            if (e == null) {
                // An email was successfully sent with reset instructions.
                Toast.makeText(getContext(), "An email was successfully sent with reset instructions", Toast.LENGTH_SHORT).show();

            } else {
                // Something went wrong. Look at the ParseException to see what's up.
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}