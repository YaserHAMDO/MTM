package com.medyatakip.app.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.medyatakip.app.R;
import com.medyatakip.app.util.Constants;
import com.medyatakip.app.util.PreferenceManager;

@SuppressLint("CustomSplashScreen")
public class LaunchScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);



        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            Intent i;

            PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

            String token = preferenceManager.getString(Constants.KEY_ACCESS_TOKEN);

            if(token != null && !token.equals("")) {

                i = new Intent(this, MainActivity.class);
            }

            else {
                preferenceManager.clear();
                i = new Intent(this, LoginActivity.class);
            }

            Bundle options = ActivityOptions.makeCustomAnimation(this, 0, R.anim.launch_screen_animation).toBundle();
            startActivity(i, options);
            finish();


        }, 500);

    }
}