package com.example.mtm;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("auth/realms/medyatakip.com/protocol/openid-connect/token")
    Call<TokenModel> getToken(@FieldMap Map<String, String> fields);

    @GET("api/data/agenda/list")
    Call<MediaAgendaModel> getMediaAgenda(
            @Header("Authorization") String authToken,
            @Query("customerId") int customerId,
            @Query("addHash") boolean addHash,
            @Query("addClip") boolean addClip,
            @Query("addContent") boolean addContent,
            @Query("addDocs") boolean addDocs,
            @Query("addTitle") boolean addTitle,
            @Query("date") String date
    );

    @GET("api/data/pm/first-pages")
    Call<NewspaperFirstPagesModel> newspaperFirstPages(
            @Header("Authorization") String authToken,
            @Query("customerId") int customerId,
            @Query("page") int page,
            @Query("perPage") int perPage,
            @Query("date") String date
    );



}
