package com.example.marvelous.models;

import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("UserComic")
public class UserComic extends ParseObject {
    // can access status of comics the user has interacted with here

    public static final String KEY_COMICID = "comicId";
    public static final String KEY_USERID = "userId";
    public static final String KEY_ISFAVORITE = "isFavorite";
    public static final String KEY_PAGENUMBER = "pageNumber";
    public static final String KEY_STATUS = "status";
    public static final String KEY_REVIEWNUM = "reviewNum";
    public static final String KEY_REVIEWPOST = "reviewPost";
    public static final String KEY_SAMPLEIMAGE = "sampleImage";
    public static final String KEY_TOTALPAGES = "totalPages";

    public int getComicId() {
        return getInt(KEY_COMICID);
    }

    public void setComicId(int comicId) {
        put(KEY_COMICID, comicId);
    }

    public ParseUser getUserId() {
        return getParseUser(KEY_USERID);
    }

    public void setUserId(ParseUser userId) {
        put(KEY_USERID, userId);
    }

    public boolean getIsFavorite() {
        return getBoolean(KEY_ISFAVORITE);
    }

    public void setIsFavorite(boolean isFavorite) {
        put(KEY_ISFAVORITE, isFavorite);
    }

    public int getPageNumber() {
        return getInt(KEY_PAGENUMBER);
    }

    public void setPageNumber(int pageNumber) {
        put(KEY_PAGENUMBER, pageNumber);
    }

    public String getStatus() {
        return getString(KEY_STATUS);
    }

    public void setStatus(String status) {
        put(KEY_STATUS, status);
    }

    public int getReviewNum() {
        return getInt(KEY_REVIEWNUM);
    }

    public void setReviewNum(int reviewNum) {
        put(KEY_REVIEWNUM, reviewNum);
    }

    public String getReviewPost() {
        return getString(KEY_REVIEWPOST);
    }

    public void setReviewPost(String reviewPost) {
        put(KEY_REVIEWPOST, reviewPost);
    }

    public ParseFile getSampleImage() {
        return getParseFile(KEY_SAMPLEIMAGE);
    }

    public void setSampleImage(ParseFile sampleImage) {
        put(KEY_SAMPLEIMAGE, sampleImage);
    }

    public int getTotalPages() {
        return getInt(KEY_TOTALPAGES);
    }

    public void setTotalPages(int totalPages) {
        put(KEY_TOTALPAGES, totalPages);
    }
}
