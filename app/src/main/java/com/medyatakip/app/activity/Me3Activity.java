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
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.medyatakip.app.R;
import com.medyatakip.app.model.VerticalModel;
import com.medyatakip.app.util.DataHolder;
import com.medyatakip.app.util.MyUtils;
import com.medyatakip.app.util.ZoomClass;

import java.util.ArrayList;

public class Me3Activity extends AppCompatActivity implements ZoomClass.ZoomClassListener{

    private ProgressBar progressBar;
    private ArrayList<VerticalModel> verticalModels;
    private ArrayList<String> printedMediaFullPageShowArray;
    private ArrayList<String> printedMediaShareLinkArray;
    private ArrayList<String> printedMediaSubPageShowArray, printedMediaDateShowArray, printedMediaNamesShowArray;
    private int index, count;
    private ZoomClass zoomClass;
    private LinearLayout ha;
    private TextView text, tumSayfa, name, sayfa, date;
    private ImageView paylas, cancel;
    private boolean fullPage;

    private LinearLayout lookingForLinearLayout;
    private int selectedCircleIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me3);

        initValues();
        loadImage();
    }
    private void initValues() {

        progressBar = findViewById(R.id.progressBar);
        zoomClass = findViewById(R.id.imageView);
        text = findViewById(R.id.text);
        tumSayfa = findViewById(R.id.tumSayfa);
        paylas = findViewById(R.id.paylas);
        cancel = findViewById(R.id.cancel);
        ha = findViewById(R.id.ha);
        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        sayfa = findViewById(R.id.sayfa);
        lookingForLinearLayout = findViewById(R.id.dynamicView_LinearLayout);
//        tarih = findViewById(R.id.tarih);

        zoomClass.setZoomClassListener(this);

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);

        String name2 = intent.getStringExtra("name");
        String date2 = intent.getStringExtra("date");



        verticalModels = DataHolder.getInstance().getVerticalModels();
        printedMediaFullPageShowArray = DataHolder.getInstance().getPrintedMediaFullPageShowArray();
        printedMediaShareLinkArray = DataHolder.getInstance().getPrintedMediaShareLinkArray();
        printedMediaSubPageShowArray = DataHolder.getInstance().getPrintedMediaSubPageShowArray();
        printedMediaDateShowArray = DataHolder.getInstance().getPrintedMediaDateShowArray();
        printedMediaNamesShowArray = DataHolder.getInstance().getPrintedMediaNamesShowArray();

        count = verticalModels.size();
        fullPage = false;


//        if (printedMediaFullPageShowArray.get(index).equals("")) {
//            tumSayfa.setVisibility(View.GONE);
//            cancel.setVisibility(View.VISIBLE);
//        }

        ImageView leftArrowImageView = findViewById(R.id.leftArrowImageView);
        ImageView rightArrowImageView = findViewById(R.id.rightArrowImageView);

        leftArrowImageView.setOnClickListener(view -> {
            selectLeft();
        });

        rightArrowImageView.setOnClickListener(view -> {
            selectRight();
        });


        tumSayfa.setOnClickListener(view -> {
            if(fullPage) {
                tumSayfa.setText("Sayfayı Gör");
//                lookingForLinearLayout.setVisibility(View.VISIBLE);
                zoomClass.fitToScreen();

            }
            else {
//                lookingForLinearLayout.setVisibility(View.INVISIBLE);
                tumSayfa.setText("Haberi Gör");
                zoomClass.fitToScreen();
            }
            fullPage = !fullPage;

            loadImage2();

        });

        paylas.setOnClickListener(view -> {

            MyUtils.shareLink(printedMediaShareLinkArray.get(index), this);
//            if(fullPage) {
//                MyUtils.shareLink(printedMediaFullPageShowArray.get(index), this);
//            }
//            else {
//                MyUtils.shareLink(printedMediaSubPageShowArray.get(index), this);
//            }

        });

        cancel.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();

        });

        ha.setOnClickListener(view -> {

        });


//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );

//        drawCircles();





    }

    private void drawCircles() {

        lookingForLinearLayout.removeAllViews();

//        selectedCircleIndex = 0;

        int size = verticalModels.get(index).getFullImages().size();

        if (size > 1) {

            lookingForLinearLayout.setVisibility(View.VISIBLE);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    dpToPx(15),
                    dpToPx(15)
            );

            layoutParams.setMargins(0, 0, 0, dpToPx(4));

            for (int i = 0; i < size; i++) {
                View view = getLayoutInflater().inflate(R.layout.circle_view, null);

                // Set padding programmatically
                view.setPadding(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));

                // Set background color based on the index
                int backgroundDrawableResId;
                if (i == 0)
                    backgroundDrawableResId = R.drawable.orange_circule;
                else
                    backgroundDrawableResId = R.drawable.blue_circule;

                view.findViewById(R.id.circle).setBackgroundResource(backgroundDrawableResId);

                lookingForLinearLayout.addView(view, layoutParams);
            }
        }
        else {
            lookingForLinearLayout.setVisibility(View.INVISIBLE);
        }


    }

    private void selectCircle(int index) {
        // Set the background of the previously selected circle to blue
        View prevSelectedView = lookingForLinearLayout.getChildAt(selectedCircleIndex);
        prevSelectedView.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_circule));

        // Set the background of the newly selected circle to orange
        View selectedView = lookingForLinearLayout.getChildAt(index);
        selectedView.setBackground(ContextCompat.getDrawable(this, R.drawable.orange_circule));

        // Update the selected circle index
        selectedCircleIndex = index;
    }

    private int dpToPx(int dp){
        return (int)(dp * this.getApplicationContext().getResources().getDisplayMetrics().density);
    }


    private void loadImage() {



        name.setText(printedMediaNamesShowArray.get(index));
        sayfa.setText(" /sf." + (index + 1));
        date.setText(printedMediaDateShowArray.get(index));


        ha.setVisibility(View.GONE);

        progressBar.setVisibility(View.VISIBLE);

        String imageUrl = "";

        if (fullPage) {
//            imageUrl = printedMediaFullPageShowArray.get(index);
            imageUrl = verticalModels.get(index).getFullImages().get(0);
        }
        else {
            imageUrl = verticalModels.get(index).getClipImages().get(0);
        }

//        if (fullPage) {
//            imageUrl = verticalModels.get(index).getHorizontalImages();
//        }
//        else {
//            imageUrl = printedMediaSubPageShowArray.get(index);
////            imageUrl = verticalModels.get(index).getHorizontalImages();
//        }


        Glide.with(this)
                .load(imageUrl)
//                    .placeholder(R.drawable.placeholder_image) // Placeholder resource while the image is loading
//                    .error(R.drawable.error_image) // Error resource if the image fails to load
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE); // Hide progress bar if image loading fails
                        ha.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE); // Hide progress bar when image loading is complete
                        ha.setVisibility(View.VISIBLE);

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


        if (count == 0) {
            count++;
        }

        text.setText((index + 1) + "/" + count);


        drawCircles();

    }

    private void loadImage2() {

        progressBar.setVisibility(View.VISIBLE);


        String imageUrl = "";

        if (fullPage) {
            imageUrl = verticalModels.get(index).getFullImages().get(selectedCircleIndex);
//            imageUrl = printedMediaFullPageShowArray.get(index);
        }
        else {
            imageUrl = verticalModels.get(index).getClipImages().get(selectedCircleIndex);
//            imageUrl = verticalModels.get(index).getVerticalImages().get(selectedCircleIndex);
        }

//        String imageUrl = verticalModels.get(index).getVerticalImages().get(selectedCircleIndex);
//
//        if (fullPage) {
//            imageUrl = verticalModels.get(index).getHorizontalImages();
//        }
//        else {
//            imageUrl = verticalModels.get(index).getHorizontalImages();
//        }

        Glide.with(this)
                .load(imageUrl)
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
    }


    private void selectRight() {
        if (index < count - 1) {
            index++;
            selectedCircleIndex = 0;
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
            selectedCircleIndex = 0;
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

    @Override
    public void onSwipeDown() {

        if (selectedCircleIndex > 0) {
            selectCircle(selectedCircleIndex - 1);
            loadImage2();
        }
        else {
            getOnBackPressedDispatcher().onBackPressed();
        }


    }

    @Override
    public void onSwipeUp() {

        if (selectedCircleIndex < lookingForLinearLayout.getChildCount() - 1) {
            selectCircle(selectedCircleIndex + 1);
            loadImage2();
        }

    }

    @Override
    public void onSingleTapUp() {
        ha.setVisibility(ha.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

}