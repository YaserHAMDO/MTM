package com.example.mtm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private ProgressBar progressBar;
    private Button rotateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        // Initialize views
        videoView = findViewById(R.id.videoView);
        progressBar = findViewById(R.id.progressBar);
        rotateButton = findViewById(R.id.rotateButton);

        Intent intent = getIntent();
        // Get the value passed from FirstActivity
        String valueReceived = intent.getStringExtra("videoUrl");



        // Load video
//        String videoUrl = "https://web.medyatakip.com/vdsrv/store/arc/gb/2024/01/24/h/0abb0fc833d0.mp4";
//        Uri videoUri = Uri.parse(videoUrl);
//        videoView.setVideoURI(videoUri);


        // Set up the video view
        videoView.setVideoPath(valueReceived);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Show progress bar while loading video
        progressBar.setVisibility(View.VISIBLE);
        videoView.setOnPreparedListener(mp -> {
            // Hide progress bar when video is prepared
            progressBar.setVisibility(View.GONE);
            // Start playing the video
            videoView.start();
        });

        // Button click listener to rotate the screen
        rotateButton.setOnClickListener(v -> {
            // Set screen orientation to landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            // Extend video to full screen
            videoView.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT));
        });
    }
}