package com.example.mtm.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mtm.R;
import com.example.mtm.util.ZoomClass;

public class ProfileActivity extends AppCompatActivity implements ZoomClass.ZoomClassListener{

    private ImageView backIconImageView;
    ZoomClass zoomClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        setItemClickListeners();


//        ZoomClass zoomImageView = findViewById(R.id.zoomImageView);

        zoomClass = findViewById(R.id.zoomImageView);
        zoomClass.setZoomClassListener(this);


//        zoomImageView.setOnSwipeListener(new ZoomClass.OnSwipeListener() {
//            @Override
//            public void onSwipeLeft() {
//                // Action for left swipe
//                Toast.makeText(ProfileActivity.this, "Swiped left", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSwipeUp() {
//
//            }
//
//            @Override
//            public void onSwipeDown() {
//
//            }
//
//            @Override
//            public void onSwipeRight() {
//                // Action for right swipe
//                Toast.makeText(ProfileActivity.this, "Swiped right", Toast.LENGTH_SHORT).show();
//            }
//        });

    }


    private void init() {
        backIconImageView = findViewById(R.id.backIconImageView);
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
    }

    @Override
    public void onSwipeRight() {
        Glide.with(this).load("https://debis.deu.edu.tr/akademiktr/resim_jpg.php?a=1992&p=379").into(zoomClass);
    }

    @Override
    public void onSwipeLeft() {
        Glide.with(this).load("https://drbanuaksoy.com/wp-content/uploads/2022/02/banu_ccexpress-1024x1024.png").into(zoomClass);
    }
}