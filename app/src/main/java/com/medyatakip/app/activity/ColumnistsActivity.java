package com.medyatakip.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medyatakip.app.R;
import com.medyatakip.app.adapter.ColumnistAdapter;
import com.medyatakip.app.model.ColumnistModel;
import com.medyatakip.app.model.VerticalModel;
import com.medyatakip.app.network.ApiService;
import com.medyatakip.app.network.RetrofitClient;
import com.medyatakip.app.response.ColumnistsResponse;
import com.medyatakip.app.util.Constants;
import com.medyatakip.app.util.DataHolder;
import com.medyatakip.app.util.Logger;
import com.medyatakip.app.util.MyUtils;
import com.medyatakip.app.util.PreferenceManager;
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

public class ColumnistsActivity extends AppCompatActivity implements ColumnistAdapter.OnItemClickListener {

    private static final String TAG = "InternetActivity";

    private ArrayList<VerticalModel> verticalModels;
    PreferenceManager preferenceManager;
    private ImageView backIconImageView, filterImageView;
    private RecyclerView recyclerView;
    private MaterialDatePicker materialDatePicker;
    private TextView filteredDateTextView;
    private ProgressBar progressBar;
    private String startDate, endDate;

    private TextView dateTextView, allTextView, newsTextView, adsTextView, okTextView;

    private LinearLayout dateLinearLayout;

    private boolean materialDatePickerControl;

    private BottomSheetDialog bottomSheetDialog;


    private boolean called;

    private ArrayList<String> columnistsShowArray;
    private ArrayList<String> printedMediaShareLinkArray;
    private ArrayList<String> printedMediaFullPageShowArray;
    private ArrayList<String> printedMediaSubPageShowArray;
    private ArrayList<String> printedMediaDateShowArray;
    private ArrayList<String> printedMediaNamesShowArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_columnists);

        init();
        initValues();
        confDate();
        setItemClickListeners();
        setData();
    }

    private void init() {
        preferenceManager = new PreferenceManager(getApplicationContext());

        backIconImageView = findViewById(R.id.backIconImageView);
        recyclerView = findViewById(R.id.recyclerView);
        filterImageView = findViewById(R.id.filterImageView);
        filteredDateTextView = findViewById(R.id.filteredDateTextView);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
//        filterImageView.setOnClickListener(view -> materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER"));

        filterImageView.setOnClickListener(view -> initBottomSheet());

    }

    private void initValues() {
        startDate = MyUtils.getPreviousDate(1);
        endDate = MyUtils.getCurrentDate();
        called = false;
        columnistsShowArray = new ArrayList<>();
        verticalModels = new ArrayList<>();

        printedMediaShareLinkArray = new ArrayList<>();
        printedMediaFullPageShowArray = new ArrayList<>();
        printedMediaSubPageShowArray = new ArrayList<>();
        printedMediaDateShowArray = new ArrayList<>();
        printedMediaNamesShowArray = new ArrayList<>();


        materialDatePickerControl = false;

//        filteredDateTextView.setText(startDate + " ile " + endDate + " arasında tarihi kayıtlar gösterilmektedir.");
    }

    private void initBottomSheet() {

        bottomSheetDialog = new BottomSheetDialog(this ,R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.filter_pop_up_layout);


       LinearLayout linearLayout = bottomSheetDialog.findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.GONE);

        dateTextView = bottomSheetDialog.findViewById(R.id.dateTextView);
        dateLinearLayout = bottomSheetDialog.findViewById(R.id.dateLinearLayout);
        allTextView = bottomSheetDialog.findViewById(R.id.allTextView);
        newsTextView = bottomSheetDialog.findViewById(R.id.newsTextView);
        adsTextView = bottomSheetDialog.findViewById(R.id.adsTextView);
        okTextView = bottomSheetDialog.findViewById(R.id.okTextView);


//        allTextView.setVisibility(View.GONE);
//        newsTextView.setVisibility(View.GONE);
//        adsTextView.setVisibility(View.GONE);

        dateLinearLayout.setOnClickListener(v -> {

            if (!materialDatePickerControl) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                materialDatePickerControl = true;
            }

//            bottomSheetDialog.dismiss();
        });

        if (startDate != null && endDate != null) {
            if (called) {
                dateTextView.setText("Tarih: " + MyUtils.changeDateFormat(startDate) + " ile " + MyUtils.changeDateFormat(startDate)  + " arasında.");
            }
            else {
                dateTextView.setText(MyUtils.changeDateFormat(endDate));

            }

        }

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

            columnists2(startDate, endDate);

            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void confDate() {
        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDateBuilder.setTitleText("Tarih Seç");
        // Set the maximum date range to today's date

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
    }


    private void setData() {

        columnistsShowArray.clear();

        printedMediaShareLinkArray.clear();
        printedMediaFullPageShowArray.clear();
        printedMediaSubPageShowArray.clear();
        printedMediaDateShowArray.clear();
        printedMediaNamesShowArray.clear();
        verticalModels.clear();


        ColumnistsResponse result = DataHolder.getInstance().getColumnistsModel();

        List<ColumnistModel> items = new ArrayList<>();

        for (int i = 0; i < result.getData().getDocs().size(); i++) {

            items.add(new ColumnistModel(
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getJournalist().getImagePath(),
                    result.getData().getDocs().get(i).getJournalist().getName(),
                    result.getData().getDocs().get(i).getMedia().getName(),
                    result.getData().getDocs().get(i).getTitle(),
                    MyUtils.changeDateFormat(result.getData().getDocs().get(i).getPublishDate()),
                    Constants.KEY_IMAGE_BASIC_URL +  result.getData().getDocs().get(i).getImageStoragePath(),
                    Constants.KEY_SHARE_URL + result.getData().getDocs().get(i).getGnoHash()
            ));


            ArrayList<String> clipImages = new ArrayList<>();
            ArrayList<String> fullImages = new ArrayList<>();


            if (result.getData().getDocs().get(i).getContinuesClip() != null && result.getData().getDocs().get(i).getContinuesClip().size() > 1) {

                for (int j = 0; j < result.getData().getDocs().get(i).getContinuesClip().size(); j++) {

                    clipImages.add(
                            "https://imgsrv.medyatakip.com/store/clip?gno=" +  result.getData().getDocs().get(i).getContinuesClip().get(j).getGno() + "&ds=" + preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_PM));

                    fullImages.add(
                            "https://imgsrv.medyatakip.com/store/clip?gno=" +  result.getData().getDocs().get(i).getContinuesClip().get(j).getGno() + "&ds=" + preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_PM));

//                    fullImages.add("https://imgsrv.medyatakip.com/store/" + result.getData().getDocs().get(i).getImageInfo().getMediaPath() + "page/" + result.getData().getDocs().get(i).getImageInfo().getPageFile() + "-" + result.getData().getDocs().get(i).getContinuesClip().get(j).getPn() + ".jpg");
                }
            }
            else {
//                    clipImages.add(Constants.KEY_IMAGE_BASIC_URL +  result.getData().getDocs().get(i).getImageStoragePath() );
                clipImages.add("https://imgsrv.medyatakip.com/store/clip?gno=" +  result.getData().getDocs().get(i).getGno() + "&ds=" + preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_PM));
                fullImages.add("https://imgsrv.medyatakip.com/store/clip?gno=" +  result.getData().getDocs().get(i).getGno() + "&ds=" + preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_PM));
//                fullImages.add(Constants.KEY_IMAGE_BASIC_URL +
//                        result.getData().getDocs().get(i).getImageInfo().getMediaPath() +
//                        "page/" + result.getData().getDocs().get(i).getImageInfo().getPageFile() +
//                        "-" + result.getData().getDocs().get(i).getContinuesClip().get(0).getPn() +
//                        ".jpg");

            }

            verticalModels.add(new VerticalModel(fullImages, clipImages));




            columnistsShowArray.add(Constants.KEY_IMAGE_BASIC_URL +  result.getData().getDocs().get(i).getImageStoragePath());

            printedMediaShareLinkArray.add(Constants.KEY_SHARE_URL + result.getData().getDocs().get(i).getGnoHash());
            printedMediaFullPageShowArray.add("");
            printedMediaSubPageShowArray.add(Constants.KEY_IMAGE_BASIC_URL +  result.getData().getDocs().get(i).getImageStoragePath());
            printedMediaDateShowArray.add(MyUtils.changeDateFormat(result.getData().getDocs().get(i).getPublishDate()));
            printedMediaNamesShowArray.add(result.getData().getDocs().get(i).getMedia().getName());

        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new ColumnistAdapter(this, items, this));

        if (called) {
            filteredDateTextView.setText(MyUtils.changeDateFormat(startDate) + " ile " + MyUtils.changeDateFormat(endDate) + " arasında tarihi\nkayıtlar gösterilmektedir.");
        }
        else {
            filteredDateTextView.setText(MyUtils.changeDateFormat(endDate) + "  tarihi kayıtlar gösterilmektedir.");

        }

    }

    private void columnists2(String startDate, String endDate) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<ColumnistsResponse> call = apiService.columnists(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                50,
                preferenceManager.getInt(Constants.KEY_CURRENT_COSTUMER_ID),
                true,
                startDate,
                endDate,
                "UNIGNORED",
                true,
                false,
                true
        );


        call.enqueue(new Callback<ColumnistsResponse>() {
            @Override
            public void onResponse(Call<ColumnistsResponse> call, Response<ColumnistsResponse> response) {

                Logger.getInstance().logDebug(TAG, "columnists", 2, response.body());

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {


                    called = true;

                    ColumnistsResponse result = response.body();

                    DataHolder.getInstance().setColumnistsModel(result);


                    setData();


                } else {

                    if (response.code() == 403) {
//                        forbiddenPopup();
                    }

                    else {
//                        refreshToken2(8);
                    }

                }
            }

            @Override
            public void onFailure(Call<ColumnistsResponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

                Logger.getInstance().logDebug(TAG, "columnists", 3, t.getMessage());
            }
        });


    }


    @Override
    public void onItemClick(int index) {
//        Intent intent = new Intent(this, ImageShowActivity.class);
//        intent.putExtra("imageUrl", mediaPath);
//        startActivity(intent);


//        DataHolder.getInstance().setColumnistsShowArray(columnistsShowArray);
//
//        Intent intent = new Intent(this, ColumnistsShowActivity.class);
//        intent.putExtra("index", index);
//        startActivity(intent);



//        DataHolder.getInstance().setPrintedMediaShareLinkArray(printedMediaShareLinkArray);
//        DataHolder.getInstance().setPrintedMediaFullPageShowArray(printedMediaFullPageShowArray);
//        DataHolder.getInstance().setPrintedMediaSubPageShowArray(printedMediaSubPageShowArray);
//        DataHolder.getInstance().setPrintedMediaDateShowArray(printedMediaDateShowArray);
//        DataHolder.getInstance().setPrintedMediaNamesShowArray(printedMediaNamesShowArray);
//
//        DataHolder.getInstance().setColumnistsShowArray(columnistsShowArray);
//
//        Intent intent = new Intent(this, MeActivity.class);
//        intent.putExtra("index", index);
//        startActivity(intent);



        DataHolder.getInstance().setPrintedMediaShareLinkArray(printedMediaShareLinkArray);
        DataHolder.getInstance().setVerticalModels(verticalModels);
        DataHolder.getInstance().setPrintedMediaFullPageShowArray(printedMediaFullPageShowArray);
        DataHolder.getInstance().setPrintedMediaSubPageShowArray(printedMediaSubPageShowArray);
        DataHolder.getInstance().setPrintedMediaDateShowArray(printedMediaDateShowArray);
        DataHolder.getInstance().setPrintedMediaNamesShowArray(printedMediaNamesShowArray);

        Intent intent = new Intent(this, Me3Activity.class);


        intent.putExtra("index", index);
        startActivity(intent);



    }
}