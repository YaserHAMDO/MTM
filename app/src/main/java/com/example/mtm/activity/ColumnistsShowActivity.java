package com.example.mtm.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mtm.R;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.ZoomClass;

import java.util.ArrayList;

public class ColumnistsShowActivity extends AppCompatActivity implements ZoomClass.ZoomClassListener {

    private ProgressBar progressBar;
    private ArrayList<String> columnistsShowArray;
    private int index, count;
    private ZoomClass zoomClass;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_columnists_show);
        initValues();
        loadImage();

    }


    private void initValues() {

        progressBar = findViewById(R.id.progressBar);
        zoomClass = findViewById(R.id.imageView);
        text = findViewById(R.id.text);

        zoomClass.setZoomClassListener(this);

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        columnistsShowArray = DataHolder.getInstance().getColumnistsShowArray();
        count = columnistsShowArray.size();


        ImageView leftArrowImageView = findViewById(R.id.leftArrowImageView);
        ImageView rightArrowImageView = findViewById(R.id.rightArrowImageView);

        leftArrowImageView.setOnClickListener(view -> {
            selectLeft();
        });

        rightArrowImageView.setOnClickListener(view -> {
            selectRight();


        });


    }


    private void loadImage() {

        progressBar.setVisibility(View.VISIBLE);

        Glide.with(this)
                .load(columnistsShowArray.get(index))
//                    .placeholder(R.drawable.placeholder_image) // Placeholder resource while the image is loading
//                    .error(R.drawable.error_image) // Error resource if the image fails to load
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE); // Hide progress bar if image loading fails
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE); // Hide progress bar when image loading is complete

//                        Glide.with(ColumnistsShowActivity.this)
//                                        .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (i + 1) + ".jpg" + "?sz=full")
//                                        .preload();

//                        if (showed < count) {
//
//                            if (i == 1) {
//                                Glide.with(SubNewspaperActivity.this)
//                                        .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (i + 1) + ".jpg" + "?sz=full")
//                                        .preload();
//
//                                Glide.with(SubNewspaperActivity.this)
//                                        .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (i + 2) + ".jpg" + "?sz=full")
//                                        .preload();
//                            }
//
//                            if(showed + 3 <= count) {
//                                Glide.with(SubNewspaperActivity.this)
//                                        .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (showed + 3) + ".jpg" + "?sz=full")
//                                        .preload();
//                            }
//
//                            if(showed + 4 <= count) {
//                                Glide.with(SubNewspaperActivity.this)
//                                        .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (showed + 4) + ".jpg" + "?sz=full")
//                                        .preload();
//                            }
//
//
//                            if(showed + 5 <= count) {
//                                Glide.with(SubNewspaperActivity.this)
//                                        .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (showed + 5) + ".jpg" + "?sz=full")
//                                        .preload();
//
//                            }
//
//                            showed = showed + 3;
//
//                        }

                        return false;
                    }
                })
                .into(zoomClass);



        text.setText((index + 1) + "/" + count);


    }


    private void selectRight() {
        if (index < count - 1) {
            index++;
            loadImage();
        }
        else {
            text.animate().
//                        alpha(0.7f).
        scaleX(1.2f).
                    scaleY(1.2f).
                    setDuration(100).withEndAction(() -> text.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(100));
        }
    }

    private void selectLeft() {
        if (index > 0) {
            index--;
            loadImage();
        }
        else {
            text.animate().
//                        alpha(0.7f).
        scaleX(1.2f).
                    scaleY(1.2f).
                    setDuration(100).withEndAction(() -> text.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(100));
        }
    }

    @Override
    public void onSwipeRight() {
        selectRight();
    }

    @Override
    public void onSwipeLeft() {
        selectLeft();
    }
}