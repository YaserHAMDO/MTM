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
import com.medyatakip.app.model.Model1;
import com.medyatakip.app.util.Constants;
import com.medyatakip.app.util.DataHolder;
import com.medyatakip.app.util.ImagePreloader;
import com.medyatakip.app.util.MyUtils;
import com.medyatakip.app.util.ZoomClass;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements ZoomClass.ZoomClassListener, ImagePreloader.progressBarVisibility {

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

    ArrayList<Model1> model1s;

    ImagePreloader imagePreloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);





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
//        count = intent.getIntExtra("count", 0);
        j = intent.getIntExtra("position", 0);

        i = 1;


        model1s = DataHolder.getInstance().getModels1();

//        for (int i = 0; i < result.getData().size(); i++) {
//            model1s.add(Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageInfo().getMediaPath() + "page/" + result.getData().get(i).getImageInfo().getPageFile() + "-1.jpg?sz=full");
//        }

//        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
//        circularProgressDrawable.setStrokeWidth(5f);
//        circularProgressDrawable.setCenterRadius(30f);
//        circularProgressDrawable.start();



        count = model1s.size();
        setTitle();


//        loadImage2();


        // Initialize your imageViews and imageUrls arrays

        ArrayList<String> imageUrls = new ArrayList<>();

        for(int i = 0; i < model1s.size(); i++) {
            imageUrls.add(model1s.get(i).getFirstPageUrl());
        }


        imagePreloader = new ImagePreloader(this, zoomClass, imageUrls, this);

        imagePreloader.loadCurrentImage(j);
//        imagePreloader.preloadImages(j);


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
            // When user swipes to the next image


            selectRight();


        });

        test12.setOnClickListener(view -> {

        });

        cancel.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());

        paylas.setOnClickListener(view -> MyUtils.shareLink(model1s.get(j).getFirstPageUrl(), this));

    }

    private void setTitle() {
        text.setText((j + 1) + "/" + model1s.size());
        sourceTextView.setText(model1s.get(j).getName() + "\n" + model1s.get(j).getDate());
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
            imagePreloader.loadPreviousImage();
            setTitle();
//            loadImage2();
            zoomClass.fitToScreen();
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
        if (j < model1s.size() - 1) {
            j++;
//            loadImage2();
            zoomClass.fitToScreen();
            imagePreloader.loadNextImage();
            setTitle();
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
                                Glide.with(MainActivity2.this)
                                    .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (i + 1) + ".jpg" + "?sz=full")
                                    .preload();

                                Glide.with(MainActivity2.this)
                                    .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (i + 2) + ".jpg" + "?sz=full")
                                    .preload();
                            }

                            if(showed + 3 <= count) {
                                Glide.with(MainActivity2.this)
                                        .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (showed + 3) + ".jpg" + "?sz=full")
                                        .preload();
                            }

                            if(showed + 4 <= count) {
                                Glide.with(MainActivity2.this)
                                        .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (showed + 4) + ".jpg" + "?sz=full")
                                        .preload();
                            }


                            if(showed + 5 <= count) {
                                Glide.with(MainActivity2.this)
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
                .load(model1s.get(j).getFirstPageUrl())
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
                                Glide.with(MainActivity2.this)
                                    .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (j + 1) + ".jpg" + "?sz=full")
                                    .preload();

                                Glide.with(MainActivity2.this)
                                    .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (j + 2) + ".jpg" + "?sz=full")
                                    .preload();
                            }

                            if(showed + 3 <= count) {
                                Glide.with(MainActivity2.this)
                                        .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (showed + 3) + ".jpg" + "?sz=full")
                                        .preload();
                            }

                            if(showed + 4 <= count) {
                                Glide.with(MainActivity2.this)
                                        .load(Constants.KEY_IMAGE_BASIC_URL + mediaPath + (showed + 4) + ".jpg" + "?sz=full")
                                        .preload();
                            }


                            if(showed + 5 <= count) {
                                Glide.with(MainActivity2.this)
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
        test12.setVisibility(test12.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void showProgressBar(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }
}