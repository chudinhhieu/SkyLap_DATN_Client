package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.DonHang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DonHangRetrofit {
    @GET("api/donHang")
    Call<List<DonHang>>GetAllDonHang();
    @POST("api/donHang/add")
    Call<DonHang>addDonHang(@Body DonHang donHang);
    @POST("api/donHang/add-trang-thai/{id}")
    Call<Void>themTrangThai(@Part("id") String id);

    @GET("api/donHang/cho-xac-nhan/{id}")
    Call<List<DonHang>>layDonHangChoXacNhan(@Path("id") String id);

    @GET("api/donHang/cho-giao-hang/{id}")
    Call<List<DonHang>>layDonHangChoGiaoHang(@Path("id") String id);
    @GET("api/donHang/dang-giao-hang/{id}")
    Call<List<DonHang>>layDonHangDangGiaoHang(@Path("id") String id);
    @GET("api/donHang/da-giao-hang/{id}")
    Call<List<DonHang>>layDonHangDaGiaoHang(@Path("id") String id);

    @GET("api/donHang/da-huy/{id}")
    Call<List<DonHang>>layDonHangDaHuy(@Path("id") String id);
}
