package com.example.mtm.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mtm.R;
import com.example.mtm.util.ZoomClass;

public class ProfileActivity extends AppCompatActivity {

    private ImageView backIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        setItemClickListeners();


        ZoomClass zoomImageView = findViewById(R.id.zoomImageView);

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

}