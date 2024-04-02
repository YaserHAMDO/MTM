package com.example.mtm.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mtm.R;
import com.example.mtm.adapter.SubInternetAdapter;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.MyUtils;
import com.example.mtm.util.ZoomClass;

import java.util.ArrayList;

public class MesutActivity extends AppCompatActivity implements ZoomClass.ZoomClassListener{

    private ProgressBar progressBar;
    private ArrayList<String> printedMediaFullPageShowArray;
    private ArrayList<String> printedMediaShareLinkArray;
    private ArrayList<String> printedMediaSubPageShowArray, printedMediaDateShowArray, printedMediaNamesShowArray;
    private int index, count;
    private ZoomClass zoomClass;
    private LinearLayout hamdo;
    private TextView text, tumSayfa, name;
    private ImageView paylas, cancel;
    private boolean fullPage;
    int showed = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesut);

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
        hamdo = findViewById(R.id.hamdo);
        name = findViewById(R.id.name);
//        tarih = findViewById(R.id.tarih);

        zoomClass.setZoomClassListener(this);

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);

        String name2 = intent.getStringExtra("name");
        String date2 = intent.getStringExtra("date");



        printedMediaFullPageShowArray = DataHolder.getInstance().getPrintedMediaFullPageShowArray();
        printedMediaShareLinkArray = DataHolder.getInstance().getPrintedMediaShareLinkArray();
        printedMediaSubPageShowArray = DataHolder.getInstance().getPrintedMediaSubPageShowArray();
        printedMediaDateShowArray = DataHolder.getInstance().getPrintedMediaDateShowArray();
        printedMediaNamesShowArray = DataHolder.getInstance().getPrintedMediaNamesShowArray();

        count = printedMediaSubPageShowArray.size();
        fullPage = false;


        if (printedMediaFullPageShowArray.get(index).equals("")) {
            tumSayfa.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
        }

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
            }
            else {
                tumSayfa.setText("Haberi Gör");
            }
            fullPage = !fullPage;

            loadImage();

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

        hamdo.setOnClickListener(view -> {

        });




    }


    private void loadImage() {

        name.setText(printedMediaNamesShowArray.get(index) + "\n" + printedMediaDateShowArray.get(index));



        hamdo.setVisibility(View.GONE);

        progressBar.setVisibility(View.VISIBLE);

        String imageUrl = "";

        if (fullPage) {
            imageUrl = printedMediaFullPageShowArray.get(index);
        }
        else {
            imageUrl = printedMediaSubPageShowArray.get(index);
        }

        System.out.println("hasan Yilmaz " + imageUrl);



        Glide.with(this)
                .load(imageUrl)
//                    .placeholder(R.drawable.placeholder_image) // Placeholder resource while the image is loading
//                    .error(R.drawable.error_image) // Error resource if the image fails to load
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE); // Hide progress bar if image loading fails
                        hamdo.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE); // Hide progress bar when image loading is complete
                        hamdo.setVisibility(View.VISIBLE);

                        if (showed < count) {

                            if (index == 1) {

                                String s1 = "", s2 = "";

                                if (fullPage) {
                                    if (index + 1 < printedMediaFullPageShowArray.size())
                                        s1 = printedMediaFullPageShowArray.get(index + 1);
                                    if (index + 2 < printedMediaFullPageShowArray.size())
                                        s2 = printedMediaFullPageShowArray.get(index + 2);
                                }
                                else {
                                    if (index + 1 < printedMediaSubPageShowArray.size())
                                        s1 = printedMediaSubPageShowArray.get(index + 1);
                                    if (index + 2 < printedMediaSubPageShowArray.size())
                                        s2 = printedMediaSubPageShowArray.get(index + 2);
                                }
                                Glide.with(MesutActivity.this)
                                        .load(s1)
                                        .preload();

                                Glide.with(MesutActivity.this)
                                        .load(s2)
                                        .preload();
                            }

                            if(showed + 3 <= count) {
                                String s;
                                if (fullPage) {
                                    s = printedMediaFullPageShowArray.get(index + 3);
                                }
                                else {
                                    s = printedMediaSubPageShowArray.get(index + 3);
                                }

                                Glide.with(MesutActivity.this)
                                        .load(s)
                                        .preload();
                            }

                            if(showed + 4 <= count) {

                                String s;
                                if (fullPage) {
                                    s = printedMediaFullPageShowArray.get(index + 4);
                                }
                                else {
                                    s = printedMediaSubPageShowArray.get(index + 4);
                                }


                                Glide.with(MesutActivity.this)
                                        .load(s)
                                        .preload();
                            }


                            if(showed + 5 <= count - 1) {

                                String s;
                                if (fullPage) {
                                    s = printedMediaFullPageShowArray.get(index + 5);
                                }
                                else {
                                    s = printedMediaSubPageShowArray.get(index + 5);
                                }

                                Glide.with(MesutActivity.this)
                                        .load(s)
                                        .preload();

                            }

                            showed = showed + 3;

                        }



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

    @Override
    public void onSwipeDown() {

        getOnBackPressedDispatcher().onBackPressed();

    }

    @Override
    public void onSwipeUp() {
//        Toast.makeText(this, "onSwipeUp", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSingleTapUp() {
        hamdo.setVisibility(hamdo.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

}