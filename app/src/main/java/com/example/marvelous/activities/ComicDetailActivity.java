package com.example.marvelous.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import com.example.marvelous.models.Comic;
import com.example.marvelous.models.UserComic;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;

public class ComicDetailActivity extends AppCompatActivity {

    TextView tvTitle;
    TextView tvPublished;
    TextView tvPenciler;
    TextView tvWriter;
    TextView tvCoverArtist;
    TextView tvDescription;
    ImageView imageView;
    Button btnReview;
    String imageUrl;
    ScrollView scrollView;
    ToggleButton btnFavorite;

    public static final String TAG = "ComicDetailActivity";

    final String PUBLIC_KEY = "685c68298103e0c7ba2d074f58a4619b";
    final String PRIVATE_KEY = "3b9af090798ac35d9d1176e212ed8c9451c30c9c";

    Date date = new Date();
    long timeMilli = date.getTime();
    String TS = (String.valueOf(timeMilli));
    String hashConvert = TS + PRIVATE_KEY + PUBLIC_KEY;
    String HASH = md5(hashConvert);

    String base_url ="https://gateway.marvel.com:443/v1/public/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Comic comic = Parcels.unwrap(getIntent().getParcelableExtra("comic"));
        setContentView(R.layout.comic_detail);

        UserComic userComic = new UserComic();

        tvTitle = findViewById(R.id.lblTitle);
        tvPublished = findViewById(R.id.lblPublished);
        tvPenciler = findViewById(R.id.lblPenciler);
        tvWriter = findViewById(R.id.lblWriter);
        tvCoverArtist = findViewById(R.id.lblCoverArtist);
        tvDescription = findViewById(R.id.lblDescription);
        imageView = (ImageView)findViewById(R.id.ivComic);
        btnReview = findViewById(R.id.btnReview);
        scrollView = findViewById(R.id.scrollView);
        btnFavorite = findViewById(R.id.btnFavorite);

        imageUrl = comic.url;
        Log.i(TAG, imageUrl);

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getApplicationContext(), ReviewActivity.class);
                i.putExtra("imageUrl", imageUrl);
                startActivity(i);
            }
        });

        initializeBtnFavorite(userComic);

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create Parse Row
                userComic.setComicId(Integer.parseInt(comic.id));


                if (userComic.getIsFavorite() == true) {
                    userComic.setIsFavorite(false);
                }
                else {
                    userComic.setIsFavorite(true);
                }

                userComic.setUserId(ParseUser.getCurrentUser());

                userComic.saveInBackground();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(ComicDetailActivity.this);
        String id = "89680";
        String url = base_url + "comics" + "/" + comic.id + "?ts=" + TS + "&apikey=" + PUBLIC_KEY + "&hash=" + HASH;

        Log.i("URL", url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                //String copyright = null;
                try {
                    tvTitle.setText(object.getJSONObject("data").getJSONArray("results").getJSONObject(0).getString("title"));
                    tvPublished.setText(object.getJSONObject("data").getJSONArray("results").getJSONObject(0).getString("modified"));
                    String imagePath = object.getJSONObject("data").getJSONArray("results").getJSONObject(0)
                            .getJSONArray("images").getJSONObject(0).getString("path");
                    String imageExtension = object.getJSONObject("data").getJSONArray("results").getJSONObject(0)
                            .getJSONArray("images").getJSONObject(0).getString("extension");
                    tvDescription.setText("No description");

                    if (comic.description != ""){
                        tvDescription.setText(comic.description);
                    }

                    Glide.with(ComicDetailActivity.this)
                            .load(imageUrl)
                            .into(imageView);

                    Glide.with(ComicDetailActivity.this)
                            .load(imageUrl)
                            .transform(new MultiTransformation<Bitmap>(new BlurTransformation(150), new BrightnessFilterTransformation((float) -0.4)))
                            .into(new CustomTarget<Drawable>() {
                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    scrollView.setBackground(resource);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });

                    JSONArray creators = object.getJSONObject("data").getJSONArray("results").getJSONObject(0)
                            .getJSONObject("creators").getJSONArray("items");

                    String role, name;
                    for(int i = 0; i < creators.length(); i++){
                        JSONObject item = creators.getJSONObject(i);
                        name = item.getString("name");
                        role = item.getString("role");
                        if(role.equals("writer"))
                            tvWriter.setText(name);
                        else if(role.equals("letterer"))
                            tvPenciler.setText(name);
                        else if(role.equals("painter (cover)"))
                            tvCoverArtist.setText(name);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "an error occured");
            }
        });
        queue.add(request);
    }

    private void initializeBtnFavorite(UserComic userComic) {
        if (userComic.getIsFavorite() == true) {
            btnFavorite.setChecked(true);
        }
        else {
            btnFavorite.setChecked(false);
        }
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