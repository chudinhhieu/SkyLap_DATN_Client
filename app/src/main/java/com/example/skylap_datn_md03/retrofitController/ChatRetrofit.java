package com.example.skylap_datn_md03.retrofitController;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ChatRetrofit {
    @POST("api/chat/create/{id}")
    Call<String>CreateConverSation(@Path("id") String id);
}
