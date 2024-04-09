package com.example.skylap_datn_md03.retrofitController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();
    public  Gson gson = new GsonBuilder().setLenient().create();
    public  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://skylab-datn-server.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build();

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
