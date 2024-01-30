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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsActivity extends AppCompatActivity {

//    private LinearLayout matchesLinearLayout;

    private RecyclerView recyclerView;
    private CustomAdapter2 adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



//        matchesLinearLayout = findViewById(R.id.matchesLinearLayout_newChatFragment);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));




//
//        // Create Retrofit instance
//        Retrofit retrofit = RetrofitClient.getClientApi();
//
//        // Assuming you have already created Retrofit instance
//        ApiService apiService = retrofit.create(ApiService.class);
//
//// Call the mediaAgenda method with appropriate parameters
//        Call<MediaAgendaModel> call = apiService.mediaAgenda(
//                "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ6SG9uOXFlcUxHdWVUU0VMYkNDVFRfOXJXaHFmOGNJRHpCd1N0TTZ4R1FRIn0.eyJqdGkiOiI1M2I1NjdiMi00NzcyLTRhZDQtOGZlYi00MWNkZmIxOWUyNmMiLCJleHAiOjE3MDY2MjMwOTgsIm5iZiI6MCwiaWF0IjoxNzA2NjA4Njk4LCJpc3MiOiJodHRwczovL3NlY3VyaXR5Lm1lZHlhdGFraXAuY29tL2F1dGgvcmVhbG1zL21lZHlhdGFraXAuY29tIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImY6ZTEwODNmNGUtYTJlMy00NjVmLTk0NTctMWM2MzFjM2JlODRmOjhhMGM5MTM0LTIzOGUtNDk2Ny1hNjJhLWE4MjE1ZGY1NzMwYSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImFjY291bnQtbW9iaWxlIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiODVmOTdhZTEtYTc0ZS00YWRhLTgxYmUtMmE4NDhiNGMxYWE4IiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJNZXN1dCBBeWfDvG4gLSBNb2JpbGUgVGVzdCIsInByZWZlcnJlZF91c2VybmFtZSI6Im1lc3V0YXlndW4zNSIsImxvY2FsZSI6InRyIiwiZ2l2ZW5fbmFtZSI6Ik1lc3V0IEF5Z8O8biAtIE1vYmlsZSBUZXN0IiwiZW1haWwiOiJtZXN1dGF5Z3VuMzVAaWNsb3VkLmNvbSJ9.Kftm-hLphWGlXzdiiUOX9aw5Itdov7FHx4Zu6DdGoMSCJ2h8vxHSBbvkBvbovDZLyGwp0_1gpSD9GQwRgqeGcoGoCBHUjHCzCVRIW11Tf6XMHpcO03Axuq1w1maGcT12lwdKVVYLbeEt0P3jWFvtXEeTVTLblmqYnoOMebFWPuEgsjZ_ZXBiLYkFcIIAtEbyr_0DMLJwa2qqL9woDkmU757rCyH1lJPeowHvBK6bj6kvQLSdxxg_A8x0IjqSfIAkq1oRIYectq1mXNYlUzVFgGydBGBAu1qeuKm40IB6GiEcHsCGyMGdXfzWxD4_SKc8lYOCEJFoG7fIBbzOi0Sa0A",
//                22632,
//                true, // addHash
//                true, // addClip
//                true, // addContent
//                true, // addDocs
//                true, // addTitle
//                "2024-01-25"
//        );
//
//// Execute the call asynchronously
//        call.enqueue(new Callback<MediaAgendaModel>() {
//            @Override
//            public void onResponse(Call<MediaAgendaModel> call, Response<MediaAgendaModel> response) {
//                if (response.isSuccessful()) {
//                    // Handle successful response
//                    MediaAgendaModel result = response.body();
//
//                    ArrayList<String> userMatch2 = new ArrayList<>();
//
//                    String genderType = "";
//                    for (int i = 0; i < result.getData().getDocs().size(); i++) {
//
//                        String newGenderType = result.getData().getDocs().get(i).getAgendaType().getName();
//                        if (!newGenderType.equals(genderType)) {
//                            userMatch2.add(newGenderType);
//                            genderType = newGenderType;
//                        }
//                    }
//                    setTypesList(userMatch2);
//
//
//                    Toast.makeText(DetailsActivity.this, "isSuccessful", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Handle error response
//
//                    Toast.makeText(DetailsActivity.this, "isSuccessful xxx", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MediaAgendaModel> call, Throwable t) {
//                // Handle network errors
//
//                Toast.makeText(DetailsActivity.this, "isSuccessful zzzzzz", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
//
//    private void updateUserMatches(ArrayList<String> userMatch2) {
//        if (userMatch2 != null && userMatch2.size() > 0) {
//
//
//            for(int i = 0; i < userMatch2.size(); i++) {
//                LayoutInflater layoutInflater = LayoutInflater.from(this);
//                View view = layoutInflater.inflate(R.layout.text2, matchesLinearLayout, false);
//
//                TextView textView = view.findViewById(R.id.greeting_tv1);
//                View view1 = view.findViewById(R.id.viewxx);
//
//                textView.setText(userMatch2.get(i));
//
//
//                int finalJ = i;
//                view.setOnClickListener(v -> {
//
//                    view1.setVisibility(View.VISIBLE);
////                        Intent intent = new Intent(getContext(), NewChatActivity.class);
////
////                        preferenceManager.putString(Constants.KEY_CHAT_OPPOSITE_PROFILE_IMAGE, Constants.NEW_KEY_SITTER_IMAGE_URL +  userMatch2.get(finalJ).getProfile_image());
////                        preferenceManager.putString(Constants.KEY_CHAT_OPPOSITE_USER_NAME, userMatch2.get(finalJ).getFullname());
////                        preferenceManager.putString(Constants.KEY_CHAT_OPPOSITE_FCM, userMatch2.get(finalJ).getFcm());
////                        preferenceManager.putString(Constants.KEY_CHAT_OPPOSITE_FUID, userMatch2.get(finalJ).getFuid());
////                        preferenceManager.putInt(Constants.KEY_CHAT_OPPOSITE_SUPER_LIKE, userMatch2.get(finalJ).getSuper_like());
////                        preferenceManager.putString(Constants.KEY_CHAT_USER_GUID, userMatch2.get(finalJ).getUser_guid());
////                        preferenceManager.putString(Constants.KEY_CHAT_MATCH_GUID, userMatch2.get(finalJ).getUser_match_guid());
////                        preferenceManager.putString(Constants.KEY_CHAT_OPPOSITE_GUID, userMatch2.get(finalJ).getUser_guid());
////                        preferenceManager.putString(Constants.KEY_CHAT_OWN_GUID, userMatch2.get(finalJ).getUser_own_guid());
////
////
////                        preferenceManager.putInt(Constants.KEY_PREVIEW_POSITION_TEMPORARY,finalJ);
////
////                        preferenceManager.putString(Constants.KEY_CAME_FROM,"chatFragment");
//
////                        startActivity(intent);
//
//
//
//                });
//
//
//                matchesLinearLayout.addView(view);
//
//            }
//        }
//
//    }

    private void setTypesList(List<String> dataList) {
        recyclerView.setAdapter(null);

        adapter = new CustomAdapter2(this, dataList);
        recyclerView.setAdapter(adapter);
    }

}