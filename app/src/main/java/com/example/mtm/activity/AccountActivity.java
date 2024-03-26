package com.example.mtm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mtm.R;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.CurrentUserResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.Logger;
import com.example.mtm.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = "AccountActivity";

    PreferenceManager preferenceManager;

    private List<Integer> ids; // List of IDs
    private List<String> names; // List of names
    private LinearLayout buttonContainer;
    private Button selectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        preferenceManager = new PreferenceManager(getApplicationContext());

        // Initialize lists
        ids = new ArrayList<>();
        names = new ArrayList<>();

        int [] savedIds = preferenceManager.getIntArray(Constants.KEY_COSTUMER_ID_ARRAY);
        String [] savedNames = preferenceManager.getStringArray(Constants.KEY_COSTUMER_NAME_ARRAY);

        for (int i = 0; i < savedIds.length; i++) {
            ids.add(savedIds[i]);
            names.add(savedNames[i]);
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
                    preferenceManager.putString(Constants.KEY_CURRENT_COSTUMER_NAME, ((Button) v).getText().toString());
                    selectedButton.setTextColor(getColor(R.color.white));

                    getCurrentUser((Integer) v.getTag());

                }
            });

            // Set margins to create space between buttons
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(30, 20, 30, 20); // Adjust the right margin as needed
            button.setLayoutParams(params);


            // Check if the button's tag is 5 and set it as selected by default
            if (button.getTag().equals(preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID))) {
                button.setSelected(true);
                button.setTextColor(getColor(R.color.white));
                selectedButton = button;
            }

            buttonContainer.addView(button);
        }

    }

    private void getCurrentUser(int customerId) {

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

        Call<CurrentUserResponse> call = apiService.getCurrentUser(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                customerId);


//        Logger.getInstance().logDebug(TAG, "getToken", 1, fields);

        call.enqueue(new Callback<CurrentUserResponse>() {
            @Override
            public void onResponse(Call<CurrentUserResponse> call, Response<CurrentUserResponse> response) {

                Logger.getInstance().logDebug(TAG, "getCurrentUser", 2, response.body());

                if (response.isSuccessful()) {

                    CurrentUserResponse tokenModel = response.body();
                    if (tokenModel != null) {


                        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());


                        preferenceManager.putInt(Constants.KEY_CURRENT_COSTUMER_PM, tokenModel.getData().getDataSource().getPM());
                        preferenceManager.putInt(Constants.KEY_CURRENT_COSTUMER_DM, tokenModel.getData().getDataSource().getDM());
                        preferenceManager.putInt(Constants.KEY_CURRENT_COSTUMER_BC, tokenModel.getData().getDataSource().getBC());
                        preferenceManager.putInt(Constants.KEY_CURRENT_COSTUMER_NA, tokenModel.getData().getDataSource().getNA());


                        Intent i = new Intent(AccountActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    }

                } else {

                    Toast.makeText(AccountActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CurrentUserResponse> call, Throwable t) {

                Toast.makeText(AccountActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                Logger.getInstance().logDebug(TAG, "getCurrentUser", 3, t.getMessage());
            }
        });

    }

}