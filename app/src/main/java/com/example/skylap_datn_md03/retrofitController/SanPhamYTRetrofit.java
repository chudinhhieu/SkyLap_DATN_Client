package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.FavoriteResponse;
import com.example.skylap_datn_md03.data.models.SanPhamYeuThich;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SanPhamYTRetrofit {

    @GET("api/yeuThich/{idAccount}")
    Call<List<SanPhamYeuThich>> getSanPhamYeuThichByAccount(@Path("idAccount") String idAccount);

    @POST("api/yeuThich/check/")
    Call<FavoriteResponse> checkSanPhamYeuThich(@Body SanPhamYeuThich sanPhamYeuThich);


    @POST("api/yeuThich/")
    Call<Void> addSanPhamYeuThich(@Body SanPhamYeuThich sanPhamYeuThich);

    @DELETE("api/yeuThich/delete/{id}")
    Call<Void> deleteSanPhamYeuThich(@Path("id") String _id);
}
