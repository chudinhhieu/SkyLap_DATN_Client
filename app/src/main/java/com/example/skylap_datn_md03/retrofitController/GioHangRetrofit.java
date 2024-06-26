package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.GioHang;
import com.example.skylap_datn_md03.data.models.MyAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GioHangRetrofit {
    //    Lấy giỏ hàng theo id account
    @GET("api/gioHang/{id}")
    Call<List<GioHang>> getGioHangByIDAccount(@Path("id") String id);

    //    Thêm giỏ hàng
    @POST("api/gioHang/add")
    Call<MyAuth> themGioHang(@Body GioHang gioHang);
    @PUT("api/gioHang/edit-soLuong/{id}")
    Call<GioHang> suaSoLuong(@Path("id") String id,@Body GioHang gioHang);

    @DELETE("api/gioHang/delete/{id}")
    Call<MyAuth> xoaGioHang(@Path("id") String id);
}
