package com.example.mtm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ImageView profileImageView, notificationImageView;
    GridView gridView;
    ViewPagerAdapter mViewPagerAdapter;

    ViewPager mViewPager;

    List<ItemData> items;

    int[] images = {
            R.drawable.test6,
            R.drawable.test6,
            R.drawable.test6
    };
    CustomAdapter adapter;
    private Handler handler;
    int page = 0;
    private int delay = 2000; //milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setItemClickListeners();



        items = new ArrayList<>();
        items.add(new ItemData(R.drawable.media_icon, R.drawable.test7, "Medya Raporu"));
        items.add(new ItemData(R.drawable.news_icon, R.drawable.test8, "Haber Listesi"));
        items.add(new ItemData(R.drawable.media2_icon, R.drawable.test9, "Medya Gündemi"));

        items.add(new ItemData(R.drawable.media_icon, R.drawable.test10, "Yazılı"));
        items.add(new ItemData(R.drawable.internet_icon, R.drawable.test11, "İnternet"));
        items.add(new ItemData(R.drawable.visual_and_auditory_icon, R.drawable.test12, "Görsel & İşitsel"));

        items.add(new ItemData(R.drawable.newspapers_icon, R.drawable.test13, "Gazeteler"));
        items.add(new ItemData(R.drawable.magazines_icon, R.drawable.test14, "Dergiler"));
        items.add(new ItemData(R.drawable.opinion_writers_icon, R.drawable.test15, "Köşe Yazarları"));


        adapter = new CustomAdapter(this, items);
        gridView.setAdapter(adapter);





        CircleIndicator indicator = findViewById(R.id.indicator);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(getApplication(), images);
        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);
//        handler = new Handler(Looper.getMainLooper());
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

//        gridView.setOnItemClickListener((parent, view, position, id) -> {
//            ItemData itemData = items.get(position);
//            // Toggle the visibility of the progress bar for the clicked item
//            itemData.setProgressBarVisible(!itemData.isProgressBarVisible());
//            // Notify the adapter that the data has changed
//            adapter.notifyDataSetChanged();
//
//            // Start the details activity or perform any other action
////            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
////            intent.putExtra("itemData", itemData.getText());
////            startActivity(intent);
//        });

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

                    break;
                case 5:

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

//            getMediaAgenda(position);



            // Start some asynchronous task (e.g., loading data)
            // You can replace this with your actual task
            // For demonstration, I'm using a handler to simulate a delay
//            new Handler().postDelayed(() -> {
//                // After completing the task, hide progress bar for the clicked item
//                itemData.setProgressBarVisible(false);
//
//                // Notify the adapter that data has changed
//                adapter.notifyDataSetChanged();
//
//                // Start the details activity or perform any other action
//                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
//                intent.putExtra("itemData", itemData.getText());
//                startActivity(intent);
//            }, 2000); // Simulating a delay of 2 seconds
        });



    }

    private void getMediaAgenda(int position) {


        ItemData itemData = items.get(position);

        // Show progress bar for the clicked item
        itemData.setProgressBarVisible(true);

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<MediaAgendaModel> call = apiService.getMediaAgenda(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                22632,
                true,
                true,
                true,
                true,
                true,
                "2024-01-25"
        );

        call.enqueue(new Callback<MediaAgendaModel>() {
            @Override
            public void onResponse(Call<MediaAgendaModel> call, Response<MediaAgendaModel> response) {

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 2, response.body());

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                if (response.isSuccessful()) {




                    MediaAgendaModel result = response.body();


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



                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);

                } else {



                }
            }

            @Override
            public void onFailure(Call<MediaAgendaModel> call, Throwable t) {

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 3, t.getMessage());
            }
        });


    }

    private void newspaperFirstPages(int position) {


        ItemData itemData = items.get(position);

        // Show progress bar for the clicked item
        itemData.setProgressBarVisible(true);

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<NewspaperFirstPagesModel> call = apiService.newspaperFirstPages(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                22632,
                0,
                50,
                MyUtils.getCurrentDate()
        );

        call.enqueue(new Callback<NewspaperFirstPagesModel>() {
            @Override
            public void onResponse(Call<NewspaperFirstPagesModel> call, Response<NewspaperFirstPagesModel> response) {

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 2, response.body());

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                if (response.isSuccessful()) {




                    NewspaperFirstPagesModel result = response.body();

                    DataHolder.getInstance().setNewspaperFirstPagesModel(result);

                    Intent intent = new Intent(MainActivity.this, NewspaperFirstPageActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);

                } else {



                }
            }

            @Override
            public void onFailure(Call<NewspaperFirstPagesModel> call, Throwable t) {

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 3, t.getMessage());
            }
        });


    }


    private void magazine(int position) {


        ItemData itemData = items.get(position);

        // Show progress bar for the clicked item
        itemData.setProgressBarVisible(true);

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<MagazineFullPagesModel> call = apiService.magazines(
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

        call.enqueue(new Callback<MagazineFullPagesModel>() {
            @Override
            public void onResponse(Call<MagazineFullPagesModel> call, Response<MagazineFullPagesModel> response) {

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 2, response.body());

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                if (response.isSuccessful()) {






                    MagazineFullPagesModel result = response.body();

                    Toast.makeText(MainActivity.this, response.body().getData().size() + "", Toast.LENGTH_SHORT).show();

                    DataHolder.getInstance().setMagazineFullPagesModel(result);

                    Intent intent = new Intent(MainActivity.this, MagazineFirstPageActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);

                } else {



                }
            }

            @Override
            public void onFailure(Call<MagazineFullPagesModel> call, Throwable t) {

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 3, t.getMessage());
            }
        });


    }



    private void columnists(int position) {


        ItemData itemData = items.get(position);

        // Show progress bar for the clicked item
        itemData.setProgressBarVisible(true);

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged();


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<ColumnistsModel> call = apiService.columnists(
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

        call.enqueue(new Callback<ColumnistsModel>() {
            @Override
            public void onResponse(Call<ColumnistsModel> call, Response<ColumnistsModel> response) {

                Logger.getInstance().logDebug(TAG, "columnists", 2, response.body());

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                if (response.isSuccessful()) {



                    ColumnistsModel result = response.body();

                    DataHolder.getInstance().setColumnistsModel(result);

                    Intent intent = new Intent(MainActivity.this, ColumnistsActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);

                } else {



                }
            }

            @Override
            public void onFailure(Call<ColumnistsModel> call, Throwable t) {

                // Show progress bar for the clicked item
                itemData.setProgressBarVisible(false);

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();

                Logger.getInstance().logDebug(TAG, "columnists", 3, t.getMessage());
            }
        });


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

    Runnable runnable = new Runnable() {
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