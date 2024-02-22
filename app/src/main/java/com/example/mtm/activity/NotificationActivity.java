package com.example.mtm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mtm.R;
import com.example.mtm.adapter.ColumnistAdapter;
import com.example.mtm.adapter.NotificationsAdapter;
import com.example.mtm.adapter.SubInternetAdapter;
import com.example.mtm.model.ColumnistModel;
import com.example.mtm.model.NotificationsModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.request.MarkAsReadRequestBody;
import com.example.mtm.response.ColumnistsResponse;
import com.example.mtm.response.MarkAsReadResponse;
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

public class NotificationActivity extends AppCompatActivity implements NotificationsAdapter.OnItemClickListener{

    private static final String TAG = "NotificationActivity";

    private List<Integer> ids; // List of IDs
    private List<String> names; // List of names
    private LinearLayout buttonContainer;
    private Button selectedButton;

    private RecyclerView recyclerView;

    private FrameLayout frameLayout;
    private Button button;
    private Button okundu;
    private ProgressBar progressBar;
    private NotificationsAdapter adapter;

    private ImageView backIconImageView;

    private int pageNumber = 0;
    private int pageSize = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        init();
//        setData2();
        setItemClickListeners();
        getNotifications();
    }

    private void init() {
        backIconImageView = findViewById(R.id.backIconImageView);
        recyclerView = findViewById(R.id.recyclerView);
        frameLayout = findViewById(R.id.frameLayout);
        button = findViewById(R.id.button);
        okundu = findViewById(R.id.okundu);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));


    }
    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        okundu.setOnClickListener(view -> setRead(1));
        button.setOnClickListener(view -> {
            pageNumber++;
            getNotifications();
        });

    }


    private void setData2() {

//        columnistsShowArray.clear();

        NotificationsResponse result = DataHolder.getInstance().getNotificationsResponse();

        List<NotificationsModel> items = new ArrayList<>();

        for (int i = 0; i < result.getData().size(); i++) {

            String url = "";
            boolean read = true;

            if (result.getData().get(i).getData().getUrl() != null) {
                url = result.getData().get(i).getData().getUrl();
            }


            items.add(new NotificationsModel(
                    result.getData().get(i).getTitle(),
                    result.getData().get(i).getBody(),
                    url,
                    result.getData().get(i).getId(),
                    result.getData().get(i).getRtime() != null
            ));

//            columnistsShowArray.add(Constants.KEY_IMAGE_BASIC_URL +  result.getData().getDocs().get(i).getImageStoragePath());

        }
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//        recyclerView.setAdapter(new NotificationsAdapter(this, items, this));

//        filteredDateTextView.setText(startDate + " ile " + endDate + " arasında tarihi kayıtlar gösterilmektedir.");



        if (pageNumber == 0) {
            adapter = new NotificationsAdapter(this, items, this);
            recyclerView.setAdapter(adapter);
        } else {

            adapter.addItems(items);

        }

        recyclerView.scrollToPosition(adapter.getItemCount() - 1);


    }


    private void getNotifications() {

        progressBar.setVisibility(View.VISIBLE);
        button.setVisibility(View.INVISIBLE);

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<NotificationsResponse> call = apiService.getNotifications(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
                pageNumber, // Use pageNumber for pagination
                pageSize
                );

        call.enqueue(new Callback<NotificationsResponse>() {
            @Override
            public void onResponse(Call<NotificationsResponse> call, Response<NotificationsResponse> response) {

                Logger.getInstance().logDebug(TAG, "getNotifications", 2, response.body());


                progressBar.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);


                if (response.isSuccessful()) {



                    NotificationsResponse result = response.body();

                    DataHolder.getInstance().setNotificationsResponse(result);


                    setData2();

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

                progressBar.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);

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

    @Override
    public void onItemClick(String title, String body, String link, int id) {

        Intent intent = new Intent(this, NotificationContentActivity.class);

        intent.putExtra("title", title);
        intent.putExtra("body", body);
        intent.putExtra("link", link);
        intent.putExtra("id", id);

        Bundle options = ActivityOptions.makeCustomAnimation(this, R.anim.left, R.anim.right).toBundle();
        startActivity(intent, options);
        finish();
//        setRead(index);
    }

    private void setRead(int index) {

        pageNumber = 0;

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        MarkAsReadRequestBody requestBody = new MarkAsReadRequestBody(preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID));

        Call<MarkAsReadResponse> call = apiService.markAllNotificationAsRead(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID)
        );

        call.enqueue(new Callback<MarkAsReadResponse>() {
            @Override
            public void onResponse(Call<MarkAsReadResponse> call, Response<MarkAsReadResponse> response) {

                Logger.getInstance().logDebug(TAG, "getNotifications", 2, response.body());


                progressBar.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);


                if (response.isSuccessful()) {

                    Toast.makeText(NotificationActivity.this, "Hepsi okundu.", Toast.LENGTH_SHORT).show();
                    getNotifications();
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
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}