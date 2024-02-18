package com.example.mtm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private FrameLayout frameLayout;
    private Button button;
    private ProgressBar progressBar;

    private String menuId, subMenuId, startDate, endDate;
    private int count;

    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_internet);

        init();
        setItemClickListeners();
        setData();





    }

    private void init() {
        backIconImageView = findViewById(R.id.backIconImageView);
        recyclerView = findViewById(R.id.recyclerView);
        webView = findViewById(R.id.webView);
        frameLayout = findViewById(R.id.frameLayout);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);

        items = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        Intent intent = getIntent();
        // Get the value passed from FirstActivity
        menuId = intent.getStringExtra("menuId");
        subMenuId = intent.getStringExtra("subMenuId");
        startDate = intent.getStringExtra("startDate");
        endDate = intent.getStringExtra("endDate");
        count = intent.getIntExtra("count", 0);
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());


        button.setOnClickListener(view -> {
            // Increment page number before making the API call

            button.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            pageNumber++;
            SubInternet(menuId, subMenuId, startDate, endDate);
        });
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
            x = items.size();
            adapter = new SubInternetAdapter(this, items, this, x == count);
            recyclerView.setAdapter(adapter);
        } else {
            // If it's not the first page, append data to the existing adapter
            // Assuming you have a method in your adapter to add more data

            int y = x;
            x = x + items.size();

//            Toast.makeText(this,  x  + "\n" + y, Toast.LENGTH_SHORT).show();

            if (x >= count) {
                adapter.setAllList(true);}

            adapter.addItems(items);
//            recyclerView.scrollToPosition(x - (items.size() - 3));


            if (items.size() < 5) {
                recyclerView.scrollToPosition(x - 1);
            }
            else {
                recyclerView.scrollToPosition(y + 3);
            }

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

                button.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                adapter.getHolder().getFrameLayout().setVisibility(View.GONE);



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

                button.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

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

    @Override
    public void onShowMore() {
        pageNumber++;
        SubInternet(menuId, subMenuId, startDate, endDate);
    }

    public interface yaser {
        void onShowMore();
    }
}