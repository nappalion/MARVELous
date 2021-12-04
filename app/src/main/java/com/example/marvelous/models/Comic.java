package com.example.marvelous.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Comic {

    public String id;
    public String title;
    public String description;
    public String url;
    public String series;

    public static Comic fromJson(JSONObject jsonObject) throws JSONException {
        Comic comic = new Comic();
        comic.id = jsonObject.getString("id");
        comic.title = jsonObject.getString("title");
        comic.description = jsonObject.getString("description");
        comic.url = jsonObject.getJSONObject("thumbnail").getString("path") + "." + jsonObject.getJSONObject("thumbnail").getString("extension");
        comic.series = jsonObject.getJSONObject("series").getString("name");
        return comic;
    }

    public static List<Comic> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Comic> comics = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            comics.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return comics;
    }

}