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
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.marvelous.R;
import com.test.InputFilterMinMax;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;

public class PageNumberActivity extends AppCompatActivity {

    EditText etPageNumber;
    TextView tvTotalPages;
    Button btnSubmit;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_number);


        etPageNumber = findViewById(R.id.etPageNumber);
        tvTotalPages = findViewById(R.id.tvTotalPages);
        btnSubmit = findViewById(R.id.btnSubmit);
        linearLayout = findViewById(R.id.linearLayout);

        etPageNumber.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        Glide.with(this)
                .load(R.drawable.page_background)
                .transform(new BrightnessFilterTransformation((float) 0.1))
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        linearLayout.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        tvTotalPages.setText(getIntent().getStringExtra("totalPages"));

        Intent data = new Intent();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.putExtra("pageNumber", etPageNumber.getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}