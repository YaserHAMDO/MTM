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
import com.example.mtm.adapter.InternetAdapter;
import com.example.mtm.adapter.InternetSubListAdapter;
import com.example.mtm.model.InternetModel;
import com.example.mtm.model.InternetSubListModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.InternetResponse;
import com.example.mtm.response.InternetSubResponse;
import com.example.mtm.response.PrintedMediaSubResponse;
import com.example.mtm.response.SubMenuVisualMediaResponse;
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

public class InternetActivity extends AppCompatActivity implements InternetSubListAdapter.OnItemClickListener{

    private static final String TAG = "InternetActivity";

    private ImageView backIconImageView, filterImageView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private MaterialDatePicker materialDatePicker;

    private String startDate, endDate;

    private InternetAdapter adapter;

    private TextView titleTextView;
    private TextView filteredDateTextView;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        init();
        initValues();
        setItemClickListeners();
        setData();
        confDate();


        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);

        switch (index) {
            case 1:
                titleTextView.setText("Yazılı Basın");
                break;

            case 2:
                titleTextView.setText("Digital Basın");
                break;

            case 3:
                titleTextView.setText(R.string.visual_media);
                break;
        }

    }

    private void init() {
        backIconImageView = findViewById(R.id.backIconImageView);
        filterImageView = findViewById(R.id.filterImageView);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        titleTextView = findViewById(R.id.titleTextView);
        filteredDateTextView = findViewById(R.id.filteredDateTextView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

    private void initValues() {
        startDate = MyUtils.getPreviousDate(1);
        endDate = MyUtils.getCurrentDate();

//        filteredDateTextView.setText(startDate + " ile " + endDate + " arasında tarihi kayıtlar gösterilmektedir.");
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
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
                        progressBar.setVisibility(View.VISIBLE);
                        filteredDateTextView.setText("");
                        this.startDate = formattedStartDate;
                        this.endDate = formattedEndDate;
                        switch (index) {
                            case 1:
                                menuList3(formattedStartDate, formattedEndDate);
                                break;

                            case 2:
                                internet(formattedStartDate, formattedEndDate);
                                break;

                            case 3:
                                visualMedia3(formattedStartDate, formattedEndDate);
                                break;
                        }

                    }
                });
    }

    private void setData() {

        InternetResponse result = DataHolder.getInstance().getInternetModel();

        List<InternetModel> items = new ArrayList<>();
        List<InternetSubListModel> items2;


        if(result.getData().getMenu().getMenu() != null) {
            for (int i = 0; i < result.getData().getMenu().getMenu().size(); i++) {
                items2 = new ArrayList<>();

                for (int j = 0; j < result.getData().getMenu().getMenu().get(i).getSubMenus().size(); j++) {
                    items2.add(new InternetSubListModel(
                            result.getData().getMenu().getMenu().get(i).getSubMenus().get(j).getName(),
                            result.getData().getMenu().getMenu().get(i).getSubMenus().get(j).getDocCount(),
                            result.getData().getMenu().getMenu().get(i).getId() + "",
                            result.getData().getMenu().getMenu().get(i).getSubMenus().get(j).getId() + ""
                    ));
                }

                items.add(new InternetModel(
                        result.getData().getMenu().getMenu().get(i).getName(),
                        result.getData().getMenu().getMenu().get(i).getDocCount(),
                        items2
                ));

            }
        }


        adapter = new InternetAdapter(this, items, this) ;

        recyclerView.setAdapter(adapter);

        filteredDateTextView.setText(startDate + " ile " + endDate + " arasında tarihi kayıtlar gösterilmektedir.");

    }

    private void SubInternet(String menuId, String subMenuId, String startDate, String endDate, int count) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<InternetSubResponse> call = apiService.subInternet(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                10,
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
//                "NEWS",
                "UNIGNORED",
                true,
//                "2024010003615660",
                menuId,
                subMenuId,
                false,
                false
        );
        call.enqueue(new Callback<InternetSubResponse>() {
            @Override
            public void onResponse(Call<InternetSubResponse> call, Response<InternetSubResponse> response) {

                Logger.getInstance().logDebug(TAG, "SubMenuVisualMedia", 2, response.body());

                if (response.isSuccessful()) {

                    InternetSubResponse result = response.body();

                    DataHolder.getInstance().setInternetSubModel(result);

                    Intent intent = new Intent(InternetActivity.this, SubInternetActivity.class);

                    intent.putExtra("menuId", menuId);
                    intent.putExtra("subMenuId", subMenuId);
                    intent.putExtra("startDate", startDate);
                    intent.putExtra("endDate", endDate);
                    intent.putExtra("count", count);
                    intent.putExtra("index", index);

                    Bundle options = ActivityOptions.makeCustomAnimation(InternetActivity.this, R.anim.left, R.anim.right).toBundle();
                    startActivity(intent, options);

                } else {



                }
            }

            @Override
            public void onFailure(Call<InternetSubResponse> call, Throwable t) {

                Logger.getInstance().logDebug(TAG, "SubMenuVisualMedia", 3, t.getMessage());
            }
        });


    }

    private void subPrinted(String menuId, String subMenuId, String startDate, String endDate, int count) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<PrintedMediaSubResponse> call = apiService.subMenuList(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                10,
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

                Logger.getInstance().logDebug(TAG, "subMenuList", 2, response.body());

                if (response.isSuccessful()) {

                    PrintedMediaSubResponse result = response.body();

                    DataHolder.getInstance().setPrintedMediaSubResponse(result);

                    Intent intent = new Intent(InternetActivity.this, SubInternetActivity.class);

                    intent.putExtra("menuId", menuId);
                    intent.putExtra("subMenuId", subMenuId);
                    intent.putExtra("startDate", startDate);
                    intent.putExtra("endDate", endDate);
                    intent.putExtra("count", count);
                    intent.putExtra("index", 1);

                    Bundle options = ActivityOptions.makeCustomAnimation(InternetActivity.this, R.anim.left, R.anim.right).toBundle();
                    startActivity(intent, options);

                } else {



                }
            }

            @Override
            public void onFailure(Call<PrintedMediaSubResponse> call, Throwable t) {

                Logger.getInstance().logDebug(TAG, "subMenuList", 3, t.getMessage());
            }
        });


    }

    private void internet(String startDate, String endDate) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<InternetResponse> call = apiService.internet(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                100000,
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
                false,
                false,
                true,
                true,
                true,
//                MyUtils.getPreviousDate(1),
//                MyUtils.getCurrentDate(),
                startDate,
                endDate,
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

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    InternetResponse result = response.body();

                    DataHolder.getInstance().setInternetModel(result);

                    setData();




                } else {

                    if (response.code() == 403) {
//                        forbiddenPopup();
                    }


                }
            }

            @Override
            public void onFailure(Call<InternetResponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

                Logger.getInstance().logDebug(TAG, "visualMedia", 3, t.getMessage());
            }
        });


    }

    private void visualMedia3(String startDate, String endDate) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<InternetResponse> call = apiService.visualMedia2(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                1000,
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
                false,
                false,
                true,
                true,
                true,
                startDate,
                endDate,
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

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    InternetResponse result = response.body();

                    DataHolder.getInstance().setInternetModel(result);

                    setData();


                } else {

                }
            }

            @Override
            public void onFailure(Call<InternetResponse> call, Throwable t) {

                Logger.getInstance().logDebug(TAG, "visualMedia", 3, t.getMessage());

                progressBar.setVisibility(View.GONE);
            }
        });


    }

    private void menuList3(String startDate, String endDate) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<InternetResponse> call = apiService.menuList2(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                10000,
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
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
                true,
                true,
                false
        );

        call.enqueue(new Callback<InternetResponse>() {
            @Override
            public void onResponse(Call<InternetResponse> call, Response<InternetResponse> response) {

                Logger.getInstance().logDebug(TAG, "menuList", 2, response.body());

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    InternetResponse result = response.body();

                    DataHolder.getInstance().setInternetModel(result);

                    setData();


                } else {



                }
            }

            @Override
            public void onFailure(Call<InternetResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Logger.getInstance().logDebug(TAG, "menuList", 3, t.getMessage());
            }
        });


    }

    private void SubMenuVisualMedia(String menuId, String subMenuId, String startDate, String endDate, int count) {


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<SubMenuVisualMediaResponse> call = apiService.subMenuVisualMedia(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                10,
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

                Logger.getInstance().logDebug(TAG, "SubMenuVisualMedia", 2, response.body());

                if (response.isSuccessful()) {

                    SubMenuVisualMediaResponse result = response.body();

                    DataHolder.getInstance().setSubMenuVisualMediaModel(result);

//                    DataHolder.getInstance().setInternetSubModel(result);

                    Intent intent = new Intent(InternetActivity.this, SubInternetActivity.class);

                    intent.putExtra("menuId", menuId);
                    intent.putExtra("subMenuId", subMenuId);
                    intent.putExtra("startDate", startDate);
                    intent.putExtra("endDate", endDate);
                    intent.putExtra("count", count);
                    intent.putExtra("index", index);

                    Bundle options = ActivityOptions.makeCustomAnimation(InternetActivity.this, R.anim.left, R.anim.right).toBundle();
                    startActivity(intent, options);





                } else {


                }
            }

            @Override
            public void onFailure(Call<SubMenuVisualMediaResponse> call, Throwable t) {


                Logger.getInstance().logDebug(TAG, "SubMenuVisualMedia", 3, t.getMessage());
            }
        });

    }


    @Override
    public void onItemClickInternetSubList(String menuId, String subMenuId, int count) {
        switch (index) {
            case 1:
                subPrinted(menuId, subMenuId, startDate, endDate, count);
                break;

            case 2:
                SubInternet(menuId, subMenuId, startDate, endDate, count);
                break;

            case 3:
                SubMenuVisualMedia(menuId, subMenuId, startDate, endDate, count);
                break;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}