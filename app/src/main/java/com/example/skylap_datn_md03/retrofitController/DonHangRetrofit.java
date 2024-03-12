package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.DonHang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DonHangRetrofit {
    @GET("api/donHang")
    Call<List<DonHang>>GetAllDonHang();
    @POST("api/donHang/add")
    Call<DonHang>addDonHang(@Body DonHang donHang);
    @POST("api/donHang/add-trang-thai/{id}")
    Call<Void>themTrangThai(@Part("id") String id);
}
