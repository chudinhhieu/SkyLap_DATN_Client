package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.data.models.DiaChi;
import com.example.skylap_datn_md03.data.models.MyAuth;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AccountRetrofit {
    // Đăng ký tài khoản
    @POST("api/account/sign-up")
    Call<MyAuth> signUp(@Body Account account);

    // Đăng nhập
    @POST("api/account/sign-in")
    Call<MyAuth> signIn(@Body Account account);

    // Lấy thông tin của một account dựa trên ID
    @GET("api/account/{id}")
    Call<Account> getAccountById(@Path("id") String accountId);

   @PUT("api/account/edit-account/{id}")
   Call<MyAuth> editAccount(@Path("id") String id,@Body Account account);
   
  // Cập nhật avatar
    @Multipart
    @PUT("api/account/edit-avatar/{id}")
    Call<ResponseBody> updateAccountAvatar(@Path("id") String accountId, @Part MultipartBody.Part avatar);

    @PUT("api/account/add-dia-chi/{id}")
    Call<MyAuth> themDiaChi(@Path("id") String id,@Body DiaChi diaChi );

    @PUT("api/account/edit-sdt/{id}")
    Call<MyAuth> themSDT(@Body Account account,@Path("id") String id );
    @PUT("api/account/edit-mat-khau/{id}")
    Call<MyAuth> updatePassword(@Path("id") String id, @Body Map<String, String> passwordData);
}
