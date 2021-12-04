package com.example.marvelous.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;

import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.marvelous.R;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.test.InputFilterMinMax;


import org.w3c.dom.Text;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;

public class ReviewActivity extends AppCompatActivity {

    ImageView ivComic;
    TextView tvComicTitle;
    ScrollView scrollView;
    String imageUrl;
    EditText etReview;
    TextView tvCounter;

    EditText etUserReview;

    public static final String TAG = "ReviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_review);

        scrollView = findViewById(R.id.scrollView);
        ivComic = findViewById(R.id.ivComic);
        tvComicTitle = findViewById(R.id.tvComicTitle);
        etReview = findViewById(R.id.etReview);

        etUserReview = findViewById(R.id.etUserReview);
        tvCounter = findViewById(R.id.tvCounter);

        imageUrl = getIntent().getStringExtra("imageUrl");

        final TextWatcher textWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tvCounter.setText(String.valueOf(s.length()) + "/750");
            }

            public void afterTextChanged(Editable s) {
            }
        };


        etUserReview.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        etReview.addTextChangedListener(textWatcher);

        Glide.with(this)
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
    }
}