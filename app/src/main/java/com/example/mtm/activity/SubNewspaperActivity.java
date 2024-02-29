package com.example.mtm.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mtm.R;
import com.example.mtm.response.NewsPaperFullPagesResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.ZoomClass;
import com.jsibbold.zoomage.ZoomageView;

public class SubNewspaperActivity extends AppCompatActivity implements ZoomClass.ZoomClassListener{

    int i;

    TextView text;

    ProgressBar progressBar;
    String mediaPath;
    int count;
    ZoomClass zoomClass;
    int showed = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_newspaper);


        NewsPaperFullPagesResponse result = DataHolder.getInstance().getNewsPaperFullPagesModel();

        zoomClass = findViewById(R.id.imageView);
        zoomClass.setZoomClassListener(this);

        ImageView leftArrowImageView = findViewById(R.id.leftArrowImageView);
        ImageView rightArrowImageView = findViewById(R.id.rightArrowImageView);
        text = findViewById(R.id.text);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        // Get the value passed from FirstActivity
        mediaPath = intent.getStringExtra("mediaPath");
        count = intent.getIntExtra("count", 0);

        i = 1;

//        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
//        circularProgressDrawable.setStrokeWidth(5f);
//        circularProgressDrawable.setCenterRadius(30f);
//        circularProgressDrawable.start();




        loadImage();



//        text.setText(i + "/" + count);
////        pages = result.getData().get();
//        Glide.with(this).load(
//                Constants.KEY_IMAGE_BASIC_URL +
//                        mediaPath +
//                        i +
//                        ".jpg" +
//                        "?sz=half"
//
////        ).placeholder(circularProgressDrawable).transition(withCrossFade(500)).into(zoomageView);
//        ).transition(withCrossFade(500)).into(zoomageView);

//        Glide.with(this).load(Constants.NEW_KEY_SITTER_IMAGE_URL + preferenceManager.getString(Constants.KEY_PREVIEW_USER_PROFILE_IMAGE)).placeholder(circularProgressDrawable)).error(R.drawable.no_image_available).into(profileImageView);


        leftArrowImageView.setOnClickListener(view -> {
            selectLeft();
        });

        rightArrowImageView.setOnClickListener(view -> {
            selectRight();


        });

    }

    private void selectLeft() {
        if (i > 1) {
            i--;
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

    private void selectRight() {
        if (i < count) {
            i++;
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

    private void loadImage() {

        progressBar.setVisibility(View.VISIBLE);

        Glide.with(this)
                .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + i + ".jpg" + "?sz=full")
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

                        if (showed < count) {

                            if (i == 1) {
                                Glide.with(SubNewspaperActivity.this)
                                    .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (i + 1) + ".jpg" + "?sz=full")
                                    .preload();

                                Glide.with(SubNewspaperActivity.this)
                                    .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (i + 2) + ".jpg" + "?sz=full")
                                    .preload();
                            }

                            if(showed + 3 <= count) {
                                Glide.with(SubNewspaperActivity.this)
                                        .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (showed + 3) + ".jpg" + "?sz=full")
                                        .preload();
                            }

                            if(showed + 4 <= count) {
                                Glide.with(SubNewspaperActivity.this)
                                        .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (showed + 4) + ".jpg" + "?sz=full")
                                        .preload();
                            }


                            if(showed + 5 <= count) {
                                Glide.with(SubNewspaperActivity.this)
                                        .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (showed + 5) + ".jpg" + "?sz=full")
                                        .preload();

                            }

                            showed = showed + 3;

                        }

                        return false;
                    }
                })
                .into(zoomClass);


        if (count == 0) {
            count++;
        }

        text.setText(i + "/" + count);


    }

    @Override
    public void onSwipeRight() {
        selectRight();
    }

    @Override
    public void onSwipeLeft() {
      selectLeft();
    }

    @Override
    public void onSwipeDown() {
        getOnBackPressedDispatcher().onBackPressed();
    }

    @Override
    public void onSwipeUp() {

    }

    @Override
    public void onSingleTapUp() {

    }
}