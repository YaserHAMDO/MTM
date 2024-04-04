package com.example.mtm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.NewspaperAdapter;
import com.example.mtm.model.NewspaperModel;
import com.example.mtm.model.SelimModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.MagazineFullPagesResponse;
import com.example.mtm.response.NewsPaperFullPagesResponse;
import com.example.mtm.response.NewspaperFirstPagesResponse;
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

public class NewspaperActivity extends AppCompatActivity implements NewspaperAdapter.OnItemClickListener {

    private static final String TAG = "NewspaperActivity";

    private RecyclerView recyclerView;
    private ImageView backIconImageView, filterImageView;
    private MaterialDatePicker materialDatePicker;
    private boolean materialDatePickerControl;
    private ProgressBar progressBar;
    private BottomSheetDialog bottomSheetDialog;
    private TextView dateTextView, allTextView, newsTextView, adsTextView, okTextView;
    private String startDate, endDate;
    private TextView filteredDateTextView, title;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper);

        init();

        setItemClickListeners();

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);

        switch (index) {
            case 1:
                startDate = MyUtils.getCurrentDate();
                endDate = MyUtils.getCurrentDate();
                title.setText("Gazete Menşetleri");
                setData();
                break;
            case 2:
                startDate = MyUtils.getFirstDateOfMonth();
                endDate = MyUtils.getCurrentDate();
                title.setText("Dergi Kapakları");
                setMagazineData();
                break;
        }

        confDate();

    }

    private void init() {
        backIconImageView = findViewById(R.id.backIconImageView);
        filterImageView = findViewById(R.id.filterImageView);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        filteredDateTextView = findViewById(R.id.filteredDateTextView);
        title = findViewById(R.id.title);

        materialDatePickerControl = false;



    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        filterImageView.setOnClickListener(view -> initBottomSheet());
    }

    private void initBottomSheet() {

        bottomSheetDialog = new BottomSheetDialog(this ,R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.filter_pop_up_layout);


        LinearLayout linearLayout = bottomSheetDialog.findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.GONE);

        dateTextView = bottomSheetDialog.findViewById(R.id.dateTextView);
        allTextView = bottomSheetDialog.findViewById(R.id.allTextView);
        newsTextView = bottomSheetDialog.findViewById(R.id.newsTextView);
        adsTextView = bottomSheetDialog.findViewById(R.id.adsTextView);
        okTextView = bottomSheetDialog.findViewById(R.id.okTextView);


//        allTextView.setVisibility(View.GONE);
//        newsTextView.setVisibility(View.GONE);
//        adsTextView.setVisibility(View.GONE);

        dateTextView.setOnClickListener(v -> {

            if (!materialDatePickerControl) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                materialDatePickerControl = true;
            }

//            bottomSheetDialog.dismiss();
        });

//        if (startDate != null && endDate != null) {
//            dateTextView.setText("Tarih: " + MyUtils.changeDateFormat(startDate) + " ile " + MyUtils.changeDateFormat(startDate)  + " arasında.");
//        }


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

            switch (index) {
                case 1:
                    newspaperFirstPages2(startDate, endDate);
                    break;
                case 2:
                    magazine2(startDate, endDate);
                    break;
            }


            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }


    private void newspaperFirstPages2(String startDate, String endDate) {

        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<NewspaperFirstPagesResponse> call = apiService.newspaperFirstPages(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
                0,
                50,
                startDate
        );

        call.enqueue(new Callback<NewspaperFirstPagesResponse>() {
            @Override
            public void onResponse(Call<NewspaperFirstPagesResponse> call, Response<NewspaperFirstPagesResponse> response) {

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 2, response.body());

                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                if (response.isSuccessful()) {




                    NewspaperFirstPagesResponse result = response.body();


                    ArrayList<SelimModel> selimModel = new ArrayList<>();

                    if (result.getData() != null) {
                        for (int i = 0; i < result.getData().size(); i++) {
                            selimModel.add(new SelimModel(
                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageInfo().getMediaPath() + "page/" + result.getData().get(i).getImageInfo().getPageFile() + "-1.jpg?sz=full",
                                    result.getData().get(i).getName(),
                                    MyUtils.changeDateFormat(result.getData().get(i).getDate())
                            ));
                        }
                    }
                  

                    DataHolder.getInstance().setSelimModels(selimModel);
                    

                    DataHolder.getInstance().setNewspaperFirstPagesModel(result);


                    setData();


                } else {


                }
            }

            @Override
            public void onFailure(Call<NewspaperFirstPagesResponse> call, Throwable t) {

                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 3, t.getMessage());
            }
        });


    }



    private void confDate() {

        switch (index) {
            case 1:
                MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
                materialDateBuilder.setTitleText("Tarih Seç");
                materialDateBuilder.setTheme(R.style.MaterialDatePicker); // You can customize the theme if needed
                materialDatePicker = materialDateBuilder.build();

                materialDatePicker.addOnDismissListener(dialog -> {
                    materialDatePickerControl = false;
                });

                materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                    Long selectedDate = (Long) selection;
                    if (selectedDate != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String formattedSelectedDate = sdf.format(new Date(selectedDate));
                        filteredDateTextView.setText("");
                        this.startDate = formattedSelectedDate;
                        this.endDate = formattedSelectedDate;

                        dateTextView.setText("Tarih: " + MyUtils.changeDateFormat(formattedSelectedDate));
                        dateTextView.setTextColor(getColor(R.color.color6));
                    }
                });

                break;

            case 2:
                MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder2 = MaterialDatePicker.Builder.dateRangePicker();
                materialDateBuilder2.setTitleText("Tarih Seç");
                materialDateBuilder2.setTheme(R.style.MaterialDatePicker);
                materialDatePicker = materialDateBuilder2.build();



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

                                filteredDateTextView.setText("");
                                this.startDate = formattedStartDate;
                                this.endDate = formattedEndDate;

                                dateTextView.setText("Tarih: " + MyUtils.changeDateFormat(formattedStartDate) + " ile " + MyUtils.changeDateFormat(formattedEndDate)  + " arasında.");
                                dateTextView.setTextColor(getColor(R.color.color6));

//                        columnists2(formattedStartDate, formattedEndDate);
                            }
                        });
                break;
        }

    }


    private void setData() {
        
        NewspaperFirstPagesResponse result = DataHolder.getInstance().getNewspaperFirstPagesModel();
        
        List<NewspaperModel> items = new ArrayList<>();

        if (result != null && result.getData() != null && result.getData().size() > 0) {
            for (int i = 0; i < result.getData().size(); i++) {
                items.add(new NewspaperModel(
                        Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageFirstPage(),
                        result.getData().get(i).getName(),
                        result.getData().get(i).getImageInfo().getMediaPath(),
                        result.getData().get(i).getImageInfo().getPageFile(),
                        result.getData().get(i).getGno() + "",
                        1

                ));

            }
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(new NewspaperAdapter(this, items, this));

            filteredDateTextView.setText(MyUtils.changeDateFormat(startDate) + " tarihi kayıtlar gösterilmektedir.");


        }
        
        else {
            Toast.makeText(this, "Seçtiğiniz tarihte kayıtlar bulunmamaktadır.", Toast.LENGTH_SHORT).show();
        }



    }

    private void setMagazineData() {

        MagazineFullPagesResponse result = DataHolder.getInstance().getMagazineFullPagesModel();

        List<NewspaperModel> items = new ArrayList<>();

        if (result != null && result.getData() != null && result.getData().size() > 0) {
            for (int i = 0; i < result.getData().size(); i++) {
                items.add(new NewspaperModel(
                        Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageFirstPage(),
                        result.getData().get(i).getName(),
                        result.getData().get(i).getImageInfo().getMediaPath(),
                        result.getData().get(i).getImageInfo().getPageFile(),
                        result.getData().get(i).getGno() + "",
                        result.getData().get(i).getPages().size()

                ));

            }
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(new NewspaperAdapter(this, items, this));

            filteredDateTextView.setText(MyUtils.changeDateFormat(startDate) + " ile " + MyUtils.changeDateFormat(endDate) + " arasında tarihi\nkayıtlar gösterilmektedir.");


        }



    }

    private void newspaperFullPages(String mediaPath, String pageFile, String gno, int position) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<NewsPaperFullPagesResponse> call = apiService.newspaperFullPages(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
                0,
                50,
                true,
                true,
                "National",
                "Newspaper",
                MyUtils.getCurrentDate()
        );

        call.enqueue(new Callback<NewsPaperFullPagesResponse>() {
            @Override
            public void onResponse(Call<NewsPaperFullPagesResponse> call, Response<NewsPaperFullPagesResponse> response) {

                Logger.getInstance().logDebug(TAG, "newspaperFullPages", 2, response.body());

                if (response.isSuccessful()) {

                    NewsPaperFullPagesResponse result = response.body();

                    DataHolder.getInstance().setNewsPaperFullPagesModel(result);

                    int count = 0;
                    String gno2;
                    for (int i = 0; i < result.getData().size(); i++) {
                        gno2 = result.getData().get(i).getGno() + "";
                        if(gno2.equals(gno)) {
                            count = result.getData().get(i).getPages().size();
                        }
                    }


//                    Intent intent = new Intent(NewspaperActivity.this, SubNewspaperActivity.class);
//                    intent.putExtra("mediaPath", mediaPath + "page/" + pageFile + "-");
//                    intent.putExtra("count", count);
//                    intent.putExtra("index", 1);
//                    intent.putExtra("position", position);
//                    startActivity(intent);


                    ArrayList<SelimModel> selimModel = new ArrayList<>();

                    for (int i = 0; i < result.getData().size(); i++) {
                        selimModel.add(new SelimModel(
                                Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageInfo().getMediaPath() + "page/" + result.getData().get(i).getImageInfo().getPageFile() + "-1.jpg?sz=full",
                                result.getData().get(i).getName(),
                                MyUtils.changeDateFormat(result.getData().get(i).getDate())
                                ));
                    }

                    DataHolder.getInstance().setSelimModels(selimModel);

                    Intent intent = new Intent(NewspaperActivity.this, SelimActivity.class);
                    intent.putExtra("mediaPath", mediaPath + "page/" + pageFile + "-");
                    intent.putExtra("count", count);
                    intent.putExtra("index", 1);
                    intent.putExtra("position", position);
                    startActivity(intent);



                } else {

                    if (response.code() == 403) {
                        forbiddenPopup();
                    }

                }
            }

            @Override
            public void onFailure(Call<NewsPaperFullPagesResponse> call, Throwable t) {

                Logger.getInstance().logDebug(TAG, "newspaperFullPages", 3, t.getMessage());
            }
        });

    }

    private void newspaperFirstPages(String mediaPath, String pageFile, String gno, int count, int position) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<NewsPaperFullPagesResponse> call = apiService.newspaperFullPages2(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
                0,
                50,
                true,
                true,
                "National",
                "Magazine",
                MyUtils.getPreviousDate(1),
                MyUtils.getCurrentDate()
        );

        call.enqueue(new Callback<NewsPaperFullPagesResponse>() {
            @Override
            public void onResponse(Call<NewsPaperFullPagesResponse> call, Response<NewsPaperFullPagesResponse> response) {

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 2, response.body());

                if (response.isSuccessful()) {

                    NewsPaperFullPagesResponse result = response.body();

                    DataHolder.getInstance().setNewsPaperFullPagesModel(result);

//                    int count = 10;
//                    String gno2;
//                    for (int i = 0; i < result.getData().size(); i++) {
//                        gno2 = result.getData().get(i).getGno() + "";
//                        if(gno2.equals(gno)) {
//                            count = result.getData().get(i).getPages().size();
//                        }
//                    }

                    Intent intent = new Intent(NewspaperActivity.this, SubNewspaperActivity.class);
                    intent.putExtra("mediaPath", mediaPath + "page/" + pageFile + "-");
                    intent.putExtra("count", count);
                    intent.putExtra("position", position);
                    startActivity(intent);



//                    NewsPaperFullPagesResponse result = response.body();
//
//                    DataHolder.getInstance().setNewsPaperFullPagesModel(result);
//
//                    int count = 0;
//                    String gno2;
//                    for (int i = 0; i < result.getData().size(); i++) {
//                        gno2 = result.getData().get(i).getGno() + "";
//                        if(gno2.equals(gno)) {
//                            count = result.getData().get(i).getPages().size();
//                        }
//                    }
//
//                    Intent intent = new Intent(MagazineActivity.this, SubNewspaperActivity.class);
//                    intent.putExtra("mediaPath", mediaPath + "page/" + pageFile + "-");
//                    intent.putExtra("count", count);
//                    startActivity(intent);

                } else {



                }
            }

            @Override
            public void onFailure(Call<NewsPaperFullPagesResponse> call, Throwable t) {

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 3, t.getMessage());
            }
        });


    }

    private void magazine2(String startDate, String endDate) {


        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<MagazineFullPagesResponse> call = apiService.magazines(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
                0,
                50,
                true,
                true,
                "National",
                "Magazine",
                startDate,
                endDate
        );

        call.enqueue(new Callback<MagazineFullPagesResponse>() {
            @Override
            public void onResponse(Call<MagazineFullPagesResponse> call, Response<MagazineFullPagesResponse> response) {

                Logger.getInstance().logDebug(TAG, "magazines", 2, response.body());

                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                if (response.isSuccessful()) {

                    MagazineFullPagesResponse result = response.body();


                    ArrayList<SelimModel> selimModel = new ArrayList<>();

                    for (int i = 0; i < result.getData().size(); i++) {
                        selimModel.add(new SelimModel(
                                Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageInfo().getMediaPath() + "page/" + result.getData().get(i).getImageInfo().getPageFile() + "-1.jpg?sz=full",
                                result.getData().get(i).getName(),
                                MyUtils.changeDateFormat(result.getData().get(i).getDate())
                        ));
                    }

                    DataHolder.getInstance().setSelimModels(selimModel);


                    DataHolder.getInstance().setMagazineFullPagesModel(result);

//                    Intent intent = new Intent(MainActivity.this, MagazineActivity.class);
////                    intent.putExtra("itemData", itemData.getText());
//                    Bundle options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.left, R.anim.right).toBundle();
//                    startActivity(intent, options);

                    setMagazineData();

                } else {



                }
            }

            @Override
            public void onFailure(Call<MagazineFullPagesResponse> call, Throwable t) {

                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                Logger.getInstance().logDebug(TAG, "magazines", 3, t.getMessage());
            }
        });


    }


    private void forbiddenPopup() {
        Toast.makeText(this, "Forbidden", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(String mediaPath, String pageFile, String gno, int count, int position) {


        Intent intent = new Intent(NewspaperActivity.this, SelimActivity.class);

        intent.putExtra("index", index);
        intent.putExtra("position", position);

        startActivity(intent);

//        switch (index) {
//            case 1:
//
//                Intent intent = new Intent(NewspaperActivity.this, SelimActivity.class);
//
//                intent.putExtra("index", index);
//                intent.putExtra("position", position);
//
//                startActivity(intent);
////                newspaperFullPages(mediaPath, pageFile, gno, position);
//                break;
//            case 2:
////                newspaperFirstPages(mediaPath, pageFile, gno, count, position);
//                break;
//        }

    }
}