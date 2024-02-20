package com.example.mtm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mtm.R;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.ColumnistsResponse;
import com.example.mtm.response.NotificationsResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.Logger;
import com.example.mtm.util.MyUtils;
import com.example.mtm.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    private static final String TAG = "NotificationActivity";

    private List<Integer> ids; // List of IDs
    private List<String> names; // List of names
    private LinearLayout buttonContainer;
    private Button selectedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getNotifications();
    }

    private void getNotifications() {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<NotificationsResponse> call = apiService.getNotifications(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                22632,
                0,
                5
        );

        call.enqueue(new Callback<NotificationsResponse>() {
            @Override
            public void onResponse(Call<NotificationsResponse> call, Response<NotificationsResponse> response) {

                Logger.getInstance().logDebug(TAG, "getNotifications", 2, response.body());


                if (response.isSuccessful()) {



                    NotificationsResponse result = response.body();

                    DataHolder.getInstance().setNotificationsResponse(result);


                    setData();

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
            public void onFailure(Call<NotificationsResponse> call, Throwable t) {

                Logger.getInstance().logDebug(TAG, "getNotifications", 3, t.getMessage());
                Logger.getInstance().logDebug(TAG, "getNotifications", 3, t.getCause());
            }
        });


    }

    private void setData() {

        NotificationsResponse result = DataHolder.getInstance().getNotificationsResponse();

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());





        // Initialize lists
        ids = new ArrayList<>();
        names = new ArrayList<>();


        for (int i = 0; i < result.getData().size(); i++) {
            ids.add(1);
            names.add(result.getData().get(i).getTitle());
        }



        buttonContainer = findViewById(R.id.buttonContainer);

        // Dynamically create buttons and add them to the layout
        for (int i = 0; i < ids.size(); i++) {
            Button button = new Button(this);
            button.setText(names.get(i));
            button.setTag(ids.get(i)); // Set tag to identify button
            button.setTextColor(getColor(R.color.black)); // Set tag to identify button
            button.setBackgroundResource(R.drawable.button_background); // Apply button style
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Change background color when clicked
                    if (selectedButton != null) {
                        selectedButton.setSelected(false); // Deselect previously selected button
                        selectedButton.setTextColor(getColor(R.color.black));
                    }
                    selectedButton = (Button) v;
                    selectedButton.setSelected(true); // Select clicked button
                    preferenceManager.putInt(Constants.KEY_CURRENT_COSTUMER_ID, (Integer) v.getTag());
                    selectedButton.setTextColor(getColor(R.color.white));
                }
            });

            // Set margins to create space between buttons
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(30, 20, 30, 20); // Adjust the right margin as needed
            button.setLayoutParams(params);


//            // Check if the button's tag is 5 and set it as selected by default
//            if (button.getTag().equals(preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID))) {
//                button.setSelected(true);
//                button.setTextColor(getColor(R.color.white));
//                selectedButton = button;
//            }

            buttonContainer.addView(button);
        }

    }

}