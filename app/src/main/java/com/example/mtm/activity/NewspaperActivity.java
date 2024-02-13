package com.example.mtm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.NewspaperAdapter;
import com.example.mtm.model.NewspaperModel;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewspaperActivity extends AppCompatActivity implements NewspaperAdapter.OnItemClickListener {

    private static final String TAG = "NewspaperActivity";

    private RecyclerView recyclerView;
    private ImageView backIconImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper);

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

        NewspaperFirstPagesResponse result = DataHolder.getInstance().getNewspaperFirstPagesModel();

        List<NewspaperModel> items = new ArrayList<>();


        for (int i = 0; i < result.getData().size(); i++) {
            items.add(new NewspaperModel(
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageFirstPage(),
                    result.getData().get(i).getName(),
                    result.getData().get(i).getImageInfo().getMediaPath(),
                    result.getData().get(i).getImageInfo().getPageFile(),
                    result.getData().get(i).getGno() + ""

            ));

        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new NewspaperAdapter(this, items, this));

    }

    private void newspaperFullPages(String mediaPath, String pageFile, String gno) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<NewsPaperFullPagesResponse> call = apiService.newspaperFullPages(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                22632,
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

                    Intent intent = new Intent(NewspaperActivity.this, SubNewspaperActivity.class);
                    intent.putExtra("mediaPath", mediaPath + "page/" + pageFile + "-");
                    intent.putExtra("count", count);
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

    private void forbiddenPopup() {
        Toast.makeText(this, "Forbidden", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(String mediaPath, String pageFile, String gno) {
        newspaperFullPages(mediaPath, pageFile, gno);
    }
}