package com.example.marvelous.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.Target;
import com.example.marvelous.R;
import com.example.marvelous.activities.ComicDetailActivity;
import com.example.marvelous.models.Comic;
import com.example.marvelous.models.UserComic;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

public class HomeComicsAdapter extends RecyclerView.Adapter<HomeComicsAdapter.ViewHolder> {

    private static List<Comic> comics;

    Context context;
    public static final String TAG = "HomeComicsAdapter";

    public HomeComicsAdapter(List<Comic> comics, Context mContext) {
        this.comics = comics;
        this.context = mContext;
        Log.i(TAG, "Constructor called: " + String.valueOf(comics.size()));
    }

    @NonNull
    @Override
    public HomeComicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        Log.i(TAG, "onCreateViewHolder called");
        Log.i(TAG, String.valueOf(comics.size()));

        // Inflate the LibraryComics item layout
        View comicView = LayoutInflater.from(context).inflate(R.layout.item_homecomics, parent, false);

        return new HomeComicsAdapter.ViewHolder(comicView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeComicsAdapter.ViewHolder childHolder, int position) {

        Log.i(TAG, "onBindViewHolder called");
        // get the data model based on position
        Comic comic = comics.get(position);

        // set item views based on views in data model
        childHolder.bind(comic);
    }

    @Override
    public int getItemCount() {
        // Log.i(TAG, "getItemCount called: " + String.valueOf(mUserComics.size()));
        return comics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivComic;
        public LinearLayout comicContainer;
//        public TextView tvRating;
        public TextView tvTitle;
        public ImageView ivBookmark;

        public ViewHolder(View itemView) {
            super(itemView);

            ivComic = itemView.findViewById(R.id.ivComic);
            comicContainer = itemView.findViewById(R.id.comicContainer);
//            tvRating = itemView.findViewById(R.id.tvRating);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivBookmark = itemView.findViewById(R.id.ivBookmark);
        }

        public void bind(Comic comic) {
            Log.i(TAG, comic.getUrl());
//            tvRating.setText("N/A");
            tvTitle.setText(comic.getTitle());
            Glide.with(context).load(comic.url)
                    .transform(new CenterInside(), new RoundedCorners(35)).into(ivComic);

            ivComic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ComicDetailActivity.class);
                    intent.putExtra("comic", Parcels.wrap(comic));
                    context.startActivity(intent);
                }
            });
            ivBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText( v.getContext(), "Comic Id: " + String.valueOf(comic.getId()), Toast.LENGTH_SHORT).show();
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    saveComic(comic, currentUser);
                }
            });
        }
    }

    public void clear() {
        comics.clear();
        notifyDataSetChanged();
    }

    private void saveComic(Comic comic, ParseUser currentUser) {
        ImageView temp = null;
        UserComic UserComic = new UserComic();
        UserComic.setComicId(comic.getId());
        UserComic.setIsFavorite(true);
        UserComic.setUserId(currentUser);
        UserComic.setPageNumber(1);
        UserComic.setStatus("Reading");
        UserComic.setComicImagelink(comic.getUrl());

        UserComic.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if ( e != null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.i(TAG, "Comic save was successful!!");
                Toast.makeText(getContext(), "Save successfully!", Toast.LENGTH_LONG).show();
            };
        });
    }
    public Context getContext() {
        return context;
    }
}

