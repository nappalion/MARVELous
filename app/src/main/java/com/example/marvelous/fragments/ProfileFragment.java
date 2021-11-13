package com.example.marvelous.fragments;

import android.content.Context;
import android.os.Bundle;
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
import com.parse.ParseFile;
import com.parse.ParseUser;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private ImageView ivProfilePic;
    private TextView tvUsername;
    private TextView tvBio;
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
        tvUsername = view.findViewById(R.id.tvUsername);
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        tvBio = view.findViewById(R.id.tvBio);

        tvUsername.setText(ParseUser.getCurrentUser().getUsername());

        String bio = ParseUser.getCurrentUser().getString("biography");
        if (bio != null) {
            tvBio.setText(ParseUser.getCurrentUser().getString("biography"));
        }

        ParseFile profilePic = ParseUser.getCurrentUser().getParseFile("profilePic");
        if (profilePic != null){
            Glide.with(this.getContext())
                    .load(ParseUser.getCurrentUser().getParseFile("profilePic").getUrl())
                    .into(ivProfilePic);
        }

    }
}
