package com.example.mtm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.MagazineAdapter;
import com.example.mtm.model.MagazineModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.MagazineFullPagesResponse;
import com.example.mtm.response.NewsPaperFullPagesResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.Logger;
import com.example.mtm.util.MyUtils;
import com.example.mtm.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MagazineActivity extends AppCompatActivity implements MagazineAdapter.OnItemClickListener {

    private static final String TAG = "NewspaperActivity";

    private RecyclerView recyclerView;
    private ImageView backIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);

        init();
        setItemClickListeners();
        setData();
    }

    private void init() {
        backIconImageView = findViewById(R.id.backIconImageView);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void setData() {

        MagazineFullPagesResponse result = DataHolder.getInstance().getMagazineFullPagesModel();

        List<MagazineModel> items = new ArrayList<>();

        for (int i = 0; i < result.getData().size(); i++) {
            items.add(new MagazineModel(
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageFirstPage(),
                    result.getData().get(i).getName(),
                    result.getData().get(i).getImageInfo().getMediaPath(),
                    result.getData().get(i).getImageInfo().getPageFile(),
                    result.getData().get(i).getGno() + "",
                    result.getData().get(i).getPages().size()

            ));
        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new MagazineAdapter(this, items, this));

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

                    Intent intent = new Intent(MagazineActivity.this, SubNewspaperActivity.class);
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



    @Override
    public void onItemClick(String mediaPath, String pageFile, String gno, int count, int position) {
        newspaperFirstPages(mediaPath, pageFile, gno, count, position);
        System.out.println("hasan yaserHamdo  "+ mediaPath + " " + pageFile + " " + gno);
    }
}