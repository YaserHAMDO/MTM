package com.example.mtm.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.SubInternetAdapter;
import com.example.mtm.model.SubInternetModel;
import com.example.mtm.response.InternetSubResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;

import java.util.ArrayList;
import java.util.List;

public class SubInternetActivity extends AppCompatActivity implements SubInternetAdapter.OnItemClickListener {

    private ImageView backIconImageView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_internet);

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

        InternetSubResponse result = DataHolder.getInstance().getInternetSubModel();

        List<SubInternetModel> items;

        items = new ArrayList<>();

        for (int i = 0; i < result.getData().getDocs().size(); i++) {

            items.add(new SubInternetModel(
                    result.getData().getDocs().get(i).getTitle(),
                    result.getData().getDocs().get(i).getMedia().getType().getName(),
                    "",
                    result.getData().getDocs().get(i).getPublishDate(),
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageStoragePath(),
                    result.getData().getDocs().get(i).getUrl()
            ));

        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new SubInternetAdapter(this, items, this));
    }

    @Override
    public void onItemClick(String mediaPath) {
//        Intent intent = new Intent(this, VideoActivity.class);
//        intent.putExtra("videoUrl", mediaPath);
//        startActivity(intent);
    }
}