package com.example.marvelous.models;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Register your parse models
        ParseObject.registerSubclass(Comic.class);
        ParseObject.registerSubclass(UserComic.class);

        //set applicationID and sever
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("qQJl812aEQHgmlkRjE1IRB8d5pbgNS8u3km5866N")
                .clientKey("GMiKvU6MFy7gSI5TgR0cdD0Dpf35BolOF2smhiQZ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
