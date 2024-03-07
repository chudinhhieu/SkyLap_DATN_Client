package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MessageRetrofit {
   @POST("api/message/send")
    Call<Void> CreateMess(@Body Message message);

}
