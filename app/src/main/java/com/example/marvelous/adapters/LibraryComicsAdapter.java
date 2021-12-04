package com.example.marvelous.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.marvelous.R;
import com.example.marvelous.activities.ComicDetailActivity;
import com.example.marvelous.activities.ReviewActivity;
import com.example.marvelous.models.Comic;
import com.example.marvelous.models.UserComic;
import com.parse.ParseFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;

public class LibraryComicsAdapter extends RecyclerView.Adapter<LibraryComicsAdapter.ViewHolder> {

    private final List<UserComic> mUserComics;
    Context context;
    Comic comic;
    List<Comic> comicsList = new ArrayList<>();
    boolean finishedLoading;
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
        return new ViewHolder(libraryView);
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

            // MARVEL API CODE
            final String PUBLIC_KEY = "685c68298103e0c7ba2d074f58a4619b";
            final String PRIVATE_KEY = "3b9af090798ac35d9d1176e212ed8c9451c30c9c";

            Date date = new Date();
            long timeMilli = date.getTime();
            String TS = (String.valueOf(timeMilli));
            String hashConvert = TS + PRIVATE_KEY + PUBLIC_KEY;
            String HASH = md5(hashConvert);
            int comicID = userComic.getComicId();

            String base_url ="https://gateway.marvel.com:443/v1/public/";

            RequestQueue queue = Volley.newRequestQueue(context);
            String url = base_url + "comics" + "/" + comicID + "?ts=" + TS + "&apikey=" + PUBLIC_KEY + "&hash=" + HASH;

            Log.i("URL", url);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject object) {
                    //String copyright = null;
                    try {
                        comic = Comic.singleFromJson(object);
                        comicsList.add(comic);
                        Glide.with(context)
                                .load(comic.url)
                                .into(ivComic);
                        finishedLoading = true;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", "an error occured");
                }
            });
            queue.add(request);
            // END MARVEL API CODE


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
                if (finishedLoading){
                    for (int i = 0; i < comicsList.size(); i++){
                        if (userComic.getComicId() == Integer.parseInt((comicsList.get(i).id))){
                            // Review Activity
                            Comic comic1 = comicsList.get(i);
                            Intent intent = new Intent(v.getContext(), ComicDetailActivity.class);
                            intent.putExtra("comic", Parcels.wrap(comic1));
                            intent.putExtra("userComic", Parcels.wrap(userComic));
                            context.startActivity(intent);
                        }
                    }
                }
            }
        });

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        mUserComics.clear();
        notifyDataSetChanged();
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & aMessageDigest));
                while (h.length() < 2)
                    h.insert(0, "0");
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
