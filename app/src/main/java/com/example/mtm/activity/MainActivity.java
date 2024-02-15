package com.example.mtm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mtm.R;
import com.example.mtm.adapter.MainActivityAdapter;
import com.example.mtm.adapter.ViewPagerAdapter;
import com.example.mtm.model.MainActivityModel;
import com.example.mtm.model.SliderModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.ColumnistsResponse;
import com.example.mtm.response.InternetResponse;
import com.example.mtm.response.MagazineFullPagesResponse;
import com.example.mtm.response.MediaAgendaResponse;
import com.example.mtm.response.MenuListResponse;
import com.example.mtm.response.NewspaperFirstPagesResponse;
import com.example.mtm.response.RefreshTokenResponse;
import com.example.mtm.response.SummaryListResponse;
import com.example.mtm.response.TokenResponse;
import com.example.mtm.response.VisualMediaResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.Logger;
import com.example.mtm.util.MyUtils;
import com.example.mtm.util.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private PreferenceManager preferenceManager;

    private ImageView profileImageView, notificationImageView;
    private GridView gridView;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private List<MainActivityModel> items;
    private MainActivityAdapter adapter;

    private Handler handler;
    int page = 0;
    private final int delay = 2000; //milliseconds

    private int newListIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setItemClickListeners();

        items = new ArrayList<>();
        items.add(new MainActivityModel(R.drawable.media_icon, R.drawable.test7, "Medya Raporu"));
        items.add(new MainActivityModel(R.drawable.news_icon, R.drawable.test8, "Haber Listesi"));
        items.add(new MainActivityModel(R.drawable.media2_icon, R.drawable.test9, "Medya Gündemi"));

        items.add(new MainActivityModel(R.drawable.media_icon, R.drawable.test10, "Yazılı"));
        items.add(new MainActivityModel(R.drawable.internet_icon, R.drawable.test11, "İnternet"));
        items.add(new MainActivityModel(R.drawable.visual_and_auditory_icon, R.drawable.test12, getString(R.string.visual_media)));

        items.add(new MainActivityModel(R.drawable.newspapers_icon, R.drawable.test13, "Gazeteler"));
        items.add(new MainActivityModel(R.drawable.magazines_icon, R.drawable.test14, "Dergiler"));
        items.add(new MainActivityModel(R.drawable.opinion_writers_icon, R.drawable.test15, "Köşe Yazarları"));

        adapter = new MainActivityAdapter(this, items);
        gridView.setAdapter(adapter);

        CircleIndicator indicator = findViewById(R.id.indicator);

        List<SliderModel> sliderModels = new ArrayList<>();
        sliderModels.add(new SliderModel("https://app.medyatakip.com/assets/slide/slider1.png" , "https://www.medyatakip.com/mLink.php?p=1"));
        sliderModels.add(new SliderModel("https://app.medyatakip.com/assets/slide/slider2.png" , "https://www.medyatakip.com/mLink.php?p=2"));


        mViewPagerAdapter = new ViewPagerAdapter(this, sliderModels);

        mViewPager.setAdapter(mViewPagerAdapter);
        indicator.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                page = position;
            }

            @Override
            public void onPageSelected(int position) {
//                Toast.makeText(LoginActivity.this, ""  + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Logger.getInstance().logDebug(TAG, "saved refreshToken", 2, preferenceManager.getString(Constants.KEY_REFRESH_TOKEN));
        Logger.getInstance().logDebug(TAG, "saved accessToken", 2, preferenceManager.getString(Constants.KEY_ACCESS_TOKEN));

    }

    private void init() {
        mViewPager = findViewById(R.id.viewPagerMaina);
        profileImageView = findViewById(R.id.profileImageView);
        notificationImageView = findViewById(R.id.notificationImageView);
        gridView = findViewById(R.id.gridView);


        preferenceManager = new PreferenceManager(getApplicationContext());
        handler = new Handler(Looper.getMainLooper());
        items = new ArrayList<>();
    }

    private void setItemClickListeners() {

        profileImageView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("itemData", "test");
            startActivity(intent);
        });

        notificationImageView.setOnClickListener(view -> {
            Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show();
        });


        gridView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    summaryList(items.get(position));
                    break;
                case 1:
                    newsList(items.get(position));
                    break;
                case 2:
                    getMediaAgenda(items.get(position));
                    break;
                case 3:
                    menuList(items.get(position), false);
                    break;
                case 4:
                    internet(items.get(position), false);
                    break;
                case 5:
                    visualMedia(items.get(position), false);
                    break;
                case 6:
                    newspaperFirstPages(items.get(position));
                    break;
                case 7:
                    magazine(items.get(position));
                    break;
                case 8:
                    columnists(items.get(position));
                    break;
            }

        });

    }

    private void summaryList(MainActivityModel itemData) {

        // Show progress bar for the clicked item
        itemData.setProgressBarVisible(true);

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<SummaryListResponse> call = apiService.summaryList(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                22632,
                "2024-02-01",
                "2024-02-01",
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true
        );

        call.enqueue(new Callback<SummaryListResponse>() {
            @Override
            public void onResponse(Call<SummaryListResponse> call, Response<SummaryListResponse> response) {

                Logger.getInstance().logDebug(TAG, "summaryList", 2, response.body());


                    // Show progress bar for the clicked item
                    itemData.setProgressBarVisible(false);

                    // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged();


                if (response.isSuccessful()) {

                    SummaryListResponse result = response.body();

                    DataHolder.getInstance().setSummaryListResponse(result);

                    Intent intent = new Intent(MainActivity.this, MediaReportActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);


                } else {

                    if (response.code() == 403) {
                        forbiddenPopup();
                    }

                    else {
                        refreshToken(itemData, 1);
                    }


                }
            }

            @Override
            public void onFailure(Call<SummaryListResponse> call, Throwable t) {


                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();


                Logger.getInstance().logDebug(TAG, "summaryList", 3, t.getMessage());
            }
        });


    }

    private void newsList(MainActivityModel itemData) {

        // Show progress bar for the clicked item
        itemData.setProgressBarVisible(true);

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();

        newListIndex = 0;

        menuList(itemData, true);
        internet(itemData, true);
        visualMedia(itemData, true);


    }

    private void getMediaAgenda(MainActivityModel itemData) {


        // Show progress bar for the clicked item
        itemData.setProgressBarVisible(true);

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<MediaAgendaResponse> call = apiService.getMediaAgenda(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                22632,
                true,
                true,
                true,
                true,
                true,
                MyUtils.getCurrentDate()
        );

        call.enqueue(new Callback<MediaAgendaResponse>() {
            @Override
            public void onResponse(Call<MediaAgendaResponse> call, Response<MediaAgendaResponse> response) {

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 2, response.body());

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                if (response.isSuccessful()) {




                    MediaAgendaResponse result = response.body();


                    DataHolder.getInstance().setMediaAgendaModel(result);

//                    ArrayList<String> userMatch2 = new ArrayList<>();
//                    siyasetModels = new ArrayList<>();
//                    ekonomiModels = new ArrayList<>();
//                    dunyaModels = new ArrayList<>();
//                    kulturModels = new ArrayList<>();
//                    yasamModels = new ArrayList<>();
//                    sporModels = new ArrayList<>();
//                    isModels = new ArrayList<>();
//
//                    String genderType = "";
//
//                    for (int i = 0; i < result.getData().getDocs().size(); i++) {
//
//                        String newGenderType = result.getData().getDocs().get(i).getAgendaType().getName();
//                        if (!newGenderType.equals(genderType)) {
//                            userMatch2.add(newGenderType);
//                            genderType = newGenderType;
//                        }
//                    }
//
//                    setTypesList(userMatch2);
//
//
//
//
//
//
//
//                    for (int i = 0; i < result.getData().getDocs().size() ; i++) {
//
//                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Siyaset")) {
//                            siyasetModels.add(new Models(
//                                    "Siyaset",
//                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
//                                    result.getData().getDocs().get(i).getAgendaType().getName(),
//                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
//                            ));
//                        }
//
//                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Ekonomi")) {
//                            ekonomiModels.add(new Models(
//                                    "Ekonomi",
//                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
//                                    result.getData().getDocs().get(i).getAgendaType().getName(),
//                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
//                            ));
//                        }
//
//                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Dünya")) {
//                            dunyaModels.add(new Models(
//                                    "Dünya",
//                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
//                                    result.getData().getDocs().get(i).getAgendaType().getName(),
//                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
//                            ));
//                        }
//
//                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Kültür-Sanat")) {
//                            kulturModels.add(new Models(
//                                    "Kültür-Sanat",
//                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
//                                    result.getData().getDocs().get(i).getAgendaType().getName(),
//                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
//                            ));
//                        }
//
//                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Yaşam")) {
//                            yasamModels.add(new Models(
//                                    "Yaşam",
//                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
//                                    result.getData().getDocs().get(i).getAgendaType().getName(),
//                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
//                            ));
//                        }
//
//                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Spor")) {
//                            sporModels.add(new Models(
//                                    "spor",
//                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
//                                    result.getData().getDocs().get(i).getAgendaType().getName(),
//                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
//                            ));
//                        }
//
//                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("İş Dünyası")) {
//                            isModels.add(new Models(
//                                    "İş Dünyası",
//                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
//                                    result.getData().getDocs().get(i).getAgendaType().getName(),
//                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
//                            ));
//                        }
//
//                    }
//
//
//
//
//                    setTypesList2(siyasetModels);



                    Intent intent = new Intent(MainActivity.this, MediaAgendaActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);

                } else {

                    if (response.code() == 403) {
                        forbiddenPopup();
                    }

                    else {
                        refreshToken(itemData, 2);
                    }

                }
            }

            @Override
            public void onFailure(Call<MediaAgendaResponse> call, Throwable t) {

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 3, t.getMessage());
            }
        });


    }

    private void menuList(MainActivityModel itemData, boolean newsList) {

        if (!newsList) {
            // Show progress bar for the clicked item
            itemData.setProgressBarVisible(true);

            // Notify the adapter that data has changed
            adapter.notifyDataSetChanged();
        }

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<MenuListResponse> call = apiService.menuList(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                10000,
                22632,
                false,
                false,
                true,
                true,
                true,
                true,
                MyUtils.getPreviousDate(1),
                MyUtils.getCurrentDate(),
                "07:00:00",
                "23:59:00",
                "UNIGNORED",
                true,
                true,
                true,
                false
        );

        call.enqueue(new Callback<MenuListResponse>() {
            @Override
            public void onResponse(Call<MenuListResponse> call, Response<MenuListResponse> response) {

                Logger.getInstance().logDebug(TAG, "menuList", 2, response.body());

                if (!newsList) {
                    // Show progress bar for the clicked item
                    itemData.setProgressBarVisible(false);

                    // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged();
                }
                else {
                    newListIndex++;
                }




                if (response.isSuccessful()) {



                    MenuListResponse result = response.body();

                    DataHolder.getInstance().setMenuListResponse(result);

                    if(!newsList) {
                        Intent intent = new Intent(MainActivity.this, PrintedActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                        startActivity(intent);
                    }
                    else {
                        if (newListIndex == 3) {

                            newListIndex = 0;

                            // Show progress bar for the clicked item
                            itemData.setProgressBarVisible(false);

                            // Notify the adapter that data has changed
                            adapter.notifyDataSetChanged();

                            Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
                            startActivity(intent);
                        }

                    }



                } else {

                    if (response.code() == 403) {
                        forbiddenPopup();
                    }

                    else {
                        refreshToken(itemData, 3);
                    }


                }
            }

            @Override
            public void onFailure(Call<MenuListResponse> call, Throwable t) {

                if (!newsList) {
                    // Show progress bar for the clicked item
                    itemData.setProgressBarVisible(false);

                    // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged();
                }

                Logger.getInstance().logDebug(TAG, "menuList", 3, t.getMessage());
            }
        });


    }

    private void internet(MainActivityModel itemData, boolean newsList) {

        if (!newsList) {
            // Show progress bar for the clicked item
            itemData.setProgressBarVisible(true);

            // Notify the adapter that data has changed
            adapter.notifyDataSetChanged();
        }

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<InternetResponse> call = apiService.internet(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                10000,
                22632,
                false,
                false,
                true,
                true,
                true,
                MyUtils.getPreviousDate(1),
                MyUtils.getCurrentDate(),
                "07:00:00",
                "23:59:00",
                "UNIGNORED",
                true,
                true,
                true
        );

        call.enqueue(new Callback<InternetResponse>() {
            @Override
            public void onResponse(Call<InternetResponse> call, Response<InternetResponse> response) {

                Logger.getInstance().logDebug(TAG, "visualMedia", 2, response.body());

                if (!newsList) {
                    // Show progress bar for the clicked item
                    itemData.setProgressBarVisible(false);

                    // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged();
                }
                else {
                    newListIndex++;
                }



                if (response.isSuccessful()) {



                    InternetResponse result = response.body();

                    DataHolder.getInstance().setInternetModel(result);

                    if(!newsList) {
                        Intent intent = new Intent(MainActivity.this, InternetActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                        startActivity(intent);
                    }
                    else {

                        if (newListIndex == 3) {

                            newListIndex = 0;

                            // Show progress bar for the clicked item
                            itemData.setProgressBarVisible(false);

                            // Notify the adapter that data has changed
                            adapter.notifyDataSetChanged();

                            Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
                            startActivity(intent);
                        }


                    }



                } else {

                    if (response.code() == 403) {
                        forbiddenPopup();
                    }

                    else {
                        refreshToken(itemData, 4);
                    }


                }
            }

            @Override
            public void onFailure(Call<InternetResponse> call, Throwable t) {

                if (!newsList) {
                    // Show progress bar for the clicked item
                    itemData.setProgressBarVisible(false);

                    // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged();
                }
                Logger.getInstance().logDebug(TAG, "visualMedia", 3, t.getMessage());
            }
        });


    }

    private void visualMedia(MainActivityModel itemData, boolean newsList) {

        if (!newsList) {
            // Show progress bar for the clicked item
            itemData.setProgressBarVisible(true);

            // Notify the adapter that data has changed
            adapter.notifyDataSetChanged();
        }

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<VisualMediaResponse> call = apiService.visualMedia(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                1000,
                22632,
                false,
                false,
                true,
                true,
                true,
                MyUtils.getPreviousDate(1),
                MyUtils.getCurrentDate(),
                "07:00:00",
                "23:59:00",
                "UNIGNORED",
                true,
                true,
                true
        );

        call.enqueue(new Callback<VisualMediaResponse>() {
            @Override
            public void onResponse(Call<VisualMediaResponse> call, Response<VisualMediaResponse> response) {

                Logger.getInstance().logDebug(TAG, "visualMedia", 2, response.body());

                if (!newsList) {
                    // Show progress bar for the clicked item
                    itemData.setProgressBarVisible(false);

                    // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged();
                }

                else {
                    newListIndex++;
                }

                if (response.isSuccessful()) {

                    VisualMediaResponse result = response.body();

                    DataHolder.getInstance().setVisualMediaModel(result);

                    if (!newsList) {
                        Intent intent = new Intent(MainActivity.this, VisualMediaActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                        startActivity(intent);
                    }
                    else {

                        if (newListIndex == 3) {

                            newListIndex = 0;

                            // Show progress bar for the clicked item
                            itemData.setProgressBarVisible(false);

                            // Notify the adapter that data has changed
                            adapter.notifyDataSetChanged();

                            Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
                            startActivity(intent);
                        }

                    }



                } else {

                    if (response.code() == 403) {
                        forbiddenPopup();
                    }
                    else {
                        refreshToken(itemData, 5);
                    }

                }
            }

            @Override
            public void onFailure(Call<VisualMediaResponse> call, Throwable t) {

                if(!newsList) {
                    // Show progress bar for the clicked item
                    itemData.setProgressBarVisible(false);

                    // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged();
                }

                Logger.getInstance().logDebug(TAG, "visualMedia", 3, t.getMessage());
            }
        });


    }

    private void newspaperFirstPages(MainActivityModel itemData) {

        // Show progress bar for the clicked item
        itemData.setProgressBarVisible(true);

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<NewspaperFirstPagesResponse> call = apiService.newspaperFirstPages(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                22632,
                0,
                50,
                MyUtils.getCurrentDate()
        );

        call.enqueue(new Callback<NewspaperFirstPagesResponse>() {
            @Override
            public void onResponse(Call<NewspaperFirstPagesResponse> call, Response<NewspaperFirstPagesResponse> response) {

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 2, response.body());

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                if (response.isSuccessful()) {




                    NewspaperFirstPagesResponse result = response.body();

                    DataHolder.getInstance().setNewspaperFirstPagesModel(result);

                    Intent intent = new Intent(MainActivity.this, NewspaperActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);

                } else {

                    if (response.code() == 403) {
                        forbiddenPopup();
                    }
                    else {
                        refreshToken(itemData, 6);
                    }

                }
            }

            @Override
            public void onFailure(Call<NewspaperFirstPagesResponse> call, Throwable t) {

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 3, t.getMessage());
            }
        });


    }

    private void magazine(MainActivityModel itemData) {

        // Show progress bar for the clicked item
        itemData.setProgressBarVisible(true);

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<MagazineFullPagesResponse> call = apiService.magazines(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                22632,
                0,
                50,
                true,
                true,
                "National",
                "Magazine",
                MyUtils.getFirstDateOfMonth(),
                MyUtils.getCurrentDate()
        );

        call.enqueue(new Callback<MagazineFullPagesResponse>() {
            @Override
            public void onResponse(Call<MagazineFullPagesResponse> call, Response<MagazineFullPagesResponse> response) {

                Logger.getInstance().logDebug(TAG, "magazines", 2, response.body());

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                if (response.isSuccessful()) {






                    MagazineFullPagesResponse result = response.body();


                    DataHolder.getInstance().setMagazineFullPagesModel(result);

                    Intent intent = new Intent(MainActivity.this, MagazineActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);

                } else {

                    if (response.code() == 403) {
                        forbiddenPopup();
                    }

                    else {
                        refreshToken(itemData, 7);
                    }

                }
            }

            @Override
            public void onFailure(Call<MagazineFullPagesResponse> call, Throwable t) {

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                Logger.getInstance().logDebug(TAG, "magazines", 3, t.getMessage());
            }
        });


    }

    private void columnists(MainActivityModel itemData) {

        // Show progress bar for the clicked item
        itemData.setProgressBarVisible(true);

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<ColumnistsResponse> call = apiService.columnists(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                50,
                22632,
                true,
                MyUtils.getCurrentDate(),
                MyUtils.getCurrentDate(),
                "UNIGNORED",
                true,
                false
        );

        call.enqueue(new Callback<ColumnistsResponse>() {
            @Override
            public void onResponse(Call<ColumnistsResponse> call, Response<ColumnistsResponse> response) {

                Logger.getInstance().logDebug(TAG, "columnists", 2, response.body());

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                if (response.isSuccessful()) {



                    ColumnistsResponse result = response.body();

                    DataHolder.getInstance().setColumnistsModel(result);

                    Intent intent = new Intent(MainActivity.this, ColumnistsActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);

                } else {

                    if (response.code() == 403) {
                        forbiddenPopup();
                    }

                    else {
                        refreshToken(itemData, 8);
                    }

                }
            }

            @Override
            public void onFailure(Call<ColumnistsResponse> call, Throwable t) {

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                Logger.getInstance().logDebug(TAG, "columnists", 3, t.getMessage());
            }
        });


    }

    private void refreshToken(MainActivityModel itemData, int index) {

        ApiService apiService = RetrofitClient.getClient(1).create(ApiService.class);

        Map<String, String> fields = new HashMap<>();
        fields.put("client_id", "account-mobile");
        fields.put("client_secret", "6323a00d-974f-46b9-974d-37e9a1588c59");
        fields.put("grant_type", "refresh_token");
        fields.put("refresh_token", preferenceManager.getString(Constants.KEY_REFRESH_TOKEN));

        Call<RefreshTokenResponse> call = apiService.refreshToken(preferenceManager.getString(Constants.KEY_ACCESS_TOKEN), fields);

        Logger.getInstance().logDebug(TAG, "refreshToken", 1, fields);

        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {

                Logger.getInstance().logDebug(TAG, "refreshToken", 2, response.body());

                Logger.getInstance().logDebug(TAG, "refreshToken KEY_ACCESS_TOKEN", 2, preferenceManager.getString(Constants.KEY_ACCESS_TOKEN));


                if (response.isSuccessful()) {

                    RefreshTokenResponse tokenModel = response.body();
                    if (tokenModel != null) {

                        preferenceManager.putString(Constants.KEY_ACCESS_TOKEN, tokenModel.getAccessToken());
                        preferenceManager.putString(Constants.KEY_REFRESH_TOKEN, tokenModel.getRefreshToken());

                        switch (index) {
                            case 0:

                                break;
                            case 1:
                                newsList(itemData);
                                break;
                            case 2:
                                getMediaAgenda(itemData);
                                break;
                            case 3:
                                menuList(itemData, false);
                                break;
                            case 4:
                                internet(itemData, false);
                                break;
                            case 5:
                                visualMedia(itemData, false);
                                break;
                            case 6:
                                newspaperFirstPages(itemData);
                                break;
                            case 7:
                                magazine(itemData);
                                break;
                            case 8:
                                columnists(itemData);
                                break;
                        }


                    }

                }
                else {


                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                Logger.getInstance().logDebug(TAG, "refreshToken", 3, t.getMessage());
            }
        });
    }

    private void forbiddenPopup() {
        Toast.makeText(this, "Forbidden", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delay);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private final Runnable runnable = new Runnable() {
        public void run() {

            if (mViewPagerAdapter.getCount() == page) {
                page = 0;
            }
            else {
                page++;
            }

            mViewPager.setCurrentItem(page, true);
            handler.postDelayed(this, delay);
        }
    };


}