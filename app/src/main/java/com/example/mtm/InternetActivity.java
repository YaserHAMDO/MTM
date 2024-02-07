package com.example.mtm;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InternetActivity extends AppCompatActivity implements CustomAdapter8.OnItemClickListener{

    private static final String TAG = "InternetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        setTypesList2();
    }
    private void setTypesList2() {

        InternetModel result = DataHolder.getInstance().getInternetModel();

//        ArrayList<Model2> model2s = new ArrayList<>();

        List<ItemData5> items;
        List<ItemData4> items4;


        items = new ArrayList<>();



        for (int i = 0; i < result.getData().getMenu().getMenu().size(); i++) {
            items4 = new ArrayList<>();

            for (int j = 0; j < result.getData().getMenu().getMenu().get(i).getSubMenus().size(); j++) {

                items4.add(new ItemData4(
                        result.getData().getMenu().getMenu().get(i).getSubMenus().get(j).getName(),
                        result.getData().getMenu().getMenu().get(i).getSubMenus().get(j).getDocCount(),
                        result.getData().getMenu().getMenu().get(i).getId() + "",
                        result.getData().getMenu().getMenu().get(i).getSubMenus().get(j).getId() + ""
                ));

            }


            items.add(new ItemData5(
                    result.getData().getMenu().getMenu().get(i).getName(),
                    result.getData().getMenu().getMenu().get(i).getDocCount(),
                    items4
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
        recyclerView.setAdapter(new CustomAdapter7(this, items, this));

    }

    private void SubInternet(String menuId, String subMenuId) {



//        ItemData itemData = items.get(position);
//
//        // Show progress bar for the clicked item
//        itemData.setProgressBarVisible(true);
//
//        // Notify the adapter that data has changed
//        adapter.notifyDataSetChanged();


        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<InternetSubModel> call = apiService.subInternet(
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
        call.enqueue(new Callback<InternetSubModel>() {
            @Override
            public void onResponse(Call<InternetSubModel> call, Response<InternetSubModel> response) {

                Logger.getInstance().logDebug(TAG, "SubMenuVisualMedia", 2, response.body());

//                // Show progress bar for the clicked item
//                itemData.setProgressBarVisible(false);
//
//                // Notify the adapter that data has changed
//                adapter.notifyDataSetChanged();

                if (response.isSuccessful()) {



                    InternetSubModel result = response.body();

                    DataHolder.getInstance().setInternetSubModel(result);

                    Intent intent = new Intent(InternetActivity.this, SubInternetActivity.class);
//                    intent.putExtra("itemData", itemData.getText());
                    startActivity(intent);

                } else {



                }
            }

            @Override
            public void onFailure(Call<InternetSubModel> call, Throwable t) {

//                // Show progress bar for the clicked item
//                itemData.setProgressBarVisible(false);
//
//                // Notify the adapter that data has changed
//                adapter.notifyDataSetChanged();

                Logger.getInstance().logDebug(TAG, "SubMenuVisualMedia", 3, t.getMessage());
            }
        });


    }



//    @Override
//    public void onItemClick(String mediaPath) {
//
//
//
//    }

    @Override
    public void onItemClick(String menuId, String subMenuId) {
        SubInternet(menuId, subMenuId);

//        Intent intent = new Intent(this, ImageShowActivity.class);
//        intent.putExtra("imageUrl", mediaPath);
//        startActivity(intent);
    }
}