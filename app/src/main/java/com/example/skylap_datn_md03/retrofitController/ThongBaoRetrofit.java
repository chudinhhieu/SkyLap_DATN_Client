package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.KhuyenMai;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.data.models.ThongBao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ThongBaoRetrofit {
    @GET("api/thongBao/{id}")
    Call<List<ThongBao>> getListThongBaoByIDAccount(@Path("id") String id);

    @PUT("api/thongBao/da-xem/{id}")
    Call<MyAuth> daXem(@Path("id") String id);
    @PUT("api/thongBao/da-xem-all/{id}")
    Call<MyAuth> daXemAll(@Path("id") String id);
}
