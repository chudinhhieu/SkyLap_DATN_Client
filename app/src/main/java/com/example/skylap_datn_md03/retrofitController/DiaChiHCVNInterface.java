package com.example.skylap_datn_md03.retrofitController;
import com.example.skylap_datn_md03.data.models.DVHCVN.Huyen;
import com.example.skylap_datn_md03.data.models.DVHCVN.Tinh;
import com.example.skylap_datn_md03.data.models.DVHCVN.Xa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DiaChiHCVNInterface {
    @GET("api/diaChi/level1")
    Call<List<Tinh>> getAllLevel1();

    @GET("api/diaChi/level1/{level1Id}/level2")
    Call<List<Huyen>> getLevel2OfLevel1(@Path("level1Id") String level1Id);

    @GET("api/diaChi/level1/{level1Id}/level2/{level2Id}/level3")
    Call<List<Xa>> getLevel3OfLevel2(@Path("level1Id") String level1Id, @Path("level2Id") String level2Id);
}
