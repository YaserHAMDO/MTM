package com.example.mtm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsActivity extends AppCompatActivity implements CustomAdapter2.OnItemClickListener  {

    private static final String TAG = "DetailsActivity";

//    private LinearLayout matchesLinearLayout;

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private CustomAdapter2 adapter;
    private CustomAdapter3 adapter2;

    ArrayList<Models> siyasetModels;
    ArrayList<Models> ekonomiModels;
    ArrayList<Models> dunyaModels;
    ArrayList<Models> kulturModels;
    ArrayList<Models> yasamModels;
    ArrayList<Models> sporModels;
    ArrayList<Models> isModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.recyclerView2);

        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

//        getMediaAgenda();


        MediaAgendaModel result = DataHolder.getInstance().getMediaAgendaModel();

        if (result != null) {
            ArrayList<String> userMatch2 = new ArrayList<>();
            siyasetModels = new ArrayList<>();
            ekonomiModels = new ArrayList<>();
            dunyaModels = new ArrayList<>();
            kulturModels = new ArrayList<>();
            yasamModels = new ArrayList<>();
            sporModels = new ArrayList<>();
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

            for (int i = 0; i < result.getData().getDocs().size() ; i++) {

                videoUrl = "";
                if (result.getData().getDocs() != null && result.getData().getDocs().get(i).getClips() != null && result.getData().getDocs().get(i).getClips().getBc() != null && result.getData().getDocs().get(i).getClips().getBc().getDocs() != null && result.getData().getDocs().get(i).getClips().getBc().getDocs().size() > 0) {
                    videoUrl =  Constants.KEY_VIDEO_BASIC_URL + result.getData().getDocs().get(i).getClips().getBc().getDocs().get(0).getVideo();
                }
                
                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Siyaset")) {
                    
                    siyasetModels.add(new Models(
                            "Siyaset",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }

                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Ekonomi")) {
                    ekonomiModels.add(new Models(
                            "Ekonomi",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }

                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Dünya")) {
                    dunyaModels.add(new Models(
                            "Dünya",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }

                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Kültür-Sanat")) {
                    kulturModels.add(new Models(
                            "Kültür-Sanat",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }

                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Yaşam")) {
                    yasamModels.add(new Models(
                            "Yaşam",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }

                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Spor")) {
                    sporModels.add(new Models(
                            "spor",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }

                if (result.getData().getDocs().get(i).getAgendaType().getName().equals("İş Dünyası")) {
                    isModels.add(new Models(
                            "İş Dünyası",
                            Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                            videoUrl,
                            result.getData().getDocs().get(i).getAgendaType().getName(),
                            result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                    ));
                }

            }




            setTypesList2(siyasetModels);
        } else {
            // Handle case where no data is available
        }


    }

    private void getMediaAgenda() {

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        ApiService apiService = RetrofitClient.getClient(2).create(ApiService.class);

        Call<MediaAgendaModel> call = apiService.getMediaAgenda(
                "Bearer " + preferenceManager.getString(Constants.KEY_ACCESS_TOKEN),
                22632,
                true,
                true,
                true,
                true,
                true,
                "2024-01-25"
        );

        call.enqueue(new Callback<MediaAgendaModel>() {
            @Override
            public void onResponse(Call<MediaAgendaModel> call, Response<MediaAgendaModel> response) {

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 2, response.body());

                if (response.isSuccessful()) {


                    MediaAgendaModel result = response.body();

                    ArrayList<String> userMatch2 = new ArrayList<>();
                    siyasetModels = new ArrayList<>();
                    ekonomiModels = new ArrayList<>();
                    dunyaModels = new ArrayList<>();
                    kulturModels = new ArrayList<>();
                    yasamModels = new ArrayList<>();
                    sporModels = new ArrayList<>();
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







                    for (int i = 0; i < result.getData().getDocs().size() ; i++) {

                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Siyaset")) {
                            siyasetModels.add(new Models(
                                    "Siyaset",
                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                    "videoUrl",
                                    result.getData().getDocs().get(i).getAgendaType().getName(),
                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                            ));
                        }

                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Ekonomi")) {
                            ekonomiModels.add(new Models(
                                    "Ekonomi",
                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                    "videoUrl",
                                    result.getData().getDocs().get(i).getAgendaType().getName(),
                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                            ));
                        }

                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Dünya")) {
                            dunyaModels.add(new Models(
                                    "Dünya",
                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                    "videoUrl",
                                    result.getData().getDocs().get(i).getAgendaType().getName(),
                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                            ));
                        }

                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Kültür-Sanat")) {
                            kulturModels.add(new Models(
                                    "Kültür-Sanat",
                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                    "rl",
                                    result.getData().getDocs().get(i).getAgendaType().getName(),
                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                            ));
                        }

                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Yaşam")) {
                            yasamModels.add(new Models(
                                    "Yaşam",
                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                    "videoUrl",
                                    result.getData().getDocs().get(i).getAgendaType().getName(),
                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                            ));
                        }

                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("Spor")) {
                            sporModels.add(new Models(
                                    "Spor",
                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                    "videoUrl",
                                    result.getData().getDocs().get(i).getAgendaType().getName(),
                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                            ));
                        }

                        if (result.getData().getDocs().get(i).getAgendaType().getName().equals("İş Dünyası")) {
                            isModels.add(new Models(
                                    "İş Dünyası",
                                    Constants.KEY_IMAGE_BASIC_URL + result.getData().getDocs().get(i).getImageUrl(),
                                    "videoUrl",
                                    result.getData().getDocs().get(i).getAgendaType().getName(),
                                    result.getData().getDocs().get(i).getContents().getTr_TR().getTitle()
                            ));
                        }

                    }




                    setTypesList2(siyasetModels);

                } else {



                }
            }

            @Override
            public void onFailure(Call<MediaAgendaModel> call, Throwable t) {

                Logger.getInstance().logDebug(TAG, "mediaAgenda", 3, t.getMessage());
            }
        });


    }

    private void setTypesList(List<String> dataList) {
        recyclerView.setAdapter(null);
        adapter = new CustomAdapter2(this, dataList, this);
        recyclerView.setAdapter(adapter);
    }

    private void setTypesList2(List<Models> dataList) {
        recyclerView2.setAdapter(null);
        adapter2 = new CustomAdapter3(this, dataList);
        recyclerView2.setAdapter(adapter2);
    }

    @Override
    public void onItemClick(String position) {

        recyclerView2.setAdapter(null);

        switch (position) {
            case "Siyaset":
                setTypesList2(siyasetModels);
                break;

            case "Ekonomi":
                setTypesList2(ekonomiModels);
                break;

            case "Dünya":
                setTypesList2(dunyaModels);
                break;

            case "Kültür-Sanat":
                setTypesList2(kulturModels);
                break;

            case "Yaşam":
                setTypesList2(yasamModels);
                break;

            case "Spor":
                setTypesList2(sporModels);
                break;

            case "İş Dünyası":
                setTypesList2(isModels);
                break;
        }


    }
}