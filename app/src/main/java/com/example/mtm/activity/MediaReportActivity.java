package com.example.mtm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.MediaReportAdapter;
import com.example.mtm.model.MediaReportModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.SummaryListResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.Logger;
import com.example.mtm.util.MyUtils;
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

public class MediaReportActivity extends AppCompatActivity implements MediaReportAdapter.OnItemClickListener {

    private static final String TAG = "MediaReportActivity";

    private ImageView backIconImageView, filterImageView;
    private RecyclerView recyclerView;
    private MaterialDatePicker materialDatePicker;
    private TextView filteredDateTextView;
    private ProgressBar progressBar;
    private String startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_report);

        init();
        initValues();
        confDate();
        setItemClickListeners();
        setData();

    }

    private void init() {
        backIconImageView = findViewById(R.id.backIconImageView);
        recyclerView = findViewById(R.id.recyclerView);
        filterImageView = findViewById(R.id.filterImageView);
        filteredDateTextView = findViewById(R.id.filteredDateTextView);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        filterImageView.setOnClickListener(view -> materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER"));
    }

    private void initValues() {
        startDate = MyUtils.getCurrentDate();
        endDate = MyUtils.getCurrentDate();

//        filteredDateTextView.setText(startDate + " ile " + endDate + " arasında tarihi kayıtlar gösterilmektedir.");
    }

    private void confDate() {
        MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Tarih Seç");
        materialDateBuilder.setTheme(R.style.MaterialDatePicker); // You can customize the theme if needed
        materialDatePicker = materialDateBuilder.build();


        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            Long selectedDate = (Long) selection;
            if (selectedDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedSelectedDate = sdf.format(new Date(selectedDate));
                filteredDateTextView.setText("");
                this.startDate = formattedSelectedDate;
                this.endDate = formattedSelectedDate;

                        summaryList2(formattedSelectedDate, formattedSelectedDate);
                    }
                });
    }


    private void setData() {


        SummaryListResponse result = DataHolder.getInstance().getSummaryListResponse();

        List<MediaReportModel> items = new ArrayList<>();

        for (int i = 0; i < result.getData().size(); i++) {

            items.add(new MediaReportModel(
                    MyUtils.changeDateFormat(startDate),
                    result.getData().get(i).getName(),
                    result.getData().get(i).getName(),
                    "https://mailservice.medyatakip.com" + result.getData().get(i).getMailUrl() + "?date=" + startDate
            ));

        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new MediaReportAdapter(this, items, this));


        filteredDateTextView.setText(MyUtils.changeDateFormat(startDate) + " tarihi kayıtlar gösterilmektedir.");
//        filteredDateTextView.setText(MyUtils.changeDateFormat(startDate) + " ile " + MyUtils.changeDateFormat(endDate) + " arasında tarihi\nkayıtlar gösterilmektedir.");

    }

    private void summaryList2(String startDate, String endDate) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<SummaryListResponse> call = apiService.summaryList(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
//                "2024-02-01",
                startDate,
                endDate
//                true,
//                true,
//                true,
//                true,
//                true,
//                true,
//                true,
//                true
        );

        call.enqueue(new Callback<SummaryListResponse>() {
            @Override
            public void onResponse(Call<SummaryListResponse> call, Response<SummaryListResponse> response) {

                Logger.getInstance().logDebug(TAG, "summaryList", 2, response.body());

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    SummaryListResponse result = response.body();

                    DataHolder.getInstance().setSummaryListResponse(result);

                    setData();

                } else {

                    if (response.code() == 403) {
//                        forbiddenPopup();
                    }

                    else {
//                        refreshToken2(1);
                    }


                }
            }

            @Override
            public void onFailure(Call<SummaryListResponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

                Logger.getInstance().logDebug(TAG, "summaryList", 3, t.getMessage());
            }
        });


    }


    @Override
    public void onItemClick(int index) {
//        Intent intent = new Intent(this, ImageShowActivity.class);
//        intent.putExtra("imageUrl", mediaPath);
//        startActivity(intent);

//
//        Intent intent = new Intent(this, ColumnistsShowActivity.class);
//        intent.putExtra("index", index);
//        startActivity(intent);


    }


}