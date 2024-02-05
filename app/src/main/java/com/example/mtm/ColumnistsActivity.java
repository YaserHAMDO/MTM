package com.example.mtm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ColumnistsActivity extends AppCompatActivity implements CustomAdapter6.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_columnists);

        setTypesList2();
    }
    private void setTypesList2() {

        ColumnistsModel result = DataHolder.getInstance().getColumnistsModel();

//        ArrayList<Model2> model2s = new ArrayList<>();

        List<ItemData3> items;

        items = new ArrayList<>();


        for (int i = 0; i < result.getData().getDocs().size(); i++) {
//            model2s.add(new Model2( Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageInfo().getMediaPath()));
            items.add(new ItemData3(
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getJournalist().getImagePath(),
                    result.getData().getDocs().get(i).getJournalist().getName(),
                    result.getData().getDocs().get(i).getMedia().getName(),
                    result.getData().getDocs().get(i).getTitle(),
                    result.getData().getDocs().get(i).getPublishDate(),
                    Constants.KEY_IMAGE_BASIC_URL +  result.getData().getDocs().get(i).getImageStoragePath()

            ));

        }

//        recyclerView.setAdapter(null);
//        CustomAdapter4 adapter = new CustomAdapter4(this, model2s, this);
//        recyclerView.setAdapter(adapter);



//        items.add(new ItemData(R.drawable.news_icon, R.drawable.test8, "Haber Listesi"));
//        items.add(new ItemData(R.drawable.media2_icon, R.drawable.test9, "Medya Gündemi"));
//
//        items.add(new ItemData(R.drawable.media_icon, R.drawable.test10, "Yazılı"));
//        items.add(new ItemData(R.drawable.internet_icon, R.drawable.test11, "İnternet"));
//        items.add(new ItemData(R.drawable.visual_and_auditory_icon, R.drawable.test12, "Görsel & İşitsel"));
//
//        items.add(new ItemData(R.drawable.newspapers_icon, R.drawable.test13, "Gazeteler"));
//        items.add(new ItemData(R.drawable.magazines_icon, R.drawable.test14, "Dergiler"));
//        items.add(new ItemData(R.drawable.opinion_writers_icon, R.drawable.test15, "Köşe Yazarları"));
//

//        CustomAdapte5 adapter;
//
//        adapter = new CustomAdapte5(this, items);
//        gridView.setAdapter(adapter);



        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new CustomAdapter6(this, items, this));

    }

    @Override
    public void onItemClick(String mediaPath) {

        Intent intent = new Intent(this, ImageShowActivity.class);
        intent.putExtra("imageUrl", mediaPath);
        startActivity(intent);

    }
}