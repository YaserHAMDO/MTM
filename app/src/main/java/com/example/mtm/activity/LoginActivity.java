package com.example.mtm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mtm.R;
import com.example.mtm.adapter.ViewPagerAdapter;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.TokenResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.Logger;
import com.example.mtm.util.PreferenceManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    ViewPagerAdapter mViewPagerAdapter;

    TextInputLayout userNameTextInputLayout;
    MaterialButton loginMaterialButton;
    TextView requiredUserNameTextView, requiredPasswordTextView;
    EditText usernameEditText, passwordEditText;
    ViewPager mViewPager;

    private Handler handler;
    int page=0;
    private int delay = 2000; //milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

//        userNameTextInputLayout.setHelperTextEnabled(false);

        handler = new Handler(Looper.getMainLooper());

        CircleIndicator indicator = findViewById(R.id.indicator);

        int[] images = {
                R.drawable.test1,
                R.drawable.test1,
                R.drawable.test1
        };

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(getApplication(), images);
        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);
//        handler = new Handler(Looper.getMainLooper());
        indicator.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                page = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        loginMaterialButton.setOnClickListener(view -> {

            String userName = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            
            if (!userName.equals("") && !password.equals("")) {
                getToken();
            }
            else {
                if (userName.equals("")) {
                    requiredUserNameTextView.setVisibility(View.VISIBLE);  
                }
                if (password.equals("")) {
                    requiredPasswordTextView.setVisibility(View.VISIBLE);
                }
            }

        });


        usernameEditText.addTextChangedListener(new TextWatcher()  {
            
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String s = usernameEditText.getText().toString();
                if (s.length() > 0) {
                    requiredUserNameTextView.setVisibility(View.INVISIBLE);
                }
            }
        });
        
        passwordEditText.addTextChangedListener(new TextWatcher()  {
            
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String s = passwordEditText.getText().toString();
                if (s.length() > 0) {
                    requiredPasswordTextView.setVisibility(View.INVISIBLE);
                }
            }
        });


        ImageView test = findViewById(R.id.test);
        test.setOnClickListener(view -> {
            usernameEditText.setText("yasershareef1995@gmail.com");
            passwordEditText.setText("yas12345");
//            usernameEditText.setText("mesutaygun35@icloud.com");
//            passwordEditText.setText("Picasso1881");
        });



    }

    private void getToken() {

        String userName = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        ApiService apiService = RetrofitClient.getClient(1).create(ApiService.class);

        Map<String, String> fields = new HashMap<>();
        fields.put("client_id", "account-mobile");
        fields.put("client_secret", "6323a00d-974f-46b9-974d-37e9a1588c59");
        fields.put("grant_type", "password");
        fields.put("username", userName);
        fields.put("password", password);

        Call<TokenResponse> call = apiService.getToken(fields);

        Logger.getInstance().logDebug(TAG, "getToken", 1, fields);

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                Logger.getInstance().logDebug(TAG, "getToken", 2, response.body());

                if (response.isSuccessful()) {

                    TokenResponse tokenModel = response.body();
                    if (tokenModel != null) {

                        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
                        preferenceManager.putString(Constants.KEY_ACCESS_TOKEN, tokenModel.getAccessToken());
                        preferenceManager.putString(Constants.KEY_REFRESH_TOKEN, tokenModel.getRefreshToken());

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    }

                }
                else {

                    Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

                Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                Logger.getInstance().logDebug(TAG, "getToken", 3, t.getMessage());
            }
        });
    }

    private void init() {
        userNameTextInputLayout = findViewById(R.id.userNameTextInputLayout);
        loginMaterialButton = findViewById(R.id.loginMaterialButton);
        requiredUserNameTextView = findViewById(R.id.requiredUserNameTextView);
        requiredPasswordTextView = findViewById(R.id.requiredPasswordTextView);
        usernameEditText = findViewById(R.id.edit_username);
        passwordEditText = findViewById(R.id.passwordEditText);
        mViewPager = findViewById(R.id.viewPagerMaina);

    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delay);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    Runnable runnable = new Runnable() {
        public void run() {

            if (mViewPagerAdapter.getCount() == page) {
                page = 0;
            }
            else {
                page++;
            }

            mViewPager.setCurrentItem(page, true);
            handler.postDelayed(this, delay);
        }
    };
}