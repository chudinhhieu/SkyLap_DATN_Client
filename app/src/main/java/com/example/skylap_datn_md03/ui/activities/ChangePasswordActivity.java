package com.example.skylap_datn_md03.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.example.skylap_datn_md03.R;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText currentPassword, newPassword, confirmNewPassword;
    private Button changePasswordButton;
    private SharedPreferencesManager sharedPreferencesManager;
    private RetrofitService retrofitService;
    private AccountRetrofit accountRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        sharedPreferencesManager = new SharedPreferencesManager(this);

        retrofitService = new RetrofitService();
        accountRetrofit = retrofitService.getRetrofit().create(AccountRetrofit.class);

        currentPassword = findViewById(R.id.current_password);
        newPassword = findViewById(R.id.new_password);
        confirmNewPassword = findViewById(R.id.confirm_new_password);
        changePasswordButton = findViewById(R.id.change_password_button);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = sharedPreferencesManager.getUserId();
                String currentPass = currentPassword.getText().toString();
                String newPass = newPassword.getText().toString();
                String confirmPass = confirmNewPassword.getText().toString();

                if (validatePasswords(currentPass, newPass, confirmPass)) {
                    updatePassword(userId, currentPass, newPass);
                }
            }
        });


        if (sharedPreferencesManager.hasUserId()) {
            loadUserDetails(sharedPreferencesManager.getUserId());
        }
    }


    private void loadUserDetails(String userId) {
        Call<Account> call = accountRetrofit.getAccountById(userId);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Success: Update UI with the retrieved user details
                    Account account = response.body();
                    updateUIWithUserDetails(account);
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Failed to load user details: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateUIWithUserDetails(Account account) {
        if (account != null) {
        }
    }

    private void updatePassword(String userId, String currentPassword, String newPassword) {
        Map<String, String> passwordDetails = new HashMap<>();
        passwordDetails.put("currentPassword", currentPassword);
        passwordDetails.put("newPassword", newPassword);

        Call<MyAuth> call = accountRetrofit.updatePassword(userId, passwordDetails);
        call.enqueue(new Callback<MyAuth>() {
            @Override
            public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(ChangePasswordActivity.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Password update failed: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "An error occurred, please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyAuth> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validatePasswords(String currentPass, String newPassStr, String confirmPass) {
        // Check for empty fields
        if (currentPass.isEmpty() || newPassStr.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "Cần điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if new password matches confirmation password
        if (!newPassStr.equals(confirmPass)) {
            Toast.makeText(this, "Nhập lại mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check new password is different from the current password
        if (newPassStr.equals(currentPass)) {
            Toast.makeText(this, "Mật khẩu mới phải khác mật khẩu hiện tại!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check for minimum length
        if (newPassStr.length() < 6|| newPassStr.length() > 20) {
            Toast.makeText(this, "Mật khẩu phải từ 6-20 kí tự", Toast.LENGTH_SHORT).show();
            return false;
        }

//        // Check for at least one letter
//        if (!newPassStr.matches(".*[a-zA-Z].*")) {
//            Toast.makeText(this, "Mật khẩu phải chứa ít nhất một chữ cái.", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        // Check for at least one digit
//        if (!newPassStr.matches(".*\\d.*")) {
//            Toast.makeText(this, "Mật khẩu phải chứa ít nhất một chữ số.", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        // Check for at least one special character
//        if (!newPassStr.matches(".*[@#$%^&+=].*")) {
//            Toast.makeText(this, "Mật khẩu phải chứa ít nhất một ký tự đặc biệt (@#$%^&+=).", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }


}

