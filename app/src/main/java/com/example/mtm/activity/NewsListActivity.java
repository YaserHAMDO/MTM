package com.example.mtm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mtm.R;
import com.example.mtm.adapter.InternetAdapter;
import com.example.mtm.adapter.InternetSubListAdapter;
import com.example.mtm.model.InternetModel;
import com.example.mtm.model.InternetSubListModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.InternetResponse;
import com.example.mtm.response.InternetSubResponse;
import com.example.mtm.response.MenuListResponse;
import com.example.mtm.response.PrintedMediaSubResponse;
import com.example.mtm.response.SubMenuVisualMediaResponse;
import com.example.mtm.response.VisualMediaResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.Logger;
import com.example.mtm.util.MyUtils;
import com.example.mtm.util.PreferenceManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListActivity extends AppCompatActivity implements InternetSubListAdapter.OnItemClickListener {

    private static final String TAG = "NewsListActivity";

    private TextView internetTextView, tvTextView, menuListTextView;
    private ImageView backIconImageView, notificationImageView;
    private RecyclerView recyclerView;
    private ConstraintLayout menuListConstraintLayout, internetConstraintLayout, tvConstraintLayout;
    private View menuListView, internetView, tvView;
    private TextView filteredDateTextView;
    int index = 1;

    private int newListIndex = 0;

    String startDay, endDay;

    private MaterialDatePicker materialDatePicker;

    private ProgressBar progressBar;

    private TextView dateTextView, allTextView, newsTextView, adsTextView, okTextView;

    private boolean materialDatePickerControl;

    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        init();
        setItemClickListeners();
        setMenuList();
    }

    private void init() {
        internetTextView = findViewById(R.id.internetTextView);
        tvTextView = findViewById(R.id.tvTextView);
        menuListTextView = findViewById(R.id.menuListTextView);
        backIconImageView = findViewById(R.id.backIconImageView);
        notificationImageView = findViewById(R.id.notificationImageView);
        recyclerView = findViewById(R.id.recyclerView);
        menuListConstraintLayout = findViewById(R.id.menuListConstraintLayout);
        internetConstraintLayout = findViewById(R.id.internetConstraintLayout);
        tvConstraintLayout = findViewById(R.id.tvConstraintLayout);
        menuListView = findViewById(R.id.menuListView);
        internetView = findViewById(R.id.internetView);
        tvView = findViewById(R.id.tvView);
        progressBar = findViewById(R.id.progressBar);
        filteredDateTextView = findViewById(R.id.filteredDateTextView);

        materialDatePickerControl = false;

        startDay = MyUtils.getPreviousDate(1);
        endDay = MyUtils.getCurrentDate();

        filteredDateTextView.setText(MyUtils.changeDateFormat(startDay) + " ile " + MyUtils.changeDateFormat(endDay) + " arasında tarihi\nkayıtlar gösterilmektedir.");

    }

    private void setItemClickListeners() {
        internetConstraintLayout.setOnClickListener(view -> setInternetData());
        tvConstraintLayout.setOnClickListener(view -> setVisualData());
        menuListConstraintLayout.setOnClickListener(view -> setMenuList());

        notificationImageView.setOnClickListener(view -> initBottomSheet());

        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());


        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDateBuilder.setTitleText("Tarih Seç");
        materialDateBuilder.setTheme(R.style.MaterialDatePicker);
        materialDatePicker = materialDateBuilder.build();



        materialDatePicker.addOnDismissListener(dialog -> {
            materialDatePickerControl = false;
        });

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
//                        recyclerView.setAdapter(null);
//                        progressBar.setVisibility(View.VISIBLE);

                        this.startDay = formattedStartDate;
                        this.endDay = formattedEndDate;

                        dateTextView.setText("Tarih: " + MyUtils.changeDateFormat(formattedStartDate) + " ile " + MyUtils.changeDateFormat(formattedEndDate)  + " arasında.");
                        dateTextView.setTextColor(getColor(R.color.color6));



//                        switch (index) {
//                            case 1:
//                                menuList3(formattedStartDate, formattedEndDate);
//                                break;
//
//                            case 2:
//                                internet(formattedStartDate, formattedEndDate);
//                                break;
//
//                            case 3:
//                                visualMedia3(formattedStartDate, formattedEndDate);
//                                break;
//                        }

                    }
                });


    }

    private void initBottomSheet() {

        bottomSheetDialog = new BottomSheetDialog(this ,R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.filter_pop_up_layout);

        dateTextView = bottomSheetDialog.findViewById(R.id.dateTextView);
        allTextView = bottomSheetDialog.findViewById(R.id.allTextView);
        newsTextView = bottomSheetDialog.findViewById(R.id.newsTextView);
        adsTextView = bottomSheetDialog.findViewById(R.id.adsTextView);
        okTextView = bottomSheetDialog.findViewById(R.id.okTextView);

        if (index == 2) {
            LinearLayout linearLayout = bottomSheetDialog.findViewById(R.id.linearLayout);
            linearLayout.setVisibility(View.GONE);
        }


        dateTextView.setOnClickListener(v -> {

            if (!materialDatePickerControl) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                materialDatePickerControl = true;
            }

//            bottomSheetDialog.dismiss();
        });

        allTextView.setOnClickListener(v -> {

            allTextView.setTextColor(getColor(R.color.white));
            newsTextView.setTextColor(getColor(R.color.white));
            adsTextView.setTextColor(getColor(R.color.white));

            allTextView.setBackground(getDrawable(R.drawable.test_f));
            newsTextView.setBackground(getDrawable(R.drawable.test_f));
            adsTextView.setBackground(getDrawable(R.drawable.test_f));
        });

        newsTextView.setOnClickListener(v -> {

            allTextView.setTextColor(getColor(R.color.color3));
            newsTextView.setTextColor(getColor(R.color.white));
            adsTextView.setTextColor(getColor(R.color.color3));

            allTextView.setBackground(getDrawable(R.drawable.test_d));
            newsTextView.setBackground(getDrawable(R.drawable.test_f));
            adsTextView.setBackground(getDrawable(R.drawable.test_d));
        });

        adsTextView.setOnClickListener(v -> {

            allTextView.setTextColor(getColor(R.color.color3));
            newsTextView.setTextColor(getColor(R.color.color3));
            adsTextView.setTextColor(getColor(R.color.white));

            allTextView.setBackground(getDrawable(R.drawable.test_d));
            newsTextView.setBackground(getDrawable(R.drawable.test_d));
            adsTextView.setBackground(getDrawable(R.drawable.test_f));
        });

        okTextView.setOnClickListener(v -> {

            recyclerView.setAdapter(null);
            progressBar.setVisibility(View.VISIBLE);
            internetConstraintLayout.setVisibility(View.INVISIBLE);
            tvConstraintLayout.setVisibility(View.INVISIBLE);
            menuListConstraintLayout.setVisibility(View.INVISIBLE);


            newsList2();

            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void newsList2() {
        newListIndex = 0;

        filteredDateTextView.setText("");

        menuList2(startDay, endDay);
        internet2(startDay, endDay);
        visualMedia2(startDay, endDay);

    }

    private void menuList2(String startDay, String endDay) {


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<MenuListResponse> call = apiService.menuList(
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
                startDay,
                endDay,
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

                newListIndex++;

                if (response.isSuccessful()) {

                    MenuListResponse result = response.body();

                    DataHolder.getInstance().setMenuListResponse(result);


                        if (newListIndex == 3) {

                            newListIndex = 0;

                            progressBar.setVisibility(View.INVISIBLE);
                            internetConstraintLayout.setVisibility(View.VISIBLE);
                            tvConstraintLayout.setVisibility(View.VISIBLE);
                            menuListConstraintLayout.setVisibility(View.VISIBLE);

                            switch (index) {
                                case 1:
                                    setMenuList();
                                    break;

                                case 2:
                                    setInternetData();
                                    break;

                                case 3:
                                    setVisualData();
                                    break;
                            }

                        }


                } else {




                }
            }

            @Override
            public void onFailure(Call<MenuListResponse> call, Throwable t) {


                progressBar.setVisibility(View.INVISIBLE);
                internetConstraintLayout.setVisibility(View.VISIBLE);
                tvConstraintLayout.setVisibility(View.VISIBLE);
                menuListConstraintLayout.setVisibility(View.VISIBLE);

                Logger.getInstance().logDebug(TAG, "menuList", 3, t.getMessage());
            }
        });


    }

    private void internet2(String startDay, String endDay) {

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
                startDay,
                endDay,
                "07:00:00",
                "23:59:00",
                "UNIGNORED",
                true,
                true,
                true,
                "NEWS"
        );

        call.enqueue(new Callback<InternetResponse>() {
            @Override
            public void onResponse(Call<InternetResponse> call, Response<InternetResponse> response) {

                Logger.getInstance().logDebug(TAG, "visualMedia", 2, response.body());

                newListIndex++;

                if (response.isSuccessful()) {



                    InternetResponse result = response.body();

                    DataHolder.getInstance().setInternetModel(result);

                    if (newListIndex == 3) {

                        newListIndex = 0;

                        progressBar.setVisibility(View.INVISIBLE);
                        internetConstraintLayout.setVisibility(View.VISIBLE);
                        tvConstraintLayout.setVisibility(View.VISIBLE);
                        menuListConstraintLayout.setVisibility(View.VISIBLE);

                        switch (index) {
                            case 1:
                                setMenuList();
                                break;

                            case 2:
                                setInternetData();
                                break;

                            case 3:
                                setVisualData();
                                break;
                        }

                    }






                } else {


                }
            }

            @Override
            public void onFailure(Call<InternetResponse> call, Throwable t) {

                progressBar.setVisibility(View.INVISIBLE);
                internetConstraintLayout.setVisibility(View.VISIBLE);
                tvConstraintLayout.setVisibility(View.VISIBLE);
                menuListConstraintLayout.setVisibility(View.VISIBLE);

                Logger.getInstance().logDebug(TAG, "visualMedia", 3, t.getMessage());
            }
        });


    }

    private void visualMedia2(String startDay, String endDay) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<VisualMediaResponse> call = apiService.visualMedia(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                1000,
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
                false,
                false,
                true,
                true,
                true,
                startDay,
                endDay,
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

                newListIndex++;

                if (response.isSuccessful()) {

                    VisualMediaResponse result = response.body();

                    DataHolder.getInstance().setVisualMediaModel(result);


                        if (newListIndex == 3) {

                            newListIndex = 0;

                            progressBar.setVisibility(View.INVISIBLE);
                            internetConstraintLayout.setVisibility(View.VISIBLE);
                            tvConstraintLayout.setVisibility(View.VISIBLE);
                            menuListConstraintLayout.setVisibility(View.VISIBLE);

                            switch (index) {
                                case 1:
                                    setMenuList();
                                    break;

                                case 2:
                                    setInternetData();
                                    break;

                                case 3:
                                    setVisualData();
                                    break;
                            }
                        }





                } else {


                }
            }

            @Override
            public void onFailure(Call<VisualMediaResponse> call, Throwable t) {

                progressBar.setVisibility(View.INVISIBLE);
                internetConstraintLayout.setVisibility(View.VISIBLE);
                tvConstraintLayout.setVisibility(View.VISIBLE);
                menuListConstraintLayout.setVisibility(View.VISIBLE);

                Logger.getInstance().logDebug(TAG, "visualMedia", 3, t.getMessage());
            }
        });


    }



    private void updateUI(int i) {

        filteredDateTextView.setText(MyUtils.changeDateFormat(startDay) + " ile " + MyUtils.changeDateFormat(endDay) + " arasında tarihi\nkayıtlar gösterilmektedir.");

        switch (i) {
            case 0:

                index = 1;

                menuListTextView.setTextColor(getColor(R.color.black));
                internetTextView.setTextColor(getColor(R.color.color1));
                tvTextView.setTextColor(getColor(R.color.color1));

                menuListView.setVisibility(View.VISIBLE);
                internetView.setVisibility(View.INVISIBLE);
                tvView.setVisibility(View.INVISIBLE);

              break;

            case 1:

                index = 2;

                menuListTextView.setTextColor(getColor(R.color.color1));
                internetTextView.setTextColor(getColor(R.color.black));
                tvTextView.setTextColor(getColor(R.color.color1));

                menuListView.setVisibility(View.INVISIBLE);
                internetView.setVisibility(View.VISIBLE);
                tvView.setVisibility(View.INVISIBLE);

              break;

            case 2:

                index = 3;

                menuListTextView.setTextColor(getColor(R.color.color1));
                internetTextView.setTextColor(getColor(R.color.color1));
                tvTextView.setTextColor(getColor(R.color.black));

                menuListView.setVisibility(View.INVISIBLE);
                internetView.setVisibility(View.INVISIBLE);
                tvView.setVisibility(View.VISIBLE);
              break;
        }
    }

    private void setMenuList() {

        updateUI(0);

        MenuListResponse result = DataHolder.getInstance().getMenuListResponse();

        List<InternetModel> items = new ArrayList<>();
        List<InternetSubListModel> items2;


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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new InternetAdapter(this, items, this));
    }

    private void setInternetData() {

        updateUI(1);

        InternetResponse result = DataHolder.getInstance().getInternetModel();

        List<InternetModel> items = new ArrayList<>();
        List<InternetSubListModel> items2;


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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new InternetAdapter(this, items, this));
    }

    private void setVisualData() {

        updateUI(2);

        VisualMediaResponse result = DataHolder.getInstance().getVisualMediaModel();

        List<InternetModel> items = new ArrayList<>();
        List<InternetSubListModel> items2;


        for (int i = 0; i < result.getData().getMenu().getMenu().size(); i++) {
            items2 = new ArrayList<>();

            for (int j = 0; j < result.getData().getMenu().getMenu().get(i).getSubMenus().size(); j++) {

                items2.add(new InternetSubListModel(
                        result.getData().getMenu().getMenu().get(i).getSubMenus().get(j).getName(),
                        result.getData().getMenu().getMenu().get(i).getSubMenus().get(j).getNewsCount(),
                        result.getData().getMenu().getMenu().get(i).getId() + "",
                        result.getData().getMenu().getMenu().get(i).getSubMenus().get(j).getId() + ""
                ));

            }

            items.add(new InternetModel(
                    result.getData().getMenu().getMenu().get(i).getName(),
                    result.getData().getMenu().getMenu().get(i).getNewsCount(),
                    items2
            ));

        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new InternetAdapter(this, items, this));
    }

    private void SubMenuVisualMedia(String menuId, String subMenuId) {


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<SubMenuVisualMediaResponse> call = apiService.subMenuVisualMedia(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                50,
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
                MyUtils.getPreviousDate(1),
                MyUtils.getCurrentDate(),
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

//                    Intent intent = new Intent(NewsListActivity.this, SubVisualMediaActivity.class);
////                    intent.putExtra("itemData", itemData.getText());
//                    startActivity(intent);

                    Intent intent = new Intent(NewsListActivity.this, SubInternetActivity.class);

                    intent.putExtra("menuId", menuId);
                    intent.putExtra("subMenuId", subMenuId);
                    intent.putExtra("startDate", startDay);
                    intent.putExtra("endDate", endDay);
                    intent.putExtra("count", 5);
                    intent.putExtra("index", 1);

                    Bundle options = ActivityOptions.makeCustomAnimation(NewsListActivity.this, R.anim.left, R.anim.right).toBundle();
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


/*
    private void SubInternet(String menuId, String subMenuId, int count) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<InternetSubResponse> call = apiService.subInternet(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                10000,
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
                MyUtils.getPreviousDate(1),
                MyUtils.getCurrentDate(),
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


                    Intent intent = new Intent(NewsListActivity.this, SubInternetActivity.class);

                    intent.putExtra("menuId", menuId);
                    intent.putExtra("subMenuId", subMenuId);
                    intent.putExtra("startDate", MyUtils.getPreviousDate(1));
                    intent.putExtra("endDate", MyUtils.getCurrentDate());
                    intent.putExtra("count", count);
                    intent.putExtra("index", 2);

                    Bundle options = ActivityOptions.makeCustomAnimation(NewsListActivity.this, R.anim.left, R.anim.right).toBundle();
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
*/

    private void SubInternet(String menuId, String subMenuId, String startDate, String endDate, int count) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<InternetSubResponse> call = apiService.subInternet(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                50,
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
                "NEWS",
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

                    Intent intent = new Intent(NewsListActivity.this, SubInternetActivity.class);

                    intent.putExtra("menuId", menuId);
                    intent.putExtra("subMenuId", subMenuId);
                    intent.putExtra("startDate", startDate);
                    intent.putExtra("endDate", endDate);
                    intent.putExtra("count", count);
                    intent.putExtra("index", index);

                    Bundle options = ActivityOptions.makeCustomAnimation(NewsListActivity.this, R.anim.left, R.anim.right).toBundle();
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
                50,
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

                    Intent intent = new Intent(NewsListActivity.this, SubInternetActivity.class);

                    intent.putExtra("menuId", menuId);
                    intent.putExtra("subMenuId", subMenuId);
                    intent.putExtra("startDate", startDate);
                    intent.putExtra("endDate", endDate);
                    intent.putExtra("count", count);
                    intent.putExtra("index", 1);

                    Bundle options = ActivityOptions.makeCustomAnimation(NewsListActivity.this, R.anim.left, R.anim.right).toBundle();
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

    private void SubMenuVisualMedia(String menuId, String subMenuId, String startDate, String endDate, int count) {


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<SubMenuVisualMediaResponse> call = apiService.subMenuVisualMedia(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                50,
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

                    Intent intent = new Intent(NewsListActivity.this, SubInternetActivity.class);

                    intent.putExtra("menuId", menuId);
                    intent.putExtra("subMenuId", subMenuId);
                    intent.putExtra("startDate", startDate);
                    intent.putExtra("endDate", endDate);
                    intent.putExtra("count", count);
                    intent.putExtra("index", index);

                    Bundle options = ActivityOptions.makeCustomAnimation(NewsListActivity.this, R.anim.left, R.anim.right).toBundle();
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


//    @Override
//    public void onItemClickSubVisualMedia(String menuId, String subMenuId) {
//        SubMenuVisualMedia(menuId, subMenuId);
//    }

    @Override
    public void onItemClickInternetSubList(String menuId, String subMenuId, int count) {

        switch (index) {
            case 1:
                subPrinted(menuId, subMenuId, startDay, endDay, count);
                break;

            case 2:
                SubInternet(menuId, subMenuId, startDay, endDay, count);
                break;

            case 3:
                SubMenuVisualMedia(menuId, subMenuId, startDay, endDay, count);
                break;
        }


    }
}