package com.example.mtm;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class LaunchScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);



        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            Intent i;

            if(true) {
//                preferenceManager.clear();
//                i = new Intent(this, NewLoginActivity.class);

//                startActivity(new Intent(LaunchScreenActivity.this, PhoneAuthActivityTest.class));
                i = new Intent(this, LoginActivity.class);
            }

            else {
//                preferenceManager.putString(Constants.KEY_CAME_FROM,"LaunchScreenActivityRefreshToken");
//                preferenceManager.putBoolean(Constants.KEY_LOCATION_TAKEN, false);
                i = new Intent(this, MainActivity.class);

            }

            Bundle options = ActivityOptions.makeCustomAnimation(this, 0, R.anim.launch_screen_animation).toBundle();
            startActivity(i, options);
            finish();


        }, 500);

    }
}