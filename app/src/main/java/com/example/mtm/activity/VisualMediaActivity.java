package com.example.mtm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.VisualMediaAdapter;
import com.example.mtm.adapter.SubVisualMediaAdapter;
import com.example.mtm.model.SubVisualMediaModel;
import com.example.mtm.model.VisualMediaModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.SubMenuVisualMediaResponse;
import com.example.mtm.response.VisualMediaResponse;
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

public class VisualMediaActivity extends AppCompatActivity implements SubVisualMediaAdapter.OnItemClickListener {

    private static final String TAG = "VisualMediaActivity";

    private ImageView backIconImageView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_media);

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

        VisualMediaResponse result = DataHolder.getInstance().getVisualMediaModel();

        List<VisualMediaModel> items = new ArrayList<>();
        List<SubVisualMediaModel> items2;


        for (int i = 0; i < result.getData().getMenu().getMenu().size(); i++) {
            items2 = new ArrayList<>();

            for (int j = 0; j < result.getData().getMenu().getMenu().get(i).getSubMenus().size(); j++) {

                items2.add(new SubVisualMediaModel(
                        result.getData().getMenu().getMenu().get(i).getSubMenus().get(j).getName(),
                        result.getData().getMenu().getMenu().get(i).getSubMenus().get(j).getNewsCount(),
                        result.getData().getMenu().getMenu().get(i).getId() + "",
                        result.getData().getMenu().getMenu().get(i).getSubMenus().get(j).getId() + ""
                ));

            }

            items.add(new VisualMediaModel(
                    result.getData().getMenu().getMenu().get(i).getName(),
                    result.getData().getMenu().getMenu().get(i).getNewsCount(),
                    items2
            ));

        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new VisualMediaAdapter(this, items, this));

    }

    private void SubMenuVisualMedia(String menuId, String subMenuId) {


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<SubMenuVisualMediaResponse> call = apiService.subMenuVisualMedia(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                50,
                22632,
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

                    Intent intent = new Intent(VisualMediaActivity.this, SubVisualMediaActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);

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
    public void onItemClick(String menuId, String subMenuId) {
        SubMenuVisualMedia(menuId, subMenuId);
    }
}