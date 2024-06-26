package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.MyAuth;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DanhGiaRetrofit {

    @Multipart
    @POST("api/danhGia/{id}")
    Call<MyAuth> postDanhGia(@Path("id") String id,
                             @Part("soSao") RequestBody soSao,
                             @Part("noiDung") RequestBody noiDung,
                             @Part List<MultipartBody.Part> image);
    @GET("api/danhGia/da-danh-gia/{idAccount}")
    Call<List<DonHang>> getDaDanhGia(@Path("idAccount") String idAccount);
    @GET("api/danhGia/by-idSanPham/{id}")
    Call<List<DonHang>> getDaDanhGiaTheoSanPham(@Path("id") String id);

    @GET("api/danhGia/chua-danh-gia/{idAccount}")
    Call<List<DonHang>> getChuaDanhGia(@Path("idAccount") String idAccount);

    @GET("api/danhGia/sl-chua-danh-gia/{idAccount}")
    Call<Integer> getSoLuongChuaDanhGia(@Path("idAccount") String idAccount);
}
