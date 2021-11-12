package com.example.marvelous.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.marvelous.R;
//import com.example.marvelous.adapters.ComicsAdapter;
import com.example.marvelous.models.Comic;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    /*private RecyclerView rvPosts;
    public static final String TAG = "PostsFragment";
    protected ComicsAdapter adapter;
    protected List<Comic> allPosts;
    SwipeRefreshLayout swipeContainer;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "fetching new data!");
                adapter.clear();
                adapter.addAll(allPosts);
                queryPost();
                swipeContainer.setRefreshing(false);
            }
        });

        rvPosts = view.findViewById(R.id.rvComics);
        allPosts = new ArrayList<>();
        adapter = new ComicsAdapter(getContext(), allPosts);


        //steps to use the recycler view
        // 0. create layout for one row in the list
        // 1. create the adapter
        // 2. create the data source
        // 3. set the adapter on the recycler view
        rvPosts.setAdapter(adapter);
        // 4. set the layout on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPost();
    }

    protected void queryPost() {
        ParseQuery<Comic> query = ParseQuery.getQuery(Comic.class);
        query.include(Comic.KEY_COMIC);
        query.setLimit(20);
        query.findInBackground(new FindCallback<Comic>() {
            @Override
            public void done(List<Comic> comics, ParseException e) {
                if(e!=null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Comic post: comics){
                    Log.i(TAG, "Comic: " + post.getDescription());
                }
                allPosts.addAll(comics);
                adapter.notifyDataSetChanged();
            }
        });
    }*/
}
