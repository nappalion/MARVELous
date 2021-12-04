package com.example.marvelous.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marvelous.R;
import com.example.marvelous.activities.ComicDetailActivity;
import com.example.marvelous.activities.ReviewActivity;
import com.example.marvelous.models.UserComic;
import com.parse.ParseFile;

import java.text.DecimalFormat;
import java.util.List;

public class LibraryComicsAdapter extends RecyclerView.Adapter<LibraryComicsAdapter.ViewHolder> {

    private List<UserComic> mUserComics;
    Context context;
    public static final String TAG = "LibraryComicsAdapter";

    public LibraryComicsAdapter(List<UserComic> userComics, Context mContext) {
        this.mUserComics = userComics;
        this.context = mContext;
        // Log.i(TAG, "Constructor called: " + String.valueOf(mUserComics.size()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Log.i(TAG, "onCreateViewHolder called");
        // Log.i(TAG, String.valueOf(mUserComics.size()));

        // Inflate the LibraryComics item layout
        View libraryView = inflater.inflate(R.layout.item_librarycomics, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(libraryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder childHolder, int position) {

        // Log.i(TAG, "onBindViewHolder called");
        // get the data model based on position
        UserComic userComic = mUserComics.get(position);

        // set item views based on views in data model
        childHolder.bind(userComic);
    }

    @Override
    public int getItemCount() {
        // Log.i(TAG, "getItemCount called: " + String.valueOf(mUserComics.size()));
        return mUserComics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivComic;
        public LinearLayout comicContainer;
        public TextView tvRating;
        public TextView tvBookmark;

        public ViewHolder(View itemView) {
            super(itemView);

            ivComic = itemView.findViewById(R.id.ivComic);
            comicContainer = itemView.findViewById(R.id.comicContainer);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvBookmark = itemView.findViewById(R.id.tvBookmark);
        }

        public void bind(UserComic userComic) {
            ParseFile sampleImage = userComic.getSampleImage();
            // Log.i(TAG, "Bind called");
            if (sampleImage != null) {
                Glide.with(context).load(sampleImage.getUrl()).into(ivComic);
                // Log.i(TAG, "ivComic");
            }
            else {
                Log.i(TAG, "No sample image!");
            }

            // Log.i(TAG, userComic.getReviewNum().toString());
            // Makes tvRating follow 0.0 format
            DecimalFormat df = new DecimalFormat("0.0");
            tvRating.setText(df.format(userComic.getReviewNum()));

            String bookmark = userComic.getPageNumber()
                                + "/" + userComic.getTotalPages();
            tvBookmark.setText(bookmark);

            // Add OnClickListener for each ivComic
            comicContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText( v.getContext(), "Comic Id: " + String.valueOf(userComic.getComicId()), Toast.LENGTH_SHORT).show();

                    // Review Activity
                    Intent i = new Intent (context, ComicDetailActivity.class);
                    i.putExtra("sampleImage", sampleImage.getUrl());
                    context.startActivity(i);
                }
            });
        }
    }

    public void clear() {
        mUserComics.clear();
        notifyDataSetChanged();
    }
}
