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
import com.example.marvelous.adapters.HomeAdapter;
import com.example.marvelous.models.Comic;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    List<String> possStatuses;
    RecyclerView rvHome;
    com.example.marvelous.adapters.HomeAdapter HomeAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        possStatuses = new ArrayList<>();
        possStatuses.add("New Comics");
        possStatuses.add("Top Rated");
        possStatuses.add("Popular");
        possStatuses.add("Recommended");

        HomeAdapter.OnClickListener onClickListener = new HomeAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                // expand the RecyclerView
                // Toast.makeText( getContext(), "Expand View", Toast.LENGTH_SHORT).show();

            }
        };

        HomeAdapter = new HomeAdapter(possStatuses, this.getContext(), onClickListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvHome = view.findViewById(R.id.rvHome);

        rvHome.setAdapter(HomeAdapter);
        rvHome.setLayoutManager(new LinearLayoutManager(getContext()));
        HomeAdapter.notifyDataSetChanged();
    }
}
