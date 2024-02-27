package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.data.models.SanPham;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountRetrofit {
    //   Đăng ký tài khoản
    @POST("api/account/sign-up")
    Call<MyAuth> signUp(@Body Account account);


//    Đăng nhập
    @POST("api/account/sign-in")
    Call<MyAuth> signIn(@Body Account account);

}