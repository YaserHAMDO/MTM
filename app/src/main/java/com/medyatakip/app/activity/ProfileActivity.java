package com.medyatakip.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.medyatakip.app.R;
import com.medyatakip.app.network.ApiService;
import com.medyatakip.app.network.RetrofitClient;
import com.medyatakip.app.request.DeviceRegisterRequest;
import com.medyatakip.app.response.DeviceRegisterResponse;
import com.medyatakip.app.util.Constants;
import com.medyatakip.app.util.DataHolder;
import com.medyatakip.app.util.Logger;
import com.medyatakip.app.util.MyUtils;
import com.medyatakip.app.util.PreferenceManager;
import com.medyatakip.app.util.ZoomClass;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity implements ZoomClass.ZoomClassListener{

    private static final String TAG = "ProfileActivity";

    private PreferenceManager preferenceManager;

    private ImageView backIconImageView;
    private ImageView notificationImageView;
    private EditText usernameEditText, passwordEditText;

    ZoomClass zoomClass;
    private MaterialButton logoutMaterialButton;
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

    @Override
    protected void onResume() {
        super.onResume();
        usernameEditText.setText(preferenceManager.getString(Constants.KEY_CURRENT_COSTUMER_NAME));
        passwordEditText.setText(preferenceManager.getString(Constants.KEY_CURRENT_COSTUMER_EMAIL));

    }

    private void init() {
        backIconImageView = findViewById(R.id.backIconImageView);
        notificationImageView = findViewById(R.id.notificationImageView);
        logoutMaterialButton = findViewById(R.id.logoutMaterialButton);
        usernameEditText = findViewById(R.id.edit_username);
        passwordEditText = findViewById(R.id.passwordEditText);

        preferenceManager = new PreferenceManager(getApplicationContext());


        // Get references to FrameLayouts
        FrameLayout facebookFrameLayout = findViewById(R.id.facebookFrameLayout);
        FrameLayout twitterFrameLayout = findViewById(R.id.twitterFrameLayout);
        FrameLayout instagramFrameLayout = findViewById(R.id.instagramFrameLayout);
        FrameLayout youtubeFrameLayout = findViewById(R.id.youtubeFrameLayout);
        FrameLayout linkedInFrameLayout = findViewById(R.id.linkedInFrameLayout);


        // Set onClickListeners for each FrameLayout
        facebookFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.facebook.com/MTM.MedyaTakipMerkezi");
            }
        });

        twitterFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://twitter.com/MTM_MedyaTakip");
            }
        });

        instagramFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.instagram.com/mtmmedyatakipmerkezi");
            }
        });

        youtubeFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.youtube.com/channel/UCTw-iMd5MM1BpJYijVdL8JA");
            }
        });

        linkedInFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink( "https://www.linkedin.com/company/mtm-medya-takip-merkezi");
            }
        });




    }
    private void openLink(String url) {
        MyUtils.openLink( url, ProfileActivity.this);
    }


    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        logoutMaterialButton.setOnClickListener(view -> logoutApi());

        notificationImageView.setOnClickListener(view -> {
            Intent i = new Intent(ProfileActivity.this, AccountActivity.class);
            startActivity(i);
        });
    }

    private void logout() {
        preferenceManager.clear();
        DataHolder.getInstance().clearDataHolderClass();
        Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void logoutApi() {

        ApiService apiService = RetrofitClient.getClient(1).create(ApiService.class);

        Map<String, String> fields = new HashMap<>();
        fields.put("client_id", "account-mobile");
        fields.put("client_secret", "6323a00d-974f-46b9-974d-37e9a1588c59");
        fields.put("refresh_token", preferenceManager.getString(Constants.KEY_REFRESH_TOKEN));

        Call<Void> call = apiService.logout(preferenceManager.getString(Constants.KEY_ACCESS_TOKEN), fields);

        Logger.getInstance().logDebug(TAG, "logout", 1, fields);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Logger.getInstance().logDebug(TAG, "logout", 2, response.body());

                if (response.isSuccessful()) {
                    int [] savedIds = preferenceManager.getIntArray(Constants.KEY_COSTUMER_ID_ARRAY);

                    for (int i = 0; i < savedIds.length; i++) {
                        deviceUnRegister(savedIds[i], preferenceManager.getString(Constants.KEY_FCM));
                    }

                    logout();
                }
                else {

                    Toast.makeText(ProfileActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(ProfileActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                Logger.getInstance().logDebug(TAG, "logout", 3, t.getMessage());
            }
        });
    }


    @Override
    public void onSwipeRight() {
        Glide.with(this).load("https://debis.deu.edu.tr/akademiktr/resim_jpg.php?a=1992&p=379").into(zoomClass);
    }

    @Override
    public void onSwipeLeft() {
        Glide.with(this).load("https://drbanuaksoy.com/wp-content/uploads/2022/02/banu_ccexpress-1024x1024.png").into(zoomClass);
    }

    @Override
    public void onSwipeDown() {
        getOnBackPressedDispatcher().onBackPressed();
    }

    @Override
    public void onSwipeUp() {

    }

    @Override
    public void onSingleTapUp() {

    }


    private void deviceUnRegister(int customerID, String fcm) {

        String BASE_URL = "https://web.medyatakip.com/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        DeviceRegisterRequest deviceInfo = new DeviceRegisterRequest(customerID, fcm, "android");

        Call<DeviceRegisterResponse> call = apiService.deviceUnRegister("Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN), deviceInfo);
        call.enqueue(new Callback<DeviceRegisterResponse>() {
            @Override
            public void onResponse(Call<DeviceRegisterResponse> call, Response<DeviceRegisterResponse> response) {
                Logger.getInstance().logDebug(TAG, "deviceRegister", 2, response.body());

                if (response.isSuccessful()) {
                    DeviceRegisterResponse apiResponse = response.body();


                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<DeviceRegisterResponse> call, Throwable t) {
                // Handle failure
            }
        });


    }


}