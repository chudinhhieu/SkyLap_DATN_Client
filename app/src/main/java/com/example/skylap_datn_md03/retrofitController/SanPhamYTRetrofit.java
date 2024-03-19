package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.SanPhamYeuThich;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SanPhamYTRetrofit {

    @GET("sanPhamYT/{idAccount}")
    Call<List<SanPhamYeuThich>> getSanPhamYeuThichByAccount(@Path("idAccount") String idAccount);

    @POST("sanPhamYT/check")
    Call<Boolean> checkSanPhamYeuThich(@Body SanPhamYeuThich sanPhamYeuThich);

    @POST("sanPhamYT")
    Call<Void> addSanPhamYeuThich(@Body SanPhamYeuThich sanPhamYeuThich);

    @DELETE("sanPhamYT/delete/{id}")
    Call<Void> deleteSanPhamYeuThich(@Path("id") String id);
}
