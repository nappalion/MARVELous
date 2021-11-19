package com.example.marvelous.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marvelous.R;
import com.example.marvelous.models.UserComic;

import java.util.List;

public class LibraryComicsAdapter extends RecyclerView.Adapter<LibraryComicsAdapter.ViewHolder> {

    private List<UserComic> mUserComics;

    public LibraryComicsAdapter(List<UserComic> userComics) {
        this.mUserComics = userComics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the data model based on position
        UserComic usercomic = mUserComics.get(position);

        // set item views based on views in data model
    }

    @Override
    public int getItemCount() {
        return mUserComics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvStatus;
        public RecyclerView rvComics;
        public ImageView ivArrow;

        public ViewHolder(View itemView) {
            super(itemView);

            tvStatus = itemView.findViewById(R.id.tvStatus);
            rvComics = itemView.findViewById(R.id.rvComics);
            ivArrow = itemView.findViewById(R.id.ivArrow);
        }
    }
}
