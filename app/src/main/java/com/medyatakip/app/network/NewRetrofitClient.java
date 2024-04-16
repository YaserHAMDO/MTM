package com.medyatakip.app.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewRetrofitClient {
    public static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient() // Set lenient to true
                    .create();

            retrofit = new Retrofit.Builder()
//                    .baseUrl("http://3.122.116.233:3001/v0/")
//                    .baseUrl(preferenceManager.getString(Constants.KEY_DYNAMIC_BASE_URL))
                    .baseUrl(baseUrl)
//                    .baseUrl("https://3.123.40.58:3001/v0/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

}
