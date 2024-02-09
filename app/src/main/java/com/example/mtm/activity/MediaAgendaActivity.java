package com.example.mtm.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.adapter.MediaAgendaTitleAdapter;
import com.example.mtm.adapter.MediaAgendaBodyAdapter;
import com.example.mtm.model.MediaAgendaModel;
import com.example.mtm.network.ApiService;
import com.example.mtm.network.RetrofitClient;
import com.example.mtm.response.MediaAgendaResponse;
import com.example.mtm.util.Constants;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.Logger;
import com.example.mtm.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MediaAgendaActivity extends AppCompatActivity implements MediaAgendaTitleAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private MediaAgendaTitleAdapter adapter;
    private MediaAgendaBodyAdapter adapter2;
    private ImageView backIconImageView;
    private ArrayList<MediaAgendaModel> politicsModels, economyModels, worldModels, cultureModels, lifeModels, sportsModels, isModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_agenda);

        init();
        setItemClickListeners();
        setData();

    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.recyclerView2);
        backIconImageView = findViewById(R.id.backIconImageView);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setItemClickListeners() {
        backIconImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void setData() {

        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        MediaAgendaResponse result = DataHolder.getInstance().getMediaAgendaModel();

        if (result != null) {
            ArrayList<String> userMatch2 = new ArrayList<>();
            politicsModels = new ArrayList<>();
            economyModels = new ArrayList<>();
            worldModels = new ArrayList<>();
            cultureModels = new ArrayList<>();
            lifeModels = new ArrayList<>();
            sportsModels = new ArrayList<>();
            isModels = new ArrayList<>();

            String genderType = "";

            for (int i = 0; i < result.getData().getDocs().size(); i++) {

                String newGenderType = result.getData().getDocs().get(i).getAgendaType().getName();
                if (!newGenderType.equals(genderType)) {
                    userMatch2.add(newGenderType);
                    genderType = newGenderType;
                }
            }

            setTypesList(userMatch2);

            String videoUrl;
            String magazineUrl;

            for (int i = 0; i < result.getData().getDocs().size() ; i++) {

                videoUrl = "";
                magazineUrl = "";
                if (result.getData().getDocs() != null && result.getData().getDocs().get(i).getClips() != null && result.getData().getDocs().get(i).getClips().getBc() != null && result.getData().getDocs().get(i).getClips().getBc().getDocs() != null && result.getData().getDocs().get(i).getClips().getBc().getDocs().size() > 0) {
                    videoUrl =  Constants.KEY_VIDEO_BASIC_URL + result.getData().getDocs().get(i).getClips().getBc().getDocs().get(0).getVideo();
                }
                if (result.getData().getDocs() != null && result.getData().getDocs().get(i).getClips() != null && result.getData().getDocs().get(i).getClips().getPm() != null && result.getData().getDocs().get(i).getClips().getPm().getDocs() != null && result.getData().getDocs().get(i).getClips().getPm().getDocs().size() > 0) {
                    magazineUrl =  Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getClips().getPm().getDocs().get(0).getImageStoragePath();
                }

                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Siyaset")) {

                    politicsModels.add(new MediaAgendaModel(
                            "Siyaset",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            magazineUrl,
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }

                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Ekonomi")) {
                    economyModels.add(new MediaAgendaModel(
                            "Ekonomi",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            magazineUrl,
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }

                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Dünya")) {
                    worldModels.add(new MediaAgendaModel(
                            "Dünya",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            magazineUrl,
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }

                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Kültür-Sanat")) {
                    cultureModels.add(new MediaAgendaModel(
                            "Kültür-Sanat",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            magazineUrl,
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }

                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Yaşam")) {
                    lifeModels.add(new MediaAgendaModel(
                            "Yaşam",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            magazineUrl,
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }

                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Spor")) {
                    sportsModels.add(new MediaAgendaModel(
                            "spor",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            magazineUrl,
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }

                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("İş Dünyası")) {
                    isModels.add(new MediaAgendaModel(
                            "İş Dünyası",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            magazineUrl,
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }
            }

            setTypesList2(politicsModels);
        } else {
            // Handle case where no data is available
        }

    }

    private void setTypesList(List<String> dataList) {
        recyclerView.setAdapter(null);
        adapter = new MediaAgendaTitleAdapter(this, dataList, this);
        recyclerView.setAdapter(adapter);
    }

    private void setTypesList2(List<MediaAgendaModel> dataList) {
        recyclerView2.setAdapter(null);
        adapter2 = new MediaAgendaBodyAdapter(this, dataList);
        recyclerView2.setAdapter(adapter2);
    }

    @Override
    public void onItemClick(String position) {

        recyclerView2.setAdapter(null);

        switch (position) {
            case "Siyaset":
                setTypesList2(politicsModels);
                break;

            case "Ekonomi":
                setTypesList2(economyModels);
                break;

            case "Dünya":
                setTypesList2(worldModels);
                break;

            case "Kültür-Sanat":
                setTypesList2(cultureModels);
                break;

            case "Yaşam":
                setTypesList2(lifeModels);
                break;

            case "Spor":
                setTypesList2(sportsModels);
                break;

            case "İş Dünyası":
                setTypesList2(isModels);
                break;
        }


    }
}