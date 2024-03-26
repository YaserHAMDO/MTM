package com.example.mtm.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.ColumnistAdapter;
import com.example.mtm.adapter.MediaReportAdapter;
import com.example.mtm.model.ColumnistModel;
import com.example.mtm.model.MediaReportModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.ColumnistsResponse;
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
        startDate = MyUtils.getPreviousDate(1);
        endDate = MyUtils.getCurrentDate();

//        filteredDateTextView.setText(startDate + " ile " + endDate + " arasında tarihi kayıtlar gösterilmektedir.");
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
                        progressBar.setVisibility(View.VISIBLE);
                        filteredDateTextView.setText("");
                        this.startDate = formattedStartDate;
                        this.endDate = formattedEndDate;
                        summaryList2(formattedStartDate, formattedEndDate);
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
                    Constants.KEY_MEDIA_REPORT_URL + result.getData().get(i).getMailUrl()
            ));

        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new MediaReportAdapter(this, items, this));



        filteredDateTextView.setText(MyUtils.changeDateFormat(startDate) + " ile " + MyUtils.changeDateFormat(endDate) + " arasında tarihi\nkayıtlar gösterilmektedir.");

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