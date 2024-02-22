package com.example.mtm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.MediaAgendaTitleAdapter;
import com.example.mtm.adapter.MediaAgendaBodyAdapter;
import com.example.mtm.model.MediaAgendaModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.MediaAgendaResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.Logger;
import com.example.mtm.util.PreferenceManager;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MediaAgendaActivity extends AppCompatActivity implements MediaAgendaTitleAdapter.OnItemClickListener,  MediaAgendaBodyAdapter.OnItemClickListener{

    private static final String TAG = "InternetActivity";

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private MediaAgendaTitleAdapter adapter;
    private MediaAgendaBodyAdapter adapter2;
    private ImageView backIconImageView, filterImageView;
    private TextView seeAll;
    private ArrayList<MediaAgendaModel> all, politicsModels, economyModels, worldModels, cultureModels, lifeModels, sportsModels, isModels;
    private ProgressBar progressBar;

    private MaterialDatePicker materialDatePicker;

    private ArrayList<String> columnistsShowArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_agenda);

        init();
        setItemClickListeners();
        setData();
        confDate();

    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.recyclerView2);
        backIconImageView = findViewById(R.id.backIconImageView);
        recyclerView = findViewById(R.id.recyclerView);
        filterImageView = findViewById(R.id.filterImageView);
        progressBar = findViewById(R.id.progressBar);
        seeAll = findViewById(R.id.seeAll);

        columnistsShowArray = new ArrayList<>();
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        seeAll.setOnClickListener(view -> {
            adapter.clearSelection();
            setTypesList2(all);
        });
        filterImageView.setOnClickListener(view -> materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER"));
    }
    private void confDate() {
        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDateBuilder.setTitleText("Tarih Seç");
        materialDateBuilder.setTheme(R.style.MaterialDatePicker);
        materialDatePicker = materialDateBuilder.build();

//        materialDatePicker.addOnPositiveButtonClickListener(
//                new MaterialPickerOnPositiveButtonClickListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onPositiveButtonClick(Object selection) {
//
//                        // if the user clicks on the positive
//                        // button that is ok button update the
//                        // selected date
////                        mShowSelectedDateText.setText("Selected Date is : " + materialDatePicker.getHeaderText());
//                        // in the above statement, getHeaderText
//                        // will return selected date preview from the
//                        // dialog
//                    }
//                });

        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {
                    Pair<Long, Long> selectionPair = (Pair<Long, Long>) materialDatePicker.getSelection();
                    if (selectionPair != null) {
                        long startDate = selectionPair.first;
                        long endDate = selectionPair.second;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String formattedStartDate = sdf.format(new Date(startDate));
                        String formattedEndDate = sdf.format(new Date(endDate));
//                        Toast.makeText(this, "Start Date: " + formattedStartDate + "\nEnd Date: " + formattedEndDate, Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(null);
                        recyclerView2.setAdapter(null);
                        progressBar.setVisibility(View.VISIBLE);
                        getMediaAgenda2(formattedStartDate, formattedEndDate);


                    }
                });
    }


    private void setData() {

        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        MediaAgendaResponse result = DataHolder.getInstance().getMediaAgendaModel();

        if (result != null) {
            ArrayList<String> userMatch2 = new ArrayList<>();
            all = new ArrayList<>();
            politicsModels = new ArrayList<>();
            economyModels = new ArrayList<>();
            worldModels = new ArrayList<>();
            cultureModels = new ArrayList<>();
            lifeModels = new ArrayList<>();
            sportsModels = new ArrayList<>();
            isModels = new ArrayList<>();

            String genderType = "";

            for (int i = 0; i < result.getData().getDocs().size(); i++) {

                String newGenderType = result.getData().getDocs().get(i).getAgendaType().getName();
                if (!newGenderType.equals(genderType)) {
                    userMatch2.add(newGenderType);
                    genderType = newGenderType;
                }
            }

            setTypesList(userMatch2);

            String videoUrl;
            String magazineUrl;

            for (int i = 0; i < result.getData().getDocs().size() ; i++) {

                videoUrl = "";
                magazineUrl = "";
                if (result.getData().getDocs() != null && result.getData().getDocs().get(i).getClips() != null && result.getData().getDocs().get(i).getClips().getBc() != null && result.getData().getDocs().get(i).getClips().getBc().getDocs() != null && result.getData().getDocs().get(i).getClips().getBc().getDocs().size() > 0) {
                    videoUrl =  Constants.KEY_VIDEO_BASIC_URL + result.getData().getDocs().get(i).getClips().getBc().getDocs().get(0).getVideo();
                }
                if (result.getData().getDocs() != null && result.getData().getDocs().get(i).getClips() != null && result.getData().getDocs().get(i).getClips().getPm() != null && result.getData().getDocs().get(i).getClips().getPm().getDocs() != null && result.getData().getDocs().get(i).getClips().getPm().getDocs().size() > 0) {
                    magazineUrl =  Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getClips().getPm().getDocs().get(0).getImageStoragePath();
                }

                String type = result.getData().getDocs().get(i).getAgendaType().getName();

                switch (type) {
                    case "Siyaset":
                        politicsModels.add(new MediaAgendaModel(
                                type,
                                Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                magazineUrl,
                                videoUrl,
                                result.getData().getDocs().get(i).getAgendaType().getName(),
                                result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                        ));
                        break;

                    case "Ekonomi":
                        economyModels.add(new MediaAgendaModel(
                                type,
                                Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                magazineUrl,
                                videoUrl,
                                result.getData().getDocs().get(i).getAgendaType().getName(),
                                result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                        ));
                        break;

                    case "Dünya":
                        worldModels.add(new MediaAgendaModel(
                                type,
                                Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                magazineUrl,
                                videoUrl,
                                result.getData().getDocs().get(i).getAgendaType().getName(),
                                result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                        ));
                        break;

                    case "Kültür-Sanat":
                        cultureModels.add(new MediaAgendaModel(
                                type,
                                Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                magazineUrl,
                                videoUrl,
                                result.getData().getDocs().get(i).getAgendaType().getName(),
                                result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                        ));
                        break;

                    case "Yaşam":
                        lifeModels.add(new MediaAgendaModel(
                                type,
                                Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                magazineUrl,
                                videoUrl,
                                result.getData().getDocs().get(i).getAgendaType().getName(),
                                result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                        ));
                        break;

                    case "Spor":
                        sportsModels.add(new MediaAgendaModel(
                                type,
                                Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                magazineUrl,
                                videoUrl,
                                result.getData().getDocs().get(i).getAgendaType().getName(),
                                result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                        ));
                        break;

                    case "İş Dünyası":
                        isModels.add(new MediaAgendaModel(
                                type,
                                Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                magazineUrl,
                                videoUrl,
                                result.getData().getDocs().get(i).getAgendaType().getName(),
                                result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                        ));
                        break;
                }

                all.add(new MediaAgendaModel(
                        type,
                        Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                        magazineUrl,
                        videoUrl,
                        result.getData().getDocs().get(i).getAgendaType().getName(),
                        result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                ));

            }

            setTypesList2(all);
        } else {
            // Handle case where no data is available
        }

    }

    private void setTypesList(List<String> dataList) {
        recyclerView.setAdapter(null);
        adapter = new MediaAgendaTitleAdapter(this, dataList, this);
        recyclerView.setAdapter(adapter);
    }

    private void setTypesList2(List<MediaAgendaModel> dataList) {
        recyclerView2.setAdapter(null);
        columnistsShowArray.clear();
        adapter2 = new MediaAgendaBodyAdapter(this, dataList, this);
        recyclerView2.setAdapter(adapter2);

        for (int i = 0; i < dataList.size(); i++) {
            columnistsShowArray.add(dataList.get(i).getMagazineImageUrl());
        }
    }

    private void getMediaAgenda2(String startDate, String endDate) {


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<MediaAgendaResponse> call = apiService.getMediaAgenda2(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
                true,
                true,
                true,
                true,
                true,
                startDate,
                endDate
        );

        call.enqueue(new Callback<MediaAgendaResponse>() {
            @Override
            public void onResponse(Call<MediaAgendaResponse> call, Response<MediaAgendaResponse> response) {

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 2, response.body());

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {






                    MediaAgendaResponse result = response.body();


                    DataHolder.getInstance().setMediaAgendaModel(result);



                    setData();


                } else {

                    if (response.code() == 403) {
//                        forbiddenPopup();
                    }

                }
            }

            @Override
            public void onFailure(Call<MediaAgendaResponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 3, t.getMessage());
            }
        });


    }

    @Override
    public void onItemClick(String position) {

        recyclerView2.setAdapter(null);

        switch (position) {
            case "Siyaset":
                setTypesList2(politicsModels);
                break;

            case "Ekonomi":
                setTypesList2(economyModels);
                break;

            case "Dünya":
                setTypesList2(worldModels);
                break;

            case "Kültür-Sanat":
                setTypesList2(cultureModels);
                break;

            case "Yaşam":
                setTypesList2(lifeModels);
                break;

            case "Spor":
                setTypesList2(sportsModels);
                break;

            case "İş Dünyası":
                setTypesList2(isModels);
                break;
        }


    }

    @Override
    public void onItemClick(int position) {

        DataHolder.getInstance().setColumnistsShowArray(columnistsShowArray);

        Intent intent = new Intent(this, ColumnistsShowActivity.class);
        intent.putExtra("index", position);
        startActivity(intent);



    }
}