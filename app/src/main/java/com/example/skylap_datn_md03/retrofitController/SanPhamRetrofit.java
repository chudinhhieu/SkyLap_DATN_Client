package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.SanPham;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SanPhamRetrofit {

    // Lấy tất cả sản phẩm
    @GET("api/sanPham")
    Call<List<SanPham>> getListSanPham();

    // Lấy sản phẩm theo id
    @GET("api/sanPham/{id}")
    Call<SanPham> getSanPhamByID(@Path("id") String id);



}
