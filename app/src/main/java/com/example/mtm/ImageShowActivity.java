package com.example.mtm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;


public class ImageShowActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);

        Intent intent = getIntent();
        // Get the value passed from FirstActivity
        String valueReceived = intent.getStringExtra("imageUrl");

        ZoomageView zoomageView = findViewById(R.id.imageView);

        Glide.with(this).load(valueReceived).into(zoomageView);



    }
}