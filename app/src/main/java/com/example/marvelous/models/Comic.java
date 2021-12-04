package com.example.marvelous.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Comic {

    public static final String TAG = "Comic Model";
    public int id;
    public String title;
    public String description;
    public String url;
    public String series;
    public int eventsNum;

    //empty constructor for Parcel
    public Comic(){}

    public static Comic fromJson(JSONObject jsonObject) throws JSONException {
        Comic comic = new Comic();
        comic.id = jsonObject.getInt("id");
        comic.title = jsonObject.getString("title");
        comic.description = jsonObject.getString("description");
        Log.e(TAG, jsonObject.getJSONObject("thumbnail").getString("path") + "." + jsonObject.getJSONObject("thumbnail").getString("extension"));
        comic.url = jsonObject.getJSONObject("thumbnail").getString("path") + "." + jsonObject.getJSONObject("thumbnail").getString("extension");
        comic.series = jsonObject.getJSONObject("series").getString("name");
        comic.eventsNum = jsonObject.getJSONObject("events").getInt("available");
        return comic;
    }

    public static List<Comic> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Comic> comics = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            comics.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return comics;
    }

    public String getSeries() {
        return series;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}