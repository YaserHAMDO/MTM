package com.example.mtm;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofitSecurity;
    private static Retrofit retrofitWeb;

    private static final String BASE_URL_SECURITY = "https://security.medyatakip.com/";
    private static final String BASE_URL_WEB = "https://web.medyatakip.com/";

    public static Retrofit getClient(int urlCode) {
        String baseUrl;
        if (urlCode == 1) {
            baseUrl = BASE_URL_SECURITY;
        } else {
            baseUrl = BASE_URL_WEB;
        }

        Retrofit retrofit;
        if (urlCode == 1) {
            retrofit = retrofitSecurity;
        } else {
            retrofit = retrofitWeb;
        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            if (urlCode == 1) {
                retrofitSecurity = retrofit;
            } else {
                retrofitWeb = retrofit;
            }
        }

        return retrofit;
    }
}