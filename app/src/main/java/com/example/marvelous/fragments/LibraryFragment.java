package com.example.marvelous.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marvelous.R;
import com.example.marvelous.adapters.LibraryAdapter;
import com.example.marvelous.models.UserComic;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

    List<String> possStatuses;
    RecyclerView rvLibrary;
    LibraryAdapter libraryAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        possStatuses = new ArrayList<>();
        possStatuses.add("Reading");
        possStatuses.add("Completed");
        possStatuses.add("On Hold");
        possStatuses.add("Dropped");
        possStatuses.add("Plan to Watch");

        libraryAdapter = new LibraryAdapter(possStatuses);
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
    }

}
