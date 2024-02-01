package com.example.mtm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewspaperFirstPageActivity extends AppCompatActivity implements CustomAdapter4.OnItemClickListener{

    private RecyclerView recyclerView;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper_first_page);

        gridView = findViewById(R.id.gridView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        setTypesList2();
    }

    private void setTypesList2() {

        NewspaperFirstPagesModel result = DataHolder.getInstance().getNewspaperFirstPagesModel();

        ArrayList<Model2> model2s = new ArrayList<>();

        List<ItemData> items;

        items = new ArrayList<>();


        for (int i = 0; i < result.getData().size(); i++) {
//            model2s.add(new Model2( Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageInfo().getMediaPath()));
            items.add(new ItemData(
                    1,
                    1,
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageFirstPage()
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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new CustomAdapter5(this, items));

    }

    @Override
    public void onItemClick(String position) {

    }
}