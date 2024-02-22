package com.example.mtm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mtm.R;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.request.MarkAsReadRequestBody;
import com.example.mtm.response.MarkAsReadResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.Logger;
import com.example.mtm.util.MyUtils;
import com.example.mtm.util.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationContentActivity extends AppCompatActivity {

    private static final String TAG = "NotificationContentActivity";

    private ImageView backIconImageView;
    private TextView titleTextView, bodyTextView;
    private Button button;
    private CardView notificationImageView;
    private String title, body, link;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_content);

        init();
        initValues();
        setItemClickListeners();
        setDate();
    }

    private void init() {
        backIconImageView = findViewById(R.id.backIconImageView);
        notificationImageView = findViewById(R.id.notificationImageView);
        titleTextView = findViewById(R.id.titleTextView);
        bodyTextView = findViewById(R.id.bodyTextView);
        button = findViewById(R.id.button);
    }

    private void initValues() {

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        body = intent.getStringExtra("body");
        link = intent.getStringExtra("link");
        id = intent.getIntExtra("id", 0);

        if(link == null || link.equals("")) {
            notificationImageView.setVisibility(View.INVISIBLE);
        }
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        notificationImageView.setOnClickListener(view -> MyUtils.openLink(link, this));
        button.setOnClickListener(view -> markAsRead());
    }

    private void setDate() {
        titleTextView.setText(title);
        bodyTextView.setText(body);
    }

    private void markAsRead() {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        MarkAsReadRequestBody requestBody = new MarkAsReadRequestBody(preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID));

        Call<MarkAsReadResponse> call = apiService.markNotificationAsRead(
                id,
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID)
        );

        call.enqueue(new Callback<MarkAsReadResponse>() {
            @Override
            public void onResponse(Call<MarkAsReadResponse> call, Response<MarkAsReadResponse> response) {

                Logger.getInstance().logDebug(TAG, "getNotifications", 2, response.body());


//                progressBar.setVisibility(View.INVISIBLE);
//                button.setVisibility(View.VISIBLE);


                if (response.isSuccessful()) {

                    Toast.makeText(NotificationContentActivity.this, "Okundu.", Toast.LENGTH_SHORT).show();

                } else {

                    if (response.code() == 403) {
//                        forbiddenPopup();
                    }

                    else {
//                        refreshToken2(8);
                    }

                }
            }

            @Override
            public void onFailure(Call<MarkAsReadResponse> call, Throwable t) {

                Logger.getInstance().logDebug(TAG, "getNotifications", 3, t.getMessage());
                Logger.getInstance().logDebug(TAG, "getNotifications", 3, t.getCause());

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, NotificationActivity.class);

        intent.putExtra("title", title);
        intent.putExtra("body", body);
        intent.putExtra("link", link);
        intent.putExtra("id", id);

        Bundle options = ActivityOptions.makeCustomAnimation(this, R.anim.left_to_right, R.anim.right_to_left).toBundle();
        startActivity(intent, options);


    }
}