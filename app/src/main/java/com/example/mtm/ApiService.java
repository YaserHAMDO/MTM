package com.example.mtm;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api/data/agenda/list")
    Call<MediaAgendaModel> mediaAgenda(
            @Header("Authorization") String authToken,
            @Query("customerId") int customerId,
            @Query("addHash") boolean addHash,
            @Query("addClip") boolean addClip,
            @Query("addContent") boolean addContent,
            @Query("addDocs") boolean addDocs,
            @Query("addTitle") boolean addTitle,
            @Query("date") String date
    );




    @FormUrlEncoded
    @POST("auth/realms/medyatakip.com/protocol/openid-connect/token")
    Call<TokenModel> getToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password
    );



//    @POST("auth/realms/medyatakip.com/protocol/openid-connect/token")
//    Call<TokenModel> getToken(
//            @Header("Cache-Control") String CacheControl,
//            @Header("Postman-Token") String PostmanToken,
//            @Header("Content-Type") String ContentType,
//            @Header("Content-Length") String ContentLength,
//            @Header("Host") String Host,
//            @Header("User-Agent") String UserAgent,
//            @Header("Accept") String Accept,
//            @Header("Accept-Encoding") String AcceptEncoding,
//            @Header("Connection") String Connection,
//            @Body TokenRequest request);

//    @POST("auth/realms/medyatakip.com/protocol/openid-connect/token")
//    Call<TokenModel> getToken(@Body TokenRequest request, @Header("Authorization") String authorization, @Body Headers2 headers);


}
