package com.example.skylap_datn_md03.retrofitController;

import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.data.models.MyAuth;

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

    // Cập nhật họ tên của một account dựa trên ID
    @PUT("api/account/edit-ho-ten/{id}")
    Call<ResponseBody> updateAccountName(@Path("id") String accountId, @Body RequestBody requestBody);

    // Cập nhật mật khẩu
    @PUT("api/account/edit-mat-khau/{id}")
    Call<ResponseBody> updateAccountPassword(@Path("id") String accountId, @Body RequestBody requestBody);

    // Cập nhật số điện thoại
    @PUT("api/account/edit-sdt/{id}")
    Call<ResponseBody> updateAccountPhone(@Path("id") String accountId, @Body RequestBody requestBody);

    // Cập nhật email
    @PUT("api/account/edit-email/{id}")
    Call<ResponseBody> updateAccountEmail(@Path("id") String accountId, @Body RequestBody requestBody);

    // Cập nhật địa chỉ
    @PUT("api/account/add-dia-chi/{id}")
    Call<ResponseBody> addAddress(@Path("id") String accountId, @Body RequestBody requestBody);
    @Multipart
    @PUT("api/account/edit-avatar/{id}")
    Call<ResponseBody> updateAccountAvatar(@Path("id") String accountId, @Part MultipartBody.Part avatar);


}
