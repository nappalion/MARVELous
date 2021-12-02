package com.example.marvelous.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marvelous.R;
import com.example.marvelous.adapters.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment{

    public static final String TAG = "SearchFragment";

    List<String> comicList;
    SearchAdapter searchAdapter;
    private RecyclerView rvSearch;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        comicList = new ArrayList<>();
        comicList.add("Infinity Gauntlet (1991) #1");
        comicList.add("Infinity (2013) #1 ");
        comicList.add("Amazing Fantasy (1962) #15 ");
        comicList.add("Civil War (2006) #1 ");
        comicList.add("Infinity War (1992) #1 ");
        comicList.add("Darth Vader (2017) #1 ");
        comicList.add("Secret Empire (2017) #1 ");
        comicList.add("Civil War II (2016) ");
        comicList.add("Black Panther (2016) #1 ");
        comicList.add("Avengers (2012) #1 ");
        comicList.add("Thanos (2016) #1 ");
        comicList.add("Deadpool (2015) #1 ");
        comicList.add("Secret Wars (2015) #1 ");
        comicList.add("Ultimate Spider-Man (2000) #1");
        comicList.add("X-Men: Gold (2017) #1 ");
        comicList.add("Old Man Logan (2016) #25 ");
        comicList.add("Astonishing X-Men (2017) #1 ");
        comicList.add("Spider-Man (2016) #1");
        comicList.add("All-New Guardians of the Galaxy (2017) #1 ");
        comicList.add("The Amazing Spider-Man (1963) #1 ");
        comicList.add("Weapon X (2017) #1 ");
        comicList.add("Peter Parker: The Spectacular Spider-Man (2017) #1 ");
        comicList.add("X-Men: Blue (2017) #1 ");
        comicList.add("Captain America: Steve Rogers (2016) #16 ");
        comicList.add("Deadpool Kills the Marvel Universe Again (2017) #1 ");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchAdapter = new SearchAdapter(getContext(), comicList);

        rvSearch = view.findViewById(R.id.rvSearch);
        rvSearch.setAdapter(searchAdapter);
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mitSearch) {
            androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d(TAG, "Text submit");
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d(TAG, "Text changed");
                    searchAdapter.getFilter().filter(newText);
                    return false;
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
