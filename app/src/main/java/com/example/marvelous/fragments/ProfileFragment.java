package com.example.marvelous.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.marvelous.R;
import com.example.marvelous.adapters.LibraryComicsAdapter;
import com.example.marvelous.models.UserComic;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private ImageView ivProfilePic;
    private TextView tvUsername;
    private TextView tvBio;
    private TextView tvReadingNum;
    private TextView tvFavoriteNum;

    private int readingCount = 0;
    private int favoriteCount = 0;

    List<UserComic> userComics;
    LibraryComicsAdapter comicsAdapter;
    private RecyclerView rvComics;

    private SwipeRefreshLayout swipeContainer;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvComics = view.findViewById(R.id.rvFavorites);
        userComics = new ArrayList<>();
        comicsAdapter = new LibraryComicsAdapter(userComics, getContext());


        tvUsername = view.findViewById(R.id.tvUsername);
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        tvBio = view.findViewById(R.id.tvBio);
        tvReadingNum = view.findViewById(R.id.tvReadingNum);
        tvFavoriteNum = view.findViewById(R.id.tvFavoriteNum);

        rvComics.setAdapter(comicsAdapter);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                comicsAdapter.clear();
                queryProfile();
                swipeContainer.setRefreshing(false);
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
            }

        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        queryProfile();

        int numberOfColumns = 3;
        rvComics.setLayoutManager(new GridLayoutManager(getContext(),numberOfColumns));

    }

    protected void queryProfile() {
        readingCount = 0;
        favoriteCount = 0;
        tvUsername.setText(ParseUser.getCurrentUser().getUsername());

        String bio = ParseUser.getCurrentUser().getString("biography");
        if (bio != null) {
            tvBio.setText(ParseUser.getCurrentUser().getString("biography"));
        }

        ParseFile profilePic = ParseUser.getCurrentUser().getParseFile("profilePic");
        if (profilePic != null){
            Glide.with(getContext())
                    .load(ParseUser.getCurrentUser().getParseFile("profilePic").getUrl())
                    .into(ivProfilePic);
        }

        ParseQuery<UserComic> query = ParseQuery.getQuery(UserComic.class);
        // Define the query conditions
        query.whereEqualTo("userId", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<UserComic>() {
            @Override
            public void done(List<UserComic> comics, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    for (UserComic comic: comics) {
                            // adds specific comic in specific row
                        if (comic.getIsFavorite() == true){
                            favoriteCount++;
                            userComics.add(comic);
                        }
                        if (comic.getStatus().equals("Reading")){
                            readingCount++;
                        }
                    }
                } else {
                    Log.d(TAG, "Issue with getting comics.");
                }

                comicsAdapter.notifyDataSetChanged();
                Log.i(TAG, "Size of userComics: " + String.valueOf(userComics.size()));
                tvReadingNum.setText(String.format("%d",readingCount));
                tvFavoriteNum.setText(String.format("%d",favoriteCount));
            }
        });
    }

}
