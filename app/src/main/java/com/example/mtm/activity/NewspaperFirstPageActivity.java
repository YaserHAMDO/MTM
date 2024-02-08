package com.example.mtm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.CustomAdapter5;
import com.example.mtm.model.ItemData2;
import com.example.mtm.model.Model2;
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

public class NewspaperFirstPageActivity extends AppCompatActivity implements CustomAdapter5.OnItemClickListener {

    private static final String TAG = "NewspaperFirstPageActivity";

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

        NewspaperFirstPagesResponse result = DataHolder.getInstance().getNewspaperFirstPagesModel();

        ArrayList<Model2> model2s = new ArrayList<>();

        List<ItemData2> items;

        items = new ArrayList<>();


        for (int i = 0; i < result.getData().size(); i++) {
//            model2s.add(new Model2( Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageInfo().getMediaPath()));
            items.add(new ItemData2(
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageFirstPage(),
                    result.getData().get(i).getName(),
                    result.getData().get(i).getImageInfo().getMediaPath(),
                    result.getData().get(i).getImageInfo().getPageFile(),
                    result.getData().get(i).getGno() + ""

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
        recyclerView.setAdapter(new CustomAdapter5(this, items, this));

    }


    private void setTypesList3() {

        MagazineFullPagesResponse result = DataHolder.getInstance().getMagazineFullPagesModel();

        ArrayList<Model2> model2s = new ArrayList<>();

        List<ItemData2> items;

        items = new ArrayList<>();


        for (int i = 0; i < result.getData().size(); i++) {
//            model2s.add(new Model2( Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageInfo().getMediaPath()));
            items.add(new ItemData2(
                    Constants.KEY_IMAGE_BASIC_URL + result.getData().get(i).getImageFirstPage(),
                    result.getData().get(i).getName(),
                    result.getData().get(i).getImageInfo().getMediaPath(),
                    result.getData().get(i).getImageInfo().getPageFile(),
                    result.getData().get(i).getGno() + ""

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
        recyclerView.setAdapter(new CustomAdapter5(this, items, this));

    }

    private void newspaperFirstPages(String mediaPath, String pageFile, String gno) {


//        ItemData itemData = items.get(position);
//
//        // Show progress bar for the clicked item
//        itemData.setProgressBarVisible(true);
//
//        // Notify the adapter that data has changed
//        adapter.notifyDataSetChanged();


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

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 2, response.body());

//                // Show progress bar for the clicked item
//                itemData.setProgressBarVisible(false);
//
//                // Notify the adapter that data has changed
//                adapter.notifyDataSetChanged();

                if (response.isSuccessful()) {




                    NewsPaperFullPagesResponse result = response.body();

                    DataHolder.getInstance().setNewsPaperFullPagesModel(result);


//                    String mediaPath = "arc/pm/2024/01/29/0000014847/page/" + "63ff26eb-";

                    int count = 0;
                    String gno2;
                    for (int i = 0; i < result.getData().size(); i++) {
                        gno2 = result.getData().get(i).getGno() + "";
                        if(gno2.equals(gno)) {
                            count = result.getData().get(i).getPages().size();
                        }
                    }



                    Intent intent = new Intent(NewspaperFirstPageActivity.this, NewspaperFullPageActivity.class);
                    intent.putExtra("mediaPath", mediaPath + "page/" + pageFile + "-");
                    intent.putExtra("count", count);
                    startActivity(intent);

                } else {



                }
            }

            @Override
            public void onFailure(Call<NewsPaperFullPagesResponse> call, Throwable t) {
//
//                // Show progress bar for the clicked item
//                itemData.setProgressBarVisible(false);
//
//                // Notify the adapter that data has changed
//                adapter.notifyDataSetChanged();

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 3, t.getMessage());
            }
        });


    }



    @Override
    public void onItemClick(String mediaPath, String pageFile, String gno) {
        newspaperFirstPages(mediaPath, pageFile, gno);
    }
}