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
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.ColumnistsResponse;
import com.example.mtm.response.InternetResponse;
import com.example.mtm.response.MagazineFullPagesResponse;
import com.example.mtm.response.MediaAgendaResponse;
import com.example.mtm.response.NewspaperFirstPagesResponse;
import com.example.mtm.response.VisualMediaResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.Logger;
import com.example.mtm.util.MyUtils;
import com.example.mtm.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageView profileImageView, notificationImageView;
    private GridView gridView;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private List<MainActivityModel> items;
    private MainActivityAdapter adapter;

    private Handler handler;
    int page = 0;
    private final int delay = 2000; //milliseconds

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

        int[] images = {
                R.drawable.test6,
                R.drawable.test6,
                R.drawable.test6
        };

        mViewPagerAdapter = new ViewPagerAdapter(getApplication(), images);
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

    }

    private void init() {
        mViewPager = findViewById(R.id.viewPagerMaina);
        profileImageView = findViewById(R.id.profileImageView);
        notificationImageView = findViewById(R.id.notificationImageView);
        gridView = findViewById(R.id.gridView);

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

                    break;
                case 1:

                    break;
                case 2:
                    getMediaAgenda(position);
                    break;
                case 3:

                    break;
                case 4:
                    internet(position);
                    break;
                case 5:
                    visualMedia(position);
                    break;
                case 6:
                    newspaperFirstPages(position);
                    break;
                case 7:
                    magazine(position);
                    break;
                case 8:
                    columnists(position);
                    break;
            }

        });



    }

    private void getMediaAgenda(int position) {


        MainActivityModel itemData = items.get(position);

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
                "2024-01-25"
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

    private void newspaperFirstPages(int position) {


        MainActivityModel itemData = items.get(position);

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

    private void magazine(int position) {

        MainActivityModel itemData = items.get(position);

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

                    Toast.makeText(MainActivity.this, response.body().getData().size() + "", Toast.LENGTH_SHORT).show();

                    DataHolder.getInstance().setMagazineFullPagesModel(result);

                    Intent intent = new Intent(MainActivity.this, MagazineActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);

                } else {

                    if (response.code() == 403) {
                        forbiddenPopup();
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

    private void columnists(int position) {

        MainActivityModel itemData = items.get(position);

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

    private void visualMedia(int position) {

        MainActivityModel itemData = items.get(position);

        // Show progress bar for the clicked item
        itemData.setProgressBarVisible(true);

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();


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
        System.out.println("YASER " + MyUtils.getPreviousDate(1) + " 07:00:00 " + MyUtils.getCurrentDate() + " 23:59:00");

        call.enqueue(new Callback<VisualMediaResponse>() {
            @Override
            public void onResponse(Call<VisualMediaResponse> call, Response<VisualMediaResponse> response) {

                Logger.getInstance().logDebug(TAG, "visualMedia", 2, response.body());

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                if (response.isSuccessful()) {



                    VisualMediaResponse result = response.body();

                    DataHolder.getInstance().setVisualMediaModel(result);

                    Intent intent = new Intent(MainActivity.this, VisualMediaActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);

                } else {

                    if (response.code() == 403) {
                        forbiddenPopup();
                    }


                }
            }

            @Override
            public void onFailure(Call<VisualMediaResponse> call, Throwable t) {

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                Logger.getInstance().logDebug(TAG, "visualMedia", 3, t.getMessage());
            }
        });


    }

    private void internet(int position) {

        MainActivityModel itemData = items.get(position);

        // Show progress bar for the clicked item
        itemData.setProgressBarVisible(true);

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();


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
        System.out.println("YASER " + MyUtils.getPreviousDate(1) + " 07:00:00 " + MyUtils.getCurrentDate() + " 23:59:00");

        call.enqueue(new Callback<InternetResponse>() {
            @Override
            public void onResponse(Call<InternetResponse> call, Response<InternetResponse> response) {

                Logger.getInstance().logDebug(TAG, "visualMedia", 2, response.body());

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                if (response.isSuccessful()) {



                    InternetResponse result = response.body();

                    DataHolder.getInstance().setInternetModel(result);

                    Intent intent = new Intent(MainActivity.this, InternetActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);

                } else {

                    if (response.code() == 403) {
                        forbiddenPopup();
                    }


                }
            }

            @Override
            public void onFailure(Call<InternetResponse> call, Throwable t) {

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                Logger.getInstance().logDebug(TAG, "visualMedia", 3, t.getMessage());
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