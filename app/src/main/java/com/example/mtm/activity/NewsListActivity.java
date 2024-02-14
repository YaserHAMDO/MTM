package com.example.mtm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mtm.R;
import com.example.mtm.adapter.InternetAdapter;
import com.example.mtm.adapter.InternetSubListAdapter;
import com.example.mtm.adapter.SubVisualMediaAdapter;
import com.example.mtm.adapter.VisualMediaAdapter;
import com.example.mtm.model.InternetModel;
import com.example.mtm.model.InternetSubListModel;
import com.example.mtm.model.SubVisualMediaModel;
import com.example.mtm.model.VisualMediaModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.InternetResponse;
import com.example.mtm.response.InternetSubResponse;
import com.example.mtm.response.MenuListResponse;
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

public class NewsListActivity extends AppCompatActivity implements InternetSubListAdapter.OnItemClickListener, SubVisualMediaAdapter.OnItemClickListener {

    private static final String TAG = "NewsListActivity";

    private TextView internetTextView, tvTextView, menuListTextView;
    private ImageView backIconImageView;
    private RecyclerView recyclerView;
    private ConstraintLayout menuListConstraintLayout, internetConstraintLayout, tvConstraintLayout;
    private View menuListView, internetView, tvView;

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
        recyclerView = findViewById(R.id.recyclerView);
        menuListConstraintLayout = findViewById(R.id.menuListConstraintLayout);
        internetConstraintLayout = findViewById(R.id.internetConstraintLayout);
        tvConstraintLayout = findViewById(R.id.tvConstraintLayout);
        menuListView = findViewById(R.id.menuListView);
        internetView = findViewById(R.id.internetView);
        tvView = findViewById(R.id.tvView);
    }

    private void setItemClickListeners() {
        internetConstraintLayout.setOnClickListener(view -> setInternetData());
        tvConstraintLayout.setOnClickListener(view -> setVisualData());
        menuListConstraintLayout.setOnClickListener(view -> setMenuList());
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void updateUI(int i) {
        switch (i) {
            case 0:
                menuListTextView.setTextColor(getColor(R.color.black));
                internetTextView.setTextColor(getColor(R.color.color1));
                tvTextView.setTextColor(getColor(R.color.color1));

                menuListView.setVisibility(View.VISIBLE);
                internetView.setVisibility(View.INVISIBLE);
                tvView.setVisibility(View.INVISIBLE);

              break;

            case 1:
                menuListTextView.setTextColor(getColor(R.color.color1));
                internetTextView.setTextColor(getColor(R.color.black));
                tvTextView.setTextColor(getColor(R.color.color1));

                menuListView.setVisibility(View.INVISIBLE);
                internetView.setVisibility(View.VISIBLE);
                tvView.setVisibility(View.INVISIBLE);

              break;

            case 2:
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

                    Intent intent = new Intent(NewsListActivity.this, SubVisualMediaActivity.class);
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

    private void SubInternet(String menuId, String subMenuId) {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<InternetSubResponse> call = apiService.subInternet(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                0,
                10000,
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

                    startActivity(intent);

                } else {



                }
            }

            @Override
            public void onFailure(Call<InternetSubResponse> call, Throwable t) {

                Logger.getInstance().logDebug(TAG, "SubMenuVisualMedia", 3, t.getMessage());
            }
        });


    }



    @Override
    public void onItemClickSubVisualMedia(String menuId, String subMenuId) {
        SubMenuVisualMedia(menuId, subMenuId);
    }

    @Override
    public void onItemClickInternetSubList(String menuId, String subMenuId) {
        SubInternet(menuId, subMenuId);
    }
}