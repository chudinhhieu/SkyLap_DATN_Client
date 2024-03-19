package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import retrofit2.http.Path;

public interface MessageRetrofit {
   @POST("/api/mess/send")
    Call<Void> CreateMess(@Body Message message);
   @PUT("/api/mess/revoke/{id}")
    Call<Void> revokeMess(@Path("id") String id);
    @PUT("/api/mess/seen/{id}")
    Call<Void> putSeen(@Path("id") String id);
}
