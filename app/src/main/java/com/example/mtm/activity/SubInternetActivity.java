package com.example.mtm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.SubInternetAdapter;
import com.example.mtm.model.SubInternetModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.InternetSubResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.Logger;
import com.example.mtm.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubInternetActivity extends AppCompatActivity implements SubInternetAdapter.OnItemClickListener {

    private static final String TAG = "SubInternetActivity";

    private int pageNumber = 0;
    private int pageSize = 10;
    private boolean isLoading = false;
    private ImageView backIconImageView;
    private RecyclerView recyclerView;
    private WebView webView;
    List<SubInternetModel> items;

    private SubInternetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_internet);

        init();
        setItemClickListeners();
        setData();


        Intent intent = getIntent();
        // Get the value passed from FirstActivity
        String menuId = intent.getStringExtra("menuId");
        String subMenuId = intent.getStringExtra("subMenuId");
        String startDate = intent.getStringExtra("startDate");
        String endDate = intent.getStringExtra("endDate");


        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            // Increment page number before making the API call
            pageNumber++;
            SubInternet(menuId, subMenuId, startDate, endDate);
        });
    }

    private void init() {
        backIconImageView = findViewById(R.id.backIconImageView);
        recyclerView = findViewById(R.id.recyclerView);
        webView = findViewById(R.id.webView);
        items = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
    }


    private void setData() {
        InternetSubResponse result = DataHolder.getInstance().getInternetSubModel();
        List<SubInternetModel> items = new ArrayList<>(); // Initialize or clear items list if needed

        for (int i = 0; i < result.getData().getDocs().size(); i++) {
            items.add(new SubInternetModel(
                    result.getData().getDocs().get(i).getTitle(),
                    result.getData().getDocs().get(i).getMedia().getType().getName(),
                    "",
                    result.getData().getDocs().get(i).getPublishDate(),
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageStoragePath(),
                    result.getData().getDocs().get(i).getUrl()
            ));
        }

        // If pageNumber is 0, it means it's the first page, so set adapter
        if (pageNumber == 0) {
            adapter = new SubInternetAdapter(this, items, this);
            recyclerView.setAdapter(adapter);
        } else {
            // If it's not the first page, append data to the existing adapter
            // Assuming you have a method in your adapter to add more data
            adapter.addItems(items);
//            recyclerView.getAdapter().addItems(items);
        }
    }

    private void SubInternet(String menuId, String subMenuId, String startDate, String endDate) {
        // Check if already loading
        if (isLoading) {
            return;
        }

        isLoading = true;

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<InternetSubResponse> call = apiService.subInternet(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                pageNumber, // Use pageNumber for pagination
                pageSize,   // Use pageSize for pagination
                22632,
                true,
                true,
                false,
                false,
                false,
                true,
                true,
                true,
                true,
                startDate,
                endDate,
                "07:00:00",
                "23:59:00",
                "UNIGNORED",
                true,
                menuId,
                subMenuId,
                false,
                false
        );
        call.enqueue(new Callback<InternetSubResponse>() {
            @Override
            public void onResponse(Call<InternetSubResponse> call, Response<InternetSubResponse> response) {
                isLoading = false; // Reset loading flag
                if (response.isSuccessful()) {
                    InternetSubResponse result = response.body();
                    DataHolder.getInstance().setInternetSubModel(result);
                    setData();
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<InternetSubResponse> call, Throwable t) {
                isLoading = false; // Reset loading flag
                Logger.getInstance().logDebug(TAG, "SubMenuVisualMedia", 3, t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(String mediaPath) {

        webView.loadUrl(mediaPath);
        webView.setVisibility(View.VISIBLE);
//        Intent intent = new Intent(this, VideoActivity.class);
//        intent.putExtra("videoUrl", mediaPath);
//        startActivity(intent);
    }
}