package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.GioHang;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GioHangRetrofit {
    @GET("/api/gioHang/{id}")
    Call<GioHang> getGioHangByIdAccount(@Path("id") String id);

    @POST("/api/gioHang/add")
    Call<GioHang> addGioHang(@Body GioHang gioHang);

    @PUT("/api/gioHang/edit-soLuong")
    Call<GioHang> editSoLuongSanPham(@Body GioHang gioHang);

    @DELETE("/api/gioHang/delete")
    Call<Void> deleteGioHang(@Body GioHang gioHang);

}