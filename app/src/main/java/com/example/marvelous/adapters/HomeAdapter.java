package com.example.marvelous.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.model.Headers;
import com.example.marvelous.R;
import com.example.marvelous.models.Comic;
import com.example.marvelous.models.UserComic;
import com.example.marvelous.models.utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    public static final String TAG = "HomeAdapter";
    RequestQueue queue;

    String base_url ="https://gateway.marvel.com:443/v1/public/";
    String PUBLIC_KEY = "685c68298103e0c7ba2d074f58a4619b";
    String PRIVATE_KEY = "3b9af090798ac35d9d1176e212ed8c9451c30c9c";

    String POPULAR_COMIC = "events=310&orderBy=-onsaleDate&limit=100&offset=0";
    String NEW_COMIC_KEY = "orderBy=-onsaleDate&limit=50&offset=0";
    String RECOMMENDED_COMIC = "events=309&orderBy=-onsaleDate&limit=100&offset=0";
    String TOP_RATED_COMIC = "events=305&orderBy=-onsaleDate&limit=100&offset=0";
    String queryString;

    Date date = new Date();
    long timeMilli = date.getTime();
    String TS = (String.valueOf(timeMilli));

    String hashConvert = TS + PRIVATE_KEY + PUBLIC_KEY;
    String HASH = utils.generateMD5Hash(hashConvert);


    public interface OnClickListener {
        void onItemClicked(int position);
    }

    private List<String> mPossStatuses;
    HomeAdapter.OnClickListener clickListener;

    public HomeAdapter(List<String> possStatus, Context context, HomeAdapter.OnClickListener clickListener) {
        this.mPossStatuses = possStatus;
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View HomeView = inflater.inflate(R.layout.item_library, parent, false);

        // Return a new holder instance
        return new ViewHolder(HomeView);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {

        // LibraryComics RV Setup
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rvComics.setLayoutManager(layoutManager);
        holder.rvComics.setHasFixedSize(true);


        // get the data model based on position
        String possStatus = mPossStatuses.get(position);
        List<Comic> comics = new ArrayList<>();
        Log.i(TAG, " called " + possStatus);
        switch (possStatus) {
            case "New Comics":
                queryString = NEW_COMIC_KEY;
                break;
            case "Popular":
                queryString = POPULAR_COMIC;
                break;
            case "Recommended":
                queryString = RECOMMENDED_COMIC;
                break;
            case "Top Rated":
                queryString = TOP_RATED_COMIC;
                break;
            default: queryString = TOP_RATED_COMIC;
                break;
        }
        queryComic(comics, queryString);

        holder.bind(possStatus);

        // Attach HomeComicsAdapter to RV
        HomeComicsAdapter HomeComicsAdapter = new HomeComicsAdapter(comics, holder.rvComics.getContext());
        holder.rvComics.setAdapter(HomeComicsAdapter);
        HomeComicsAdapter.notifyDataSetChanged();
    }

    public void queryComic(List<Comic> comics, String queryString){
//        Log.i("URL", url);
        String url = base_url + "comics?"+ queryString + "&ts=" + TS + "&apikey=" + PUBLIC_KEY + "&hash=" + HASH;
        queue = Volley.newRequestQueue(this.context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject object){
                try {
                    //HomeComicsAdapter.clear();
                    Log.i(TAG, "successfully connected" + object.toString());
                    JSONArray results = object.getJSONObject("data").getJSONArray("results");
                    comics.addAll(Comic.fromJsonArray(results));
                } catch (JSONException e) {
                    Log.e(TAG, "Json exception", e);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "connection failed, an error occurred");
            }
        });
        queue.add(request);
    }

    @Override
    public int getItemCount() {
        return mPossStatuses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvStatus;
        public RecyclerView rvComics;
        public ImageView ivArrow;
        public LinearLayout libContainer;

        public ViewHolder(View itemView) {
            super(itemView);

            tvStatus = itemView.findViewById(R.id.tvStatus);
            rvComics = itemView.findViewById(R.id.rvComics);
            ivArrow = itemView.findViewById(R.id.ivArrow);
            libContainer = itemView.findViewById(R.id.libContainer);
        }

        // Update the view inside of the view holder with this data
        public void bind(String possStatus) {
            // Setting text for status
            tvStatus.setText(possStatus);

            // Added OnClickListener for each tvStatus
            libContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());

                    // expand/collapse RV when clicked
                    if (rvComics.getVisibility() == View.VISIBLE) {
                        rvComics.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.drawable.arrow_up);
                    }
                    else
                    {
                        rvComics.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.drawable.arrow_down);
                    }
                }
            });
        }
    }
}
