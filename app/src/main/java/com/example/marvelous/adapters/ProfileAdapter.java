package com.example.marvelous.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.marvelous.R;
import com.example.marvelous.models.UserComic;

import java.util.List;

public class ProfileAdapter extends LibraryComicsAdapter{

    public ProfileAdapter(List<UserComic> userComics, Context mContext) {
        super(userComics, mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_comics, parent, false);
        return new ViewHolder(view);
    }

}
