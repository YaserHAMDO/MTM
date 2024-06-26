package com.medyatakip.app.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medyatakip.app.R;
import com.medyatakip.app.adapter.SubVisualAdapter;
import com.medyatakip.app.model.SubInternetModel;
import com.medyatakip.app.response.SubMenuVisualMediaResponse;
import com.medyatakip.app.util.Constants;
import com.medyatakip.app.util.DataHolder;

import java.util.ArrayList;
import java.util.List;

public class SubVisualMediaActivity extends AppCompatActivity implements SubVisualAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_media_details_activity);

        setTypesList2();

    }

    private void setTypesList2() {

        SubMenuVisualMediaResponse result = DataHolder.getInstance().getSubMenuVisualMediaModel();

        List<SubInternetModel> items;

        items = new ArrayList<>();

        for (int i = 0; i < result.getData().getDocs().size(); i++) {

            items.add(new SubInternetModel(
                    result.getData().getDocs().get(i).getTitle(),
                    result.getData().getDocs().get(i).getMedia().getName(),
                    "",
                    result.getData().getDocs().get(i).getPublishDate(),
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getMedia().getLogo(),
                    Constants.KEY_VIDEO_BASIC_URL + result.getData().getDocs().get(i).getVideo(),
                    "",
                    ""
            ));

        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new SubVisualAdapter(this, items, this));

    }

    @Override
    public void onItemClick(String mediaPath) {
        Intent intent = new Intent(this, VideoShowActivity.class);
        intent.putExtra("videoUrl", mediaPath);
        startActivity(intent);
    }
}