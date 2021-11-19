package com.example.marvelous.models;

import com.parse.ParseClassName;
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

    public String getComicId() {
        return getString(KEY_COMICID);
    }

    public void setComicId(int comicId) {
        put(KEY_COMICID, comicId);
    }

    public String getUserId() {
        return getString(KEY_USERID);
    }

    public void setUserId(ParseUser userId) {
        put(KEY_USERID, userId);
    }

    public String getIsFavorite() {
        return getString(KEY_ISFAVORITE);
    }

    public void setIsFavorite(boolean isFavorite) {
        put(KEY_ISFAVORITE, isFavorite);
    }

    public String getPageNumber() {
        return getString(KEY_PAGENUMBER);
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

    public String getReviewNum() {
        return getString(KEY_REVIEWNUM);
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
}
