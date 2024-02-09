package com.example.mtm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.ColumnistAdapter;
import com.example.mtm.model.ColumnistModel;
import com.example.mtm.response.ColumnistsResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;

import java.util.ArrayList;
import java.util.List;

public class ColumnistsActivity extends AppCompatActivity implements ColumnistAdapter.OnItemClickListener {

    private ImageView backIconImageView;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_columnists);

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

        ColumnistsResponse result = DataHolder.getInstance().getColumnistsModel();

        List<ColumnistModel> items = new ArrayList<>();

        for (int i = 0; i < result.getData().getDocs().size(); i++) {

            items.add(new ColumnistModel(
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getJournalist().getImagePath(),
                    result.getData().getDocs().get(i).getJournalist().getName(),
                    result.getData().getDocs().get(i).getMedia().getName(),
                    result.getData().getDocs().get(i).getTitle(),
                    result.getData().getDocs().get(i).getPublishDate(),
                    Constants.KEY_IMAGE_BASIC_URL +  result.getData().getDocs().get(i).getImageStoragePath()
            ));

        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new ColumnistAdapter(this, items, this));

    }

    @Override
    public void onItemClick(String mediaPath) {
        Intent intent = new Intent(this, ImageShowActivity.class);
        intent.putExtra("imageUrl", mediaPath);
        startActivity(intent);
    }
}