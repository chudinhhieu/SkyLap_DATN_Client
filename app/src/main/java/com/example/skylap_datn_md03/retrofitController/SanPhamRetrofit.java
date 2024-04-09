package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.SanPham;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SanPhamRetrofit {

    // Lấy tất cả sản phẩm
    @GET("api/sanPham")
    Call<List<SanPham>> getListSanPham();

    // Lấy sản phẩm theo id
    @GET("api/sanPham/get-by-id/{id}")
    Call<SanPham> getSanPhamByID(@Path("id") String id);
    @GET("api/sanPham/cpu/{cpu}")
    Call<List<SanPham>> getListSanPhamByCPU(@Path("cpu") String cpu);
    @POST("api/sanpham/search")
    Call<List<SanPham>> getsearch(@Body SanPham sanPham);
    @GET("api/sanPham/hang/{id}")
    Call<List<SanPham>> getListSanPhamByHang(@Path("id") String id);
    // Lọc sản phẩm dựa trên các tiêu chí nhất định
    @GET("api/sanPham/filter")
    Call<List<SanPham>> filterSanPham(
            @Query("idHangSx") String idHangSx,
            @Query("giaMin") String giaMin,
            @Query("giaMax") String giaMax,
            @Query("cpu") String cpu,
            @Query("ram") String ram,
            @Query("display") String display,
            @Query("baohanh") String baohanh,
            @Query("gpu") String gpu,
            @Query("rom") String rom

    );
}
