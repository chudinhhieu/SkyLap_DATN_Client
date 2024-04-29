package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.DanhGia;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.MyAuth;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DonHangRetrofit {
    @GET("api/donHang")
    Call<List<DonHang>>GetAllDonHang();
    @GET("api/donHang/get-by-id/{id}")
    Call<DonHang>GetDonHangById(@Path("id") String id);
    @POST("api/donHang/add")
    Call<MyAuth>addDonHang(@Body DonHang donHang);
    @POST("api/donHang/add-trang-thai/{id}")
    Call<Void> themTrangThai(@Path("id") String id, @Query("trangThai") String trangThai);
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
    @GET("api/donHang/tra-hang/{id}")
    Call<List<DonHang>>layDonHangTraHang(@Path("id") String id);
    @GET("api/donHang/da-ban/{id}")
    Call<Integer>layDaBan(@Path("id") String id);
    @GET("api/donHang/sao/{id}")
    Call<Double>laySaoTrungBinh(@Path("id") String id);
    @GET("api/donHang/lan-danh-gia/{id}")
    Call<Integer>layLanDanhGia(@Path("id") String id);
    @GET("api/donHang/danh-sach-danh-gia/{id}")
    Call<List<DanhGia>>danhSachDanhGia(@Path("id") String id);


    @GET("api/donHang/sl-cho-xac-nhan/{id}")
    Call<Integer>laySLDonHangChoXacNhan(@Path("id") String id);

    @GET("api/donHang/sl-cho-giao-hang/{id}")
    Call<Integer>laySLDonHangChoGiaoHang(@Path("id") String id);
    @GET("api/donHang/sl-dang-giao-hang/{id}")
    Call<Integer>laySLDonHangDangGiaoHang(@Path("id") String id);

    @Multipart
    @PUT("api/donHang/bao-hanh/{iddh}/{idbh}")
    Call<MyAuth> putBaoHanh(@Path("iddh") String iddh,
                            @Path("idbh") String idbh,
                             @Part("lyDo") RequestBody lyDo,
                             @Part("tinhTrang") RequestBody tinhTrang,
                             @Part List<MultipartBody.Part> image);
}
