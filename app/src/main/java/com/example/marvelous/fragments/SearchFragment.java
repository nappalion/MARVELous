package com.example.marvelous.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelous.R;
import com.example.marvelous.adapters.SearchAdapter;
import com.example.marvelous.models.Comic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchFragment extends Fragment{

    public static final String TAG = "SearchFragment";
    private final int REQUEST_CODE = 20;

    String base_url ="https://gateway.marvel.com:443/v1/public/";
    String PUBLIC_KEY = "685c68298103e0c7ba2d074f58a4619b";
    String PRIVATE_KEY = "3b9af090798ac35d9d1176e212ed8c9451c30c9c";
    String searchQuery;

    Date date = new Date();
    long timeMilli = date.getTime();
    String TS = (String.valueOf(timeMilli));

    String hashConvert = TS + PRIVATE_KEY + PUBLIC_KEY;
    String HASH = md5(hashConvert);

    String url = base_url + "comics?" + "titleStartsWith=" + searchQuery + "&limit=20&offset=0" + "&ts=" + TS + "&apikey=" + PUBLIC_KEY + "&hash=" + HASH;

    List<Comic> comicList;
    SearchAdapter searchAdapter;
    private RecyclerView rvSearch;
    RequestQueue queue;
    ProgressBar progressBar;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        comicList = new ArrayList<>();
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
                    searchQuery = query;
                    Log.d(TAG, "Text submit");
                    url = base_url + "comics?" + "titleStartsWith=" + searchQuery + "&limit=20&offset=0" + "&ts=" + TS + "&apikey=" + PUBLIC_KEY + "&hash=" + HASH;
                    searchComic();
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
//                    Log.d(TAG, "Text changed");
//                    searchAdapter.getFilter().filter(newText);
                      return false;
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        super.onActivityCreated(savedInstanceState);
    }

    public  void searchComic(){
        Log.i("URL", url);
        queue = Volley.newRequestQueue(getContext());
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse (JSONObject object){
                try {
                    searchAdapter.clear();
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.i(TAG, "successfully connected" + object.toString());
                    JSONArray comics = object.getJSONObject("data").getJSONArray("results");
                    searchAdapter.addAll(Comic.fromJsonArray(comics));
                } catch (JSONException e) {
                    progressBar.setVisibility(View.INVISIBLE);
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

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
