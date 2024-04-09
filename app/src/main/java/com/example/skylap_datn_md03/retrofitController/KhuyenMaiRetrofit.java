package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.KhuyenMai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface KhuyenMaiRetrofit {
    @GET("api/khuyenMai")
    Call<List<KhuyenMai>> getListKhuyenMai();

    @GET("api/khuyenMai/get-by-id/{id}")
    Call<KhuyenMai>getKhuyenMaiById(@Path("id") String id);
}
