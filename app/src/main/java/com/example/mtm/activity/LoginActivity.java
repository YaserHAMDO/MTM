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
import com.example.mtm.model.SliderModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.AccountResponse;
import com.example.mtm.response.TokenResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.Logger;
import com.example.mtm.util.PreferenceManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private ViewPagerAdapter mViewPagerAdapter;

    private MaterialButton loginMaterialButton;
    private TextView requiredUserNameTextView, requiredPasswordTextView;
    private EditText usernameEditText, passwordEditText;
    private ViewPager mViewPager;
    private ImageView test;
    private CircleIndicator indicator;

    private Handler handler;
    private int page = 0;
    private final int delay = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setItemClickListeners();
    }

    private void init() {
        loginMaterialButton = findViewById(R.id.loginMaterialButton);
        requiredUserNameTextView = findViewById(R.id.requiredUserNameTextView);
        requiredPasswordTextView = findViewById(R.id.requiredPasswordTextView);
        usernameEditText = findViewById(R.id.edit_username);
        passwordEditText = findViewById(R.id.passwordEditText);
        mViewPager = findViewById(R.id.viewPagerMaina);
        test = findViewById(R.id.test);
        indicator = findViewById(R.id.indicator);

        handler = new Handler(Looper.getMainLooper());

        List<SliderModel> sliderModels = new ArrayList<>();
        sliderModels.add(new SliderModel("https://app.medyatakip.com/assets/slide/slider1.png" , "https://www.medyatakip.com/mLink.php?p=1"));
        sliderModels.add(new SliderModel("https://app.medyatakip.com/assets/slide/slider2.png" , "https://www.medyatakip.com/mLink.php?p=2"));


        mViewPagerAdapter = new ViewPagerAdapter(this, sliderModels);
        mViewPager.setAdapter(mViewPagerAdapter);
        indicator.setViewPager(mViewPager);
    }

    private void setItemClickListeners() {

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
        fields.put("scope", "offline_access");
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
                        preferenceManager.putString(Constants.KEY_CURRENT_COSTUMER_EMAIL, userName);

                        getAccount(tokenModel.getAccessToken());



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

    private void getAccount(String token) {

//        String userName = usernameEditText.getText().toString();
//        String password = passwordEditText.getText().toString();

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

//        Map<String, String> fields = new HashMap<>();
//        fields.put("client_id", "account-mobile");
//        fields.put("client_secret", "6323a00d-974f-46b9-974d-37e9a1588c59");
//        fields.put("grant_type", "password");
//        fields.put("scope", "offline_access");
//        fields.put("username", userName);
//        fields.put("password", password);

        Call<AccountResponse> call = apiService.getAccount(
                "Bearer " + token);


//        Logger.getInstance().logDebug(TAG, "getToken", 1, fields);

        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {

                Logger.getInstance().logDebug(TAG, "getAccount", 2, response.body());

                if (response.isSuccessful()) {

                    AccountResponse tokenModel = response.body();
                    if (tokenModel != null) {


                        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());


                        preferenceManager.putInt(Constants.KEY_CURRENT_COSTUMER_ID, tokenModel.getData().get(0).getId());
                        preferenceManager.putString(Constants.KEY_CURRENT_COSTUMER_NAME, tokenModel.getData().get(0).getName());

                        int [] ids = new int[tokenModel.getData().size()];
                        String [] names = new String[tokenModel.getData().size()];

                        for (int i = 0; i < tokenModel.getData().size(); i++) {
                            ids[i] = tokenModel.getData().get(i).getId();
                            names[i] = tokenModel.getData().get(i).getName();
                        }

                        preferenceManager.putIntArray(Constants.KEY_COSTUMER_ID_ARRAY, ids);
                        preferenceManager.putStringArray(Constants.KEY_COSTUMER_NAME_ARRAY, names);


                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    }

                } else {

                    Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {

                Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                Logger.getInstance().logDebug(TAG, "getAccount", 3, t.getMessage());
            }
        });

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

    private final Runnable runnable = new Runnable() {
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