package com.medyatakip.app.activity;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.medyatakip.app.R;
import com.medyatakip.app.adapter.SubInternetAdapter;
import com.medyatakip.app.model.SubInternetModel;
import com.medyatakip.app.model.VerticalModel;
import com.medyatakip.app.network.ApiService;
import com.medyatakip.app.network.RetrofitClient;
import com.medyatakip.app.response.InternetSubResponse;
import com.medyatakip.app.response.PrintedMediaSubResponse;
import com.medyatakip.app.response.SubMenuVisualMediaResponse;
import com.medyatakip.app.util.Constants;
import com.medyatakip.app.util.DataHolder;
import com.medyatakip.app.util.Logger;
import com.medyatakip.app.util.MyUtils;
import com.medyatakip.app.util.PreferenceManager;
import com.medyatakip.app.util.ZoomClass;
import com.medyatakip.app.util.ZoomClassWebView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubInternetActivity extends AppCompatActivity implements SubInternetAdapter.OnItemClickListener, ZoomClass.ZoomClassListener{

    private static final String TAG = "SubInternetActivity";

    PreferenceManager preferenceManager;

    private int pageNumber = 0;
    private int pageSize = 50;
    private boolean isLoading = false;
    private ImageView backIconImageView, backIconImageView2;
    private RecyclerView recyclerView;
//    private WebView webView;
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
    ZoomClassWebView webView;
    LinearLayout me;

    int x = 0;

    private int index;

    private ArrayList<String> printedMediaFullPageShowArray;
    private ArrayList<String> printedMediaShareLinkArray;
    private ArrayList<VerticalModel> verticalModels;
    private ArrayList<String> printedMediaSubPageShowArray;
    private ArrayList<String> printedMediaDateShowArray, printedMediaNamesShowArray;

    private ArrayList<String> CaUrlArray;
    private ArrayList<String> CaDatesArray;
    private ArrayList<String> CaNamesArray;
    private ArrayList<String> CaShareUrlArray;


    private GestureDetector gestureDetector;

    private String clipType;

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
                titleTextView.setText("İnternet");
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

            String video = "";
            if (result.getData().getDocs().get(i).getVideo() != null && !result.getData().getDocs().get(i).getVideo().equals("")) {
                video =  Constants.KEY_VIDEO_BASIC_URL + result.getData().getDocs().get(i).getVideo();
            }
            else {
                video = "https://web.medyatakip.com/vdsrv/store/" +result.getData().getDocs().get(i).getAdsVersion().getMedia();
            }
            items.add(new SubInternetModel(
                    result.getData().getDocs().get(i).getTitle(),
                    result.getData().getDocs().get(i).getMedia().getName(),
                    Constants.KEY_SHARE_URL3 + result.getData().getDocs().get(i).getGnoHash(),
                    MyUtils.changeDateFormat(result.getData().getDocs().get(i).getPublishDate()),
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getMedia().getLogo(),
                     video,
                    "",
                    result.getData().getDocs().get(i).getProgram().getName()
            ));

            CaUrlArray.add(video);

            CaDatesArray.add(MyUtils.changeDateFormat(result.getData().getDocs().get(i).getPublishDate()) + " - " + result.getData().getDocs().get(i).getPublishTime());
            CaNamesArray.add(result.getData().getDocs().get(i).getMedia().getName() + " - " + result.getData().getDocs().get(i).getProgram().getName());
            CaShareUrlArray.add(Constants.KEY_SHARE_URL3 + result.getData().getDocs().get(i).getGnoHash());


        }



        if (pageNumber == 0) {
            x = items.size();
            adapter = new SubInternetAdapter(this, items, this, x == count, index);
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

        preferenceManager = new PreferenceManager(getApplicationContext());


        backIconImageView = findViewById(R.id.backIconImageView);

        recyclerView = findViewById(R.id.recyclerView);
//        webView = findViewById(R.id.webView2);
        test12 = findViewById(R.id.test12);
        progressBar2 = findViewById(R.id.progressBar2);
        includedLayout = findViewById(R.id.web_view_layout);
        me = findViewById(R.id.me);
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

        clipType = intent.getStringExtra("clipType");

        sourceUrl = "";


        zoomClass = findViewById(R.id.imageView);
        zoomClass.setZoomClassListener(this);

        webView = findViewById(R.id.webView2);
//        webView.setZoomClassListener2(this);

        printedMediaFullPageShowArray = new ArrayList<>();
        verticalModels = new ArrayList<>();
        printedMediaShareLinkArray = new ArrayList<>();
        printedMediaSubPageShowArray = new ArrayList<>();
        printedMediaDateShowArray = new ArrayList<>();
        printedMediaNamesShowArray = new ArrayList<>();
        CaUrlArray = new ArrayList<>();
        CaDatesArray = new ArrayList<>();
        CaNamesArray = new ArrayList<>();
        CaShareUrlArray = new ArrayList<>();
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
//        backIconImageView2.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        titleTextView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());

        sourceTextView.setOnClickListener(view -> {
            MyUtils.openLink(sourceUrl, this);
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // Step 2: Detect when the user has scrolled to a certain point in the list
                int triggerPoint = totalItemCount / 2; // For example, trigger when scrolled halfway through the list
                if (!isLoading && !(x >= count)) {
                    if (firstVisibleItemPosition >= triggerPoint && totalItemCount >= PAGE_SIZE) {

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
                }
            }
        });

    }


    private void setData() {
        InternetSubResponse result = DataHolder.getInstance().getInternetSubModel();
        List<SubInternetModel> items = new ArrayList<>(); // Initialize or clear items list if needed

        for (int i = 0; i < result.getData().getDocs().size(); i++) {
            items.add(new SubInternetModel(
                    result.getData().getDocs().get(i).getTitle(),
                    result.getData().getDocs().get(i).getMedia().getName(),
                    Constants.KEY_SHARE_URL2 + result.getData().getDocs().get(i).getGnoHash(),
                    MyUtils.changeDateFormat(result.getData().getDocs().get(i).getPublishDate()),
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageStoragePath(),
                    result.getData().getDocs().get(i).getUrl(),
                    "",
                    ""
            ));

//            CaUrlArray.add(result.getData().getDocs().get(i).getUrl());
            CaUrlArray.add(Constants.KEY_INTERNET_URL + result.getData().getDocs().get(i).getGnoHash());
            CaDatesArray.add(MyUtils.changeDateFormat(result.getData().getDocs().get(i).getPublishDate()));
            CaNamesArray.add(result.getData().getDocs().get(i).getMedia().getName());
            CaShareUrlArray.add(Constants.KEY_SHARE_URL2 + result.getData().getDocs().get(i).getGnoHash());
        }

        if (pageNumber == 0) {
            x = items.size();
            adapter = new SubInternetAdapter(this, items, this, x == count, index);
            recyclerView.setAdapter(adapter);
        } else {

            int y = x;
            x = x + items.size();

            if (x >= count) {
                adapter.setAllList(true);}

            adapter.addItems(items);

            if (items.size() < 5) {
//                recyclerView.scrollToPosition(x - 1);
            }
            else {
//                recyclerView.scrollToPosition(y + 3);
            }

        }
        
    }

    private void setPrintedMediaData() {
        PrintedMediaSubResponse result = DataHolder.getInstance().getPrintedMediaSubResponse();
        List<SubInternetModel> items = new ArrayList<>(); // Initialize or clear items list if needed

        if (result.getData() != null) {
            for (int i = 0; i < result.getData().getDocs().size(); i++) {
                items.add(new SubInternetModel(
                        result.getData().getDocs().get(i).getTitle(),
                        result.getData().getDocs().get(i).getMedia().getName(),
                        Constants.KEY_SHARE_URL + result.getData().getDocs().get(i).getGnoHash(),
                        MyUtils.changeDateFormat(result.getData().getDocs().get(i).getPublishDate()),
                        Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getMedia().getLogo(),
                        Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageStoragePath(),
                        Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getPage().getFullImagePath(),
                        ""
                ));

                ArrayList<String> clipImages = new ArrayList<>();
                ArrayList<String> fullImages = new ArrayList<>();


                if (result.getData().getDocs().get(i).getContinuesClip().size() > 1) {

                    for (int j = 0; j < result.getData().getDocs().get(i).getContinuesClip().size(); j++) {

                        clipImages.add(
                                "https://imgsrv.medyatakip.com/store/clip?gno=" +  result.getData().getDocs().get(i).getContinuesClip().get(j).getGno() + "&ds=" + preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_PM));

                        fullImages.add("https://imgsrv.medyatakip.com/store/" + result.getData().getDocs().get(i).getImageInfo().getMediaPath() + "page/" + result.getData().getDocs().get(i).getImageInfo().getPageFile() + "-" + result.getData().getDocs().get(i).getContinuesClip().get(j).getPn() + ".jpg");
                    }
                }
                else {
//                    clipImages.add(Constants.KEY_IMAGE_BASIC_URL +  result.getData().getDocs().get(i).getImageStoragePath() );
                    clipImages.add("https://imgsrv.medyatakip.com/store/clip?gno=" +  result.getData().getDocs().get(i).getGno() + "&ds=" + preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_PM));
                    fullImages.add(Constants.KEY_IMAGE_BASIC_URL +
                            result.getData().getDocs().get(i).getImageInfo().getMediaPath() +
                            "page/" + result.getData().getDocs().get(i).getImageInfo().getPageFile() +
                            "-" + result.getData().getDocs().get(i).getContinuesClip().get(0).getPn() +
                            ".jpg");
                }



                printedMediaFullPageShowArray.add(Constants.KEY_IMAGE_BASIC_URL +  result.getData().getDocs().get(i).getPage().getFullImagePath());
//            printedMediaSubPageShowArray.add(Constants.KEY_IMAGE_BASIC_URL +  result.getData().getDocs().get(i).getImageStoragePath());





                verticalModels.add(new VerticalModel(fullImages, clipImages));

                for (int n = 0; n < clipImages.size(); n++) {

                }


//            printedMediaFullPageShowArray.add(
//                    Constants.KEY_IMAGE_BASIC_URL +
//                            result.getData().getDocs().get(i).getImageInfo().getMediaPath() +
//                            "/page/" + result.getData().getDocs().get(i).getImageInfo().getPageFile() +
//                            "-" + result.getData().getDocs().get(i).getContinuesClip().get(0).getPn() +
//                            ".jpg");

//            printedMediaSubPageShowArray.add(Constants.KEY_IMAGE_BASIC_URL +  result.getData().getDocs().get(i).getImageStoragePath());
                printedMediaDateShowArray.add(MyUtils.changeDateFormat(result.getData().getDocs().get(i).getPublishDate()));
                printedMediaNamesShowArray.add(result.getData().getDocs().get(i).getMedia().getName());
                printedMediaShareLinkArray.add(Constants.KEY_SHARE_URL + result.getData().getDocs().get(i).getGnoHash());

            }

            if (pageNumber == 0) {
                x = items.size();
                adapter = new SubInternetAdapter(this, items, this, x == count, index);
                recyclerView.setAdapter(adapter);
            } else {

                int y = x;
                x = x + items.size();

                if (x >= count) {
                    adapter.setAllList(true);}

                adapter.addItems(items);

//            if (items.size() < 5) {
//                recyclerView.smoothScrollToPosition(x - 1);
//            }
//            else {
//                recyclerView.s(y);
//            }

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
                clipType,
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
                clipType,
                "UNIGNORED",
                true,
                menuId,
                subMenuId,
                false,
                false,
                true
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
                clipType,
                "UNIGNORED",
                true,
//                "2024010003615660",
                menuId,
                subMenuId,
                false,
                false,
                false
        );

        Logger.getInstance().logDebug(TAG, "subMenuList", 1, "menuId: " + menuId + "subMenuId: " + subMenuId);

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
                me.setVisibility(View.VISIBLE);
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


//        Intent intent = new Intent(SubInternetActivity.this, MeActivity.class);
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
                DataHolder.getInstance().setPrintedMediaShareLinkArray(printedMediaShareLinkArray);
                DataHolder.getInstance().setVerticalModels(verticalModels);
                DataHolder.getInstance().setPrintedMediaFullPageShowArray(printedMediaFullPageShowArray);
                DataHolder.getInstance().setPrintedMediaSubPageShowArray(printedMediaSubPageShowArray);
                DataHolder.getInstance().setPrintedMediaDateShowArray(printedMediaDateShowArray);
                DataHolder.getInstance().setPrintedMediaNamesShowArray(printedMediaNamesShowArray);

                Intent intent = new Intent(this, Me2Activity.class);


                intent.putExtra("index", position);
                startActivity(intent);

                break;

            case 2:

            case 3:


                DataHolder.getInstance().setCaUrlArray(CaUrlArray);
                DataHolder.getInstance().setCaDatesArray(CaDatesArray);
                DataHolder.getInstance().setCaNamesArray(CaNamesArray);
                DataHolder.getInstance().setCaShareUrlArray(CaShareUrlArray);

                Intent intent2 = new Intent(this, MainActivity3.class);

                intent2.putExtra("index2", position);
                startActivity(intent2);

                break;

            //                // Show the included layout containing the WebView and ProgressBar
//                includedLayout.setVisibility(View.VISIBLE);
//
//                // Load the URL in the WebView
//                webView.loadUrl(mediaPath);
//
//                // Set up a WebViewClient to handle page loading events
//                webView.setWebViewClient(new WebViewClient() {
//                    // Show ProgressBar when page starts loading
//                    @Override
//                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                        progressBar2.setVisibility(View.VISIBLE);
//                    }
//
//                    // Hide ProgressBar when page finishes loading
//                    @Override
//                    public void onPageFinished(WebView view, String url) {
//                        progressBar2.setVisibility(View.GONE);
//                    }
//                });
        }


        sourceUrl = mediaPath;





    }

    @Override
    public void onShowMore() {
//        pageNumber++;
//
//        switch (index) {
//            case 1:
//                subPrinted(menuId, subMenuId, startDate, endDate);
//                break;
//
//            case 2:
//                SubInternet(menuId, subMenuId, startDate, endDate);
//                break;
//
//            case 3:
//                SubMenuVisualMedia(menuId, subMenuId, startDate, endDate);
//                break;
//        }


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


    @Override
    public void onBackPressed() {

        if (includedLayout.getVisibility() == View.VISIBLE) {
            includedLayout.setVisibility(View.INVISIBLE);
            test12.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else if (me.getVisibility() == View.VISIBLE) {
            me.setVisibility(View.INVISIBLE);
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