package com.example.marvelous.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marvelous.R;
import com.example.marvelous.adapters.LibraryAdapter;
import com.example.marvelous.models.UserComic;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

    List<String> possStatuses;
    RecyclerView rvLibrary;
    LibraryAdapter libraryAdapter;
    String tvStatus;
    public static final String TAG = "LibraryFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        possStatuses = new ArrayList<>();
        possStatuses.add("Reading");
        possStatuses.add("Completed");
        possStatuses.add("On Hold");
        possStatuses.add("Dropped");
        possStatuses.add("Plan to Watch");

        LibraryAdapter.OnClickListener onClickListener = new LibraryAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                // expand the RecyclerView
                // Toast.makeText( getContext(), "Expand View", Toast.LENGTH_SHORT).show();
            }
        };

        libraryAdapter = new LibraryAdapter(possStatuses, getContext(), onClickListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvLibrary = view.findViewById(R.id.rvLibrary);

        rvLibrary.setAdapter(libraryAdapter);
        rvLibrary.setLayoutManager(new LinearLayoutManager(getContext()));
        libraryAdapter.notifyDataSetChanged();
    }

}
