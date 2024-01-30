package com.example.mtm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.logging.LogRecord;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    ViewPagerAdapter mViewPagerAdapter;

    TextInputLayout userNameTextInputLayout;
    MaterialButton loginMaterialButton;
    TextView requiredUserNameTextView, requiredPasswordTextView;
    EditText usernameEditText, passwordEditText;
    ViewPager mViewPager;

    int[] images = {
            R.drawable.test1,
            R.drawable.test1,
            R.drawable.test1
    };

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
//                Toast.makeText(LoginActivity.this, ""  + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        loginMaterialButton.setOnClickListener(view -> {

            String userName = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            
            if (!userName.equals("") && !password.equals("")) {

                Intent i = new Intent(this, MainActivity.class);
//                Bundle options = ActivityOptions.makeCustomAnimation(this, 0, R.anim.launch_screen_animation).toBundle();
//                startActivity(i, options);
                startActivity(i);
                finish();

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


        getToken();

    }

    private void getToken() {


        ApiService apiService = RetrofitClient.getApiService();
        Call<TokenModel> call = apiService.getToken(
                "account-mobile",
                "6323a00d-974f-46b9-974d-37e9a1588c59",
                "password",
                "yasershareef1995@gmail.com",
                "yas12345"
                );
        // Make API call to get token
//        Call<ResponseBody> call = apiService.getToken(request);
        call.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "XXxx", Toast.LENGTH_SHORT).show();
                    TokenModel tokenModel = response.body();
                    if (tokenModel != null) {
                        // Token received, do something with it
//                        Log.d("TOKEN", "Access Token: " + tokenModel.getAccessToken());

                        Toast.makeText(LoginActivity.this, tokenModel.getAccessToken(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "failed\n" +  response.message() + "\n" + response.errorBody() + "\n"  + response.code(), Toast.LENGTH_SHORT).show();
                    // Request not successful, handle error
//                    Log.e("TOKEN", "Failed to get token. Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "zzzzzzzz", Toast.LENGTH_SHORT).show();
                // Request failed, handle error
//                Log.e("TOKEN", "Failed to get token. Error: " + t.getMessage());
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