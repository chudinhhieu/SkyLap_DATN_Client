package com.example.skylap_datn_md03.retrofitController;
import com.example.skylap_datn_md03.data.models.HangSX;
import com.example.skylap_datn_md03.data.models.SanPham;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface HangSxInterface {
    @GET("api/hangSX")
    Call<List<HangSX>> getListHangSx();
}
