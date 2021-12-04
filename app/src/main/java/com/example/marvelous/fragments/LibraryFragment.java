package com.example.marvelous.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.marvelous.activities.MainActivity;
import com.example.marvelous.adapters.LibraryAdapter;
import com.example.marvelous.models.UserComic;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        super.onActivityCreated(savedInstanceState);
    }
}
