package com.example.mtm.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.SubVisualAdapter;
import com.example.mtm.model.SubInternetModel;
import com.example.mtm.response.SubMenuVisualMediaResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;

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

//        ArrayList<Model2> model2s = new ArrayList<>();

        List<SubInternetModel> items;

        items = new ArrayList<>();



        for (int i = 0; i < result.getData().getDocs().size(); i++) {

            SubInternetModel f = new SubInternetModel("","","","","","");
            items.add(new SubInternetModel(
                    result.getData().getDocs().get(i).getTitle(),
                    result.getData().getDocs().get(i).getMedia().getName(),
                    "",
                    result.getData().getDocs().get(i).getPublishDate(),
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getMedia().getLogo(),
                    Constants.KEY_VIDEO_BASIC_URL + result.getData().getDocs().get(i).getVideo()
            ));

        }

//        recyclerView.setAdapter(null);
//        CustomAdapter4 adapter = new CustomAdapter4(this, model2s, this);
//        recyclerView.setAdapter(adapter);



//        items.add(new MainActivityModel(R.drawable.news_icon, R.drawable.test8, "Haber Listesi"));
//        items.add(new MainActivityModel(R.drawable.media2_icon, R.drawable.test9, "Medya Gündemi"));
//
//        items.add(new MainActivityModel(R.drawable.media_icon, R.drawable.test10, "Yazılı"));
//        items.add(new MainActivityModel(R.drawable.internet_icon, R.drawable.test11, "İnternet"));
//        items.add(new MainActivityModel(R.drawable.visual_and_auditory_icon, R.drawable.test12, "Görsel & İşitsel"));
//
//        items.add(new MainActivityModel(R.drawable.newspapers_icon, R.drawable.test13, "Gazeteler"));
//        items.add(new MainActivityModel(R.drawable.magazines_icon, R.drawable.test14, "Dergiler"));
//        items.add(new MainActivityModel(R.drawable.opinion_writers_icon, R.drawable.test15, "Köşe Yazarları"));
//

//        CustomAdapte5 adapter;
//
//        adapter = new CustomAdapte5(this, items);
//        gridView.setAdapter(adapter);



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