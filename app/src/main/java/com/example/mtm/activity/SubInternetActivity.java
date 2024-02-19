package com.example.mtm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.SubInternetAdapter;
import com.example.mtm.adapter.SubVisualAdapter;
import com.example.mtm.model.SubInternetModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.InternetSubResponse;
import com.example.mtm.response.SubMenuVisualMediaResponse;
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
    LinearLayout test12;
    private ProgressBar progressBar2;
    ViewGroup includedLayout;
    private TextView sourceTextView;
    String sourceUrl;
    private String menuId, subMenuId, startDate, endDate;
    private int count;

    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_internet);

        init();
        setItemClickListeners();
//        setData();
        setData2();
        setTypesList2();
//        OnBackPressedDispatcher onBackPressedDispatcher = this.getOnBackPressedDispatcher();
//        onBackPressedDispatcher.addCallback(this, onBackPressedCallback);



    }

    private void init() {
        backIconImageView = findViewById(R.id.backIconImageView);
        recyclerView = findViewById(R.id.recyclerView);
        webView = findViewById(R.id.webView2);
        test12 = findViewById(R.id.test12);
        progressBar2 = findViewById(R.id.progressBar2);
        includedLayout = findViewById(R.id.web_view_layout);
        sourceTextView = findViewById(R.id.sourceTextView);
        items = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        Intent intent = getIntent();
        // Get the value passed from FirstActivity
        menuId = intent.getStringExtra("menuId");
        subMenuId = intent.getStringExtra("subMenuId");
        startDate = intent.getStringExtra("startDate");
        endDate = intent.getStringExtra("endDate");
        count = intent.getIntExtra("count", 0);

        sourceUrl = "";
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());

        sourceTextView.setOnClickListener(view -> {
            MyUtils.openLink(sourceUrl, this);
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

        if (pageNumber == 0) {
            x = items.size();
            adapter = new SubInternetAdapter(this, items, this, x == count);
            recyclerView.setAdapter(adapter);
        } else {

            int y = x;
            x = x + items.size();

            if (x >= count) {
                adapter.setAllList(true);}

            adapter.addItems(items);

            if (items.size() < 5) {
                recyclerView.scrollToPosition(x - 1);
            }
            else {
                recyclerView.scrollToPosition(y + 3);
            }

        }
        
    }

    private void setData2() {
        SubMenuVisualMediaResponse result = DataHolder.getInstance().getSubMenuVisualMediaModel();

        List<SubInternetModel> items = new ArrayList<>();


        for (int i = 0; i < result.getData().getDocs().size(); i++) {

            items.add(new SubInternetModel(
                    result.getData().getDocs().get(i).getTitle(),
                    result.getData().getDocs().get(i).getMedia().getName(),
                    "",
                    result.getData().getDocs().get(i).getPublishDate(),
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getMedia().getLogo(),
                    Constants.KEY_VIDEO_BASIC_URL + result.getData().getDocs().get(i).getVideo()
            ));

        }


//        for (int i = 0; i < result.getData().getDocs().size(); i++) {
//            items.add(new SubInternetModel(
//                    result.getData().getDocs().get(i).getTitle(),
//                    result.getData().getDocs().get(i).getMedia().getType().getName(),
//                    "",
//                    result.getData().getDocs().get(i).getPublishDate(),
//                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageStoragePath(),
//                    result.getData().getDocs().get(i).getUrl()
//            ));
//        }

        if (pageNumber == 0) {
            x = items.size();
            adapter = new SubInternetAdapter(this, items, this, x == count);
            recyclerView.setAdapter(adapter);
        } else {

            int y = x;
            x = x + items.size();

            if (x >= count) {
                adapter.setAllList(true);}

            adapter.addItems(items);

            if (items.size() < 5) {
                recyclerView.scrollToPosition(x - 1);
            }
            else {
                recyclerView.scrollToPosition(y + 3);
            }

        }

    }

    private void setTypesList2() {

        SubMenuVisualMediaResponse result = DataHolder.getInstance().getSubMenuVisualMediaModel();

        List<SubInternetModel> items;

        items = new ArrayList<>();

        for (int i = 0; i < result.getData().getDocs().size(); i++) {

            items.add(new SubInternetModel(
                    result.getData().getDocs().get(i).getTitle(),
                    result.getData().getDocs().get(i).getMedia().getName(),
                    "",
                    result.getData().getDocs().get(i).getPublishDate(),
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getMedia().getLogo(),
                    Constants.KEY_VIDEO_BASIC_URL + result.getData().getDocs().get(i).getVideo()
            ));

        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new SubInternetAdapter(this, items, this, x == count);
        recyclerView.setAdapter(adapter);
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

                Logger.getInstance().logDebug(TAG, "SubMenuVisualMedia", 3, t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(String mediaPath) {
        // Show the included layout containing the WebView and ProgressBar
        includedLayout.setVisibility(View.VISIBLE);
        test12.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        // Load the URL in the WebView
        webView.loadUrl(mediaPath);

        // Set up a WebViewClient to handle page loading events
        webView.setWebViewClient(new WebViewClient() {
            // Show ProgressBar when page starts loading
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar2.setVisibility(View.VISIBLE);
            }

            // Hide ProgressBar when page finishes loading
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar2.setVisibility(View.GONE);
            }
        });

        sourceUrl = mediaPath;
    }

    @Override
    public void onShowMore() {
        pageNumber++;
        SubInternet(menuId, subMenuId, startDate, endDate);
    }

    public interface yaser {
        void onShowMore();
    }

    @Override
    public void onBackPressed() {

        if (includedLayout.getVisibility() == View.VISIBLE) {
            includedLayout.setVisibility(View.INVISIBLE);
            test12.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            super.onBackPressed();
        }

    }

//    OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
//        @Override
//        public void handleOnBackPressed() {
//
//        }
//    };
//
}