package com.example.mtm.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mtm.R;
import com.example.mtm.adapter.SubInternetAdapter;
import com.example.mtm.model.SubInternetModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.InternetSubResponse;
import com.example.mtm.response.PrintedMediaSubResponse;
import com.example.mtm.response.SubMenuVisualMediaResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.Logger;
import com.example.mtm.util.MyUtils;
import com.example.mtm.util.PreferenceManager;
import com.example.mtm.util.ZoomClass;
import com.jsibbold.zoomage.ZoomageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubInternetActivity extends AppCompatActivity implements SubInternetAdapter.OnItemClickListener, ZoomClass.ZoomClassListener {

    private static final String TAG = "SubInternetActivity";

    private int pageNumber = 0;
    private int pageSize = 10;
    private boolean isLoading = false;
    private ImageView backIconImageView, backIconImageView2;
    private RecyclerView recyclerView;
    private WebView webView;
    List<SubInternetModel> items;

    private SubInternetAdapter adapter;
    LinearLayout test12;
    private ProgressBar progressBar2;
    ViewGroup includedLayout;
    private TextView sourceTextView, titleTextView, tumSayfa;
    String sourceUrl;
    private String menuId, subMenuId, startDate, endDate;
    private int count;

    ZoomClass zoomClass;
    LinearLayout mesut;

    int x = 0;

    private int index;

    private ArrayList<String> printedMediaFullPageShowArray;
    private ArrayList<String> printedMediaSubPageShowArray;
    private ArrayList<String> printedMediaDateShowArray, printedMediaNamesShowArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_internet);

        init();
        setItemClickListeners();


        switch (index) {
            case 1:
                titleTextView.setText("Yazılı Basın");
                setPrintedMediaData();
                break;

            case 2:
                titleTextView.setText("Digital Basın");
                setData();
                break;

            case 3:
                titleTextView.setText(R.string.visual_media);
                setTypesList2();
                break;
        }




//        setData();


//        OnBackPressedDispatcher onBackPressedDispatcher = this.getOnBackPressedDispatcher();
//        onBackPressedDispatcher.addCallback(this, onBackPressedCallback);



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
                    Constants.KEY_VIDEO_BASIC_URL + result.getData().getDocs().get(i).getVideo(),
                    ""
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



    private void init() {
        backIconImageView = findViewById(R.id.backIconImageView);
        backIconImageView2 = findViewById(R.id.backIconImageView2);
        recyclerView = findViewById(R.id.recyclerView);
        webView = findViewById(R.id.webView2);
        test12 = findViewById(R.id.test12);
        progressBar2 = findViewById(R.id.progressBar2);
        includedLayout = findViewById(R.id.web_view_layout);
        mesut = findViewById(R.id.mesut);
        tumSayfa = findViewById(R.id.tumSayfa);
        sourceTextView = findViewById(R.id.sourceTextView);
        titleTextView = findViewById(R.id.titleTextView);
        items = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        Intent intent = getIntent();
        // Get the value passed from FirstActivity
        menuId = intent.getStringExtra("menuId");
        subMenuId = intent.getStringExtra("subMenuId");
        startDate = intent.getStringExtra("startDate");
        endDate = intent.getStringExtra("endDate");
        count = intent.getIntExtra("count", 0);
        index = intent.getIntExtra("index", 0);


        sourceUrl = "";


        zoomClass = findViewById(R.id.imageView);
        zoomClass.setZoomClassListener(this);

        printedMediaFullPageShowArray = new ArrayList<>();
        printedMediaSubPageShowArray = new ArrayList<>();
        printedMediaDateShowArray = new ArrayList<>();
        printedMediaNamesShowArray = new ArrayList<>();
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        backIconImageView2.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        titleTextView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());

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
                    result.getData().getDocs().get(i).getMedia().getName(),
                    "",
                    result.getData().getDocs().get(i).getPublishDate(),
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageStoragePath(),
                    result.getData().getDocs().get(i).getUrl(),
                    ""
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

    private void setPrintedMediaData() {
        PrintedMediaSubResponse result = DataHolder.getInstance().getPrintedMediaSubResponse();
        List<SubInternetModel> items = new ArrayList<>(); // Initialize or clear items list if needed

        for (int i = 0; i < result.getData().getDocs().size(); i++) {
            items.add(new SubInternetModel(
                    result.getData().getDocs().get(i).getTitle(),
                    result.getData().getDocs().get(i).getMedia().getName(),
                    "",
                    result.getData().getDocs().get(i).getPublishDate(),
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getMedia().getLogo(),
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageStoragePath(),
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getPage().getFullImagePath()
            ));

            printedMediaFullPageShowArray.add(Constants.KEY_IMAGE_BASIC_URL +  result.getData().getDocs().get(i).getPage().getFullImagePath());
            printedMediaSubPageShowArray.add(Constants.KEY_IMAGE_BASIC_URL +  result.getData().getDocs().get(i).getImageStoragePath());
            printedMediaDateShowArray.add(result.getData().getDocs().get(i).getPublishDate());
            printedMediaNamesShowArray.add(result.getData().getDocs().get(i).getMedia().getName());

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

//    private void setData2() {
//        SubMenuVisualMediaResponse result = DataHolder.getInstance().getSubMenuVisualMediaModel();
//
//        List<SubInternetModel> items = new ArrayList<>();
//
//
//        for (int i = 0; i < result.getData().getDocs().size(); i++) {
//
//            items.add(new SubInternetModel(
//                    result.getData().getDocs().get(i).getTitle(),
//                    result.getData().getDocs().get(i).getMedia().getName(),
//                    "",
//                    result.getData().getDocs().get(i).getPublishDate(),
//                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getMedia().getLogo(),
//                    Constants.KEY_VIDEO_BASIC_URL + result.getData().getDocs().get(i).getVideo()
//            ));
//
//        }
//
//
////        for (int i = 0; i < result.getData().getDocs().size(); i++) {
////            items.add(new SubInternetModel(
////                    result.getData().getDocs().get(i).getTitle(),
////                    result.getData().getDocs().get(i).getMedia().getType().getName(),
////                    "",
////                    result.getData().getDocs().get(i).getPublishDate(),
////                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageStoragePath(),
////                    result.getData().getDocs().get(i).getUrl()
////            ));
////        }
//
//        if (pageNumber == 0) {
//            x = items.size();
//            adapter = new SubInternetAdapter(this, items, this, x == count);
//            recyclerView.setAdapter(adapter);
//        } else {
//
//            int y = x;
//            x = x + items.size();
//
//            if (x >= count) {
//                adapter.setAllList(true);}
//
//            adapter.addItems(items);
//
//            if (items.size() < 5) {
//                recyclerView.scrollToPosition(x - 1);
//            }
//            else {
//                recyclerView.scrollToPosition(y + 3);
//            }
//
//        }
//
//    }

//    private void setTypesList2() {
//
//        SubMenuVisualMediaResponse result = DataHolder.getInstance().getSubMenuVisualMediaModel();
//
//        List<SubInternetModel> items;
//
//        items = new ArrayList<>();
//
//        for (int i = 0; i < result.getData().getDocs().size(); i++) {
//
//            items.add(new SubInternetModel(
//                    result.getData().getDocs().get(i).getTitle(),
//                    result.getData().getDocs().get(i).getMedia().getName(),
//                    "",
//                    result.getData().getDocs().get(i).getPublishDate(),
//                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getMedia().getLogo(),
//                    Constants.KEY_VIDEO_BASIC_URL + result.getData().getDocs().get(i).getVideo()
//            ));
//
//        }
//
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//        adapter = new SubInternetAdapter(this, items, this, x == count);
//        recyclerView.setAdapter(adapter);
//    }

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
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
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

                Logger.getInstance().logDebug(TAG, "SubInternet", 2, response.body());

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

    private void SubMenuVisualMedia(String menuId, String subMenuId, String startDate, String endDate) {

        if (isLoading) {
            return;
        }

        isLoading = true;


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<SubMenuVisualMediaResponse> call = apiService.subMenuVisualMedia(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                pageNumber, // Use pageNumber for pagination
                pageSize,   // Use pageSize for pagination
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
                true,
                true,
                false,
                false,
                false,
                true,
                true,
                true,
                true,
                true,
                startDate,
                endDate,
                "07:00:00",
                "23:59:00",
                "NEWS",
                "UNIGNORED",
                true,
                menuId,
                subMenuId,
                false,
                false
        );
        call.enqueue(new Callback<SubMenuVisualMediaResponse>() {
            @Override
            public void onResponse(Call<SubMenuVisualMediaResponse> call, Response<SubMenuVisualMediaResponse> response) {

                isLoading = false; // Reset loading flag

                adapter.getHolder().getFrameLayout().setVisibility(View.GONE);

                Logger.getInstance().logDebug(TAG, "SubMenuVisualMedia", 2, response.body());

                if (response.isSuccessful()) {


                    SubMenuVisualMediaResponse result = response.body();

                    DataHolder.getInstance().setSubMenuVisualMediaModel(result);


                    setTypesList2();



                } else {


                }
            }

            @Override
            public void onFailure(Call<SubMenuVisualMediaResponse> call, Throwable t) {

                isLoading = false; // Reset loading flag

                Logger.getInstance().logDebug(TAG, "SubMenuVisualMedia", 3, t.getMessage());
            }
        });

    }

    private void subPrinted(String menuId, String subMenuId, String startDate, String endDate) {

        if (isLoading) {
            return;
        }

        isLoading = true;

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<PrintedMediaSubResponse> call = apiService.subMenuList(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                pageNumber, // Use pageNumber for pagination
                pageSize,   // Use pageSize for pagination
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
                true,
                true,
                false,
                false,
                false,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                startDate,
                endDate,
                "07:00:00",
                "23:59:00",
                "NEWS",
                "UNIGNORED",
                true,
//                "2024010003615660",
                menuId,
                subMenuId,
                false,
                false,
                false
        );
        call.enqueue(new Callback<PrintedMediaSubResponse>() {
            @Override
            public void onResponse(Call<PrintedMediaSubResponse> call, Response<PrintedMediaSubResponse> response) {

                isLoading = false; // Reset loading flag

                adapter.getHolder().getFrameLayout().setVisibility(View.GONE);


                Logger.getInstance().logDebug(TAG, "subMenuList", 2, response.body());

                if (response.isSuccessful()) {

                    PrintedMediaSubResponse result = response.body();

                    DataHolder.getInstance().setPrintedMediaSubResponse(result);

                    setPrintedMediaData();

                } else {



                }
            }

            @Override
            public void onFailure(Call<PrintedMediaSubResponse> call, Throwable t) {

                isLoading = false; // Reset loading flag


                Logger.getInstance().logDebug(TAG, "subMenuList", 3, t.getMessage());
            }
        });


    }


    public void onItemClick2(String mediaPath, String mediaPath2) {


        test12.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);



        switch (index) {
            case 1:
                mesut.setVisibility(View.VISIBLE);
                Glide.with(this).load(mediaPath).into(zoomClass);

                tumSayfa.setOnClickListener(view -> {
                    Glide.with(this).load(mediaPath2).into(zoomClass);
                });


                break;

            case 2:

            case 3:
                // Show the included layout containing the WebView and ProgressBar
                includedLayout.setVisibility(View.VISIBLE);

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
                break;
        }


        sourceUrl = mediaPath;





    }

    @Override
    public void onItemClick(String mediaPath, String mediaPath2, int position) {


//        Intent intent = new Intent(SubInternetActivity.this, MesutActivity.class);
//
//        intent.putExtra("menuId", menuId);
//        intent.putExtra("subMenuId", subMenuId);
//        intent.putExtra("startDate", startDate);
//        intent.putExtra("endDate", endDate);
//        intent.putExtra("count", count);
//
//
//        intent.putExtra("index", index);
//
//        Bundle options = ActivityOptions.makeCustomAnimation(SubInternetActivity.this, R.anim.left, R.anim.right).toBundle();
//        startActivity(intent, options);


        switch (index) {
            case 1:

                DataHolder.getInstance().setPrintedMediaFullPageShowArray(printedMediaFullPageShowArray);
                DataHolder.getInstance().setPrintedMediaSubPageShowArray(printedMediaSubPageShowArray);
                DataHolder.getInstance().setPrintedMediaDateShowArray(printedMediaDateShowArray);
                DataHolder.getInstance().setPrintedMediaNamesShowArray(printedMediaNamesShowArray);

                Intent intent = new Intent(this, MesutActivity.class);


                intent.putExtra("index", position);
                startActivity(intent);

                break;

            case 2:

            case 3:
                // Show the included layout containing the WebView and ProgressBar
                includedLayout.setVisibility(View.VISIBLE);

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
                break;
        }


        sourceUrl = mediaPath;





    }

    @Override
    public void onShowMore() {
        pageNumber++;

        switch (index) {
            case 1:
                subPrinted(menuId, subMenuId, startDate, endDate);
                break;

            case 2:
                SubInternet(menuId, subMenuId, startDate, endDate);
                break;

            case 3:
                SubMenuVisualMedia(menuId, subMenuId, startDate, endDate);
                break;
        }


    }

    @Override
    public void onSwipeRight() {

    }

    @Override
    public void onSwipeLeft() {

    }

    @Override
    public void onSwipeDown() {

    }

    @Override
    public void onSwipeUp() {

    }

    @Override
    public void onSingleTapUp() {

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
        else if (mesut.getVisibility() == View.VISIBLE) {
            mesut.setVisibility(View.INVISIBLE);
            test12.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            super.onBackPressed();
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
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