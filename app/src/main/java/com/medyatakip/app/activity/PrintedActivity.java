package com.medyatakip.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.medyatakip.app.R;
import com.medyatakip.app.adapter.InternetAdapter;
import com.medyatakip.app.adapter.InternetSubListAdapter;
import com.medyatakip.app.model.InternetModel;
import com.medyatakip.app.model.InternetSubListModel;
import com.medyatakip.app.response.InternetResponse;
import com.medyatakip.app.util.DataHolder;

import java.util.ArrayList;
import java.util.List;

public class PrintedActivity extends AppCompatActivity implements InternetSubListAdapter.OnItemClickListener {

    private ImageView backIconImageView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printed);

        init();
        setItemClickListeners();
        setMenuList();
    }
    private void init() {
        backIconImageView = findViewById(R.id.backIconImageView);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void setMenuList() {
        InternetResponse result = DataHolder.getInstance().getMenuListResponse();

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


    @Override
    public void onItemClickInternetSubList(String menuId, String subMenuId, int count) {

    }
}