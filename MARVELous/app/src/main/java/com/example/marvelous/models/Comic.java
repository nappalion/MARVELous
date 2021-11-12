package com.example.marvelous.models;

import androidx.annotation.NonNull;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comic")
public class Comic extends ParseObject {

    /*public static final String KEY_DESCRIPTION = "Description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_COMIC = "comic";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {return getParseFile(KEY_IMAGE);}

    public ParseUser getComic(){
        return getParseUser(KEY_COMIC);
    }*/
}