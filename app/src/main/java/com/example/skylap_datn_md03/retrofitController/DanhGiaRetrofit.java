package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.DanhGia;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DanhGiaRetrofit {
    @GET("/api/danhGia/{idAccount}")
    Call<List<DanhGia>> getDaDanhGia(@Path("idAccount") String idAccount);

    @GET("/api/danhGia/chuaDanhGia")
    Call<List<DanhGia>> getChuaDanhGia();

    @POST("/api/danhGia/{id}")
    Call<Void> themDanhGia(@Path("id") String id, @Part MultipartBody.Part image);
}
