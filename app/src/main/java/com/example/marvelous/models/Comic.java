package com.example.marvelous.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Comic {

    public int id2;
    public String id;
    public String title;
    public String description;
    public String url;
    public String series;
    public int eventsNum;

    public static Comic fromJson(JSONObject jsonObject) throws JSONException {
        Comic comic = new Comic();
        comic.id = jsonObject.getString("id");
        comic.title = jsonObject.getString("title");
        comic.description = jsonObject.getString("description");
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

    public static Comic singleFromJson(JSONObject jsonObject) throws JSONException {
        Comic comic = new Comic();
        comic.id = jsonObject.getJSONObject("data").getJSONArray("results").getJSONObject(0).getString("id");
        comic.title = jsonObject.getJSONObject("data").getJSONArray("results").getJSONObject(0).getString("title");
        comic.description = jsonObject.getJSONObject("data").getJSONArray("results").getJSONObject(0).getString("description");
        comic.url = jsonObject.getJSONObject("data").getJSONArray("results").getJSONObject(0).getJSONObject("thumbnail").getString("path") + "." + jsonObject.getJSONObject("data").getJSONArray("results").getJSONObject(0).getJSONObject("thumbnail").getString("extension");
        comic.series = jsonObject.getJSONObject("data").getJSONArray("results").getJSONObject(0).getJSONObject("series").getString("name");
        return comic;
    }


    public String getSeries() {
        return series;
    }
    public int getId() {
        return id2;
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
