package com.medyatakip.app.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.medyatakip.app.R;
import com.medyatakip.app.response.NewsPaperFullPagesResponse;
import com.medyatakip.app.util.Constants;
import com.medyatakip.app.util.DataHolder;
import com.medyatakip.app.util.ZoomClass;

import java.util.ArrayList;

public class SubNewspaperActivity extends AppCompatActivity implements ZoomClass.ZoomClassListener{

    int i;
    int j;

    TextView text;

    ProgressBar progressBar;
    String mediaPath;
    int count;
    ZoomClass zoomClass;
    int showed = 1;

    LinearLayout test12;
    ImageView paylas, cancel;
    TextView sourceTextView;

    ArrayList<String> firstPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_newspaper);





        zoomClass = findViewById(R.id.imageView);
        test12 = findViewById(R.id.test12);
        paylas = findViewById(R.id.paylas);
        cancel = findViewById(R.id.cancel);
        sourceTextView = findViewById(R.id.sourceTextView);



        zoomClass.setZoomClassListener(this);

        ImageView leftArrowImageView = findViewById(R.id.leftArrowImageView);
        ImageView rightArrowImageView = findViewById(R.id.rightArrowImageView);
        text = findViewById(R.id.text);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        // Get the value passed from FirstActivity
        mediaPath = intent.getStringExtra("mediaPath");
        count = intent.getIntExtra("count", 0);
        j = intent.getIntExtra("position", 0);

        i = 1;


        firstPages = new ArrayList<>();
        NewsPaperFullPagesResponse result = DataHolder.getInstance().getNewsPaperFullPagesModel();

        for (int i = 0; i < result.getData().size(); i++) {
            firstPages.add(Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageInfo().getMediaPath() + "page/" + result.getData().get(i).getImageInfo().getPageFile() + "-1.jpg?sz=full");
        }

//        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
//        circularProgressDrawable.setStrokeWidth(5f);
//        circularProgressDrawable.setCenterRadius(30f);
//        circularProgressDrawable.start();



        count = firstPages.size();


        loadImage2();



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

//    private void selectLeft() {
//        if (i > 1) {
//            i--;
//            loadImage();
//        }
//        else {
//            text.animate().
////                        alpha(0.7f).
//        scaleX(1.2f).
//                    scaleY(1.2f).
//                    setDuration(100).withEndAction(() -> text.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(100));
//        }
//    }
    private void selectLeft() {
        if (j > 0) {
            j--;
            loadImage2();
        }
        else {
            text.animate().
//                        alpha(0.7f).
        scaleX(1.2f).
                    scaleY(1.2f).
                    setDuration(100).withEndAction(() -> text.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(100));
        }
    }

//    private void selectRight() {
//        if (i < count) {
//            i++;
//            loadImage();
//        }
//        else {
//            text.animate().
////                        alpha(0.7f).
//        scaleX(1.2f).
//                    scaleY(1.2f).
//                    setDuration(100).withEndAction(() -> text.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(100));
//        }
//
//
//    }

    private void selectRight() {
        if (j < firstPages.size()) {
            j++;
            loadImage2();
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

    private void loadImage2() {

        progressBar.setVisibility(View.VISIBLE);


        Glide.with(this)
                .load(firstPages.get(j))
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

                            if (j == 0) {
                                Glide.with(SubNewspaperActivity.this)
                                    .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (j + 1) + ".jpg" + "?sz=full")
                                    .preload();

                                Glide.with(SubNewspaperActivity.this)
                                    .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (j + 2) + ".jpg" + "?sz=full")
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

        text.setText( (j + 1) + "/" + count);


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