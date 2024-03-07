package com.example.skylap_datn_md03.retrofitController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public  Gson gson = new GsonBuilder().setLenient().create();
    public  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.89:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
