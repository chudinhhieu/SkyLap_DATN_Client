package com.example.skylap_datn_md03.ui.activities.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skylap_datn_md03.MainActivity;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private TextView tvRegister,tvKhongDangNhap;
    private TextView tvForgotPassword;
    private TextInputLayout iplUsername;
    private TextInputLayout iplPassword;
    private TextInputEditText ipUsername;
    private TextInputEditText ipPassword;
    private Button btnLogin;
    private TextWatcher inputWatcher;
    private AccountRetrofit accountRetrofit;
    private RetrofitService retrofitService;
    private MyAuth myAuth;
    private SharedPreferencesManager sharedPreferencesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesManager= new SharedPreferencesManager(this);
        setContentView(R.layout.activity_login);
        initView();
        retrofitService = new RetrofitService();
        fillDataToInput();
//        Theo dõi trạng thái của input để thực hiện validate form
        inputWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnLogin.setEnabled(validateForm());
            }
        };
        if (ipUsername != null) {
            ipUsername.addTextChangedListener(inputWatcher);
        }
        if (ipPassword != null) {
            ipPassword.addTextChangedListener(inputWatcher);
        }
        tvKhongDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoHomeScreen();
            }
        });
//        Sự kiện ấn button login và xử lý chức năng đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = new Account(ipUsername.getText().toString().trim(),ipPassword.getText().toString().trim());
                accountRetrofit = retrofitService.retrofit.create(AccountRetrofit.class);
                Call<MyAuth> login = accountRetrofit.signIn(account);
                login.enqueue(new Callback<MyAuth>() {
                    @Override
                    public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
                        myAuth = response.body();
                        if (myAuth != null){
                            if (myAuth.isSuccess()){
                                CustomToast.showToast(LoginActivity.this, myAuth.getMessage());
                                saveUserIdToSharedPreferences(myAuth.getValue());
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                CustomToast.showToast(LoginActivity.this, myAuth.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MyAuth> call, Throwable t) {
                        Log.e("Retrofit Failure", "Lỗi: " + t.getMessage());
                        t.printStackTrace();
                    }
                });
            }
        });

//        Sự kiện ấn vào textview đăng ký và chuyển sang màn hình RegisterActivity
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


//        Sự kiện ấn vào textview quên mật khẩu và chuyển sang màn hình ForgotPasswordActivity
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fillDataToInput() {
        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");
            if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
                ipUsername.setText(username);
                ipPassword.setText(password);
                btnLogin.setEnabled(true);
            }
        }
    }

    private void gotoHomeScreen() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //  Lưu lại uid của người dùng vào SharedPreferences
    private void saveUserIdToSharedPreferences(String userId) {
        sharedPreferencesManager.saveUserId(userId);
    }

    //    Xử lý validate form
    private boolean validateForm() {
        String username = ipUsername.getText().toString().trim();
        String password = ipPassword.getText().toString().trim();

        boolean isUsernameValid = true;
        boolean isPasswordValid = true;

        if (username.isEmpty()) {
            iplUsername.setError("Vui lòng nhập tài khoản");
            isUsernameValid = false;
        } else {
            iplUsername.setErrorEnabled(false);
        }

        int minUsernameLength = 5;
        int maxUsernameLength = 20;
        if (username.length() < minUsernameLength || username.length() > maxUsernameLength) {
            iplUsername.setError("Tài khoản phải có từ " + minUsernameLength + " đến " + maxUsernameLength + " ký tự");
            isUsernameValid = false;
        } else {
            if (isUsernameValid) {
                iplUsername.setErrorEnabled(false);
            }
        }

        if (password.isEmpty()) {
            iplPassword.setError("Vui lòng nhập mật khẩu");
            isPasswordValid = false;
        } else {
            iplPassword.setErrorEnabled(false);
        }

        int minPasswordLength = 6;
        int maxPasswordLength = 20;
        if (password.length() < minPasswordLength || password.length() > maxPasswordLength) {
            iplPassword.setError("Mật khẩu phải có từ " + minPasswordLength + " đến " + maxPasswordLength + " ký tự");
            isPasswordValid = false;
        } else {
            if (isPasswordValid) {
                iplPassword.setErrorEnabled(false);
            }
        }
        return isUsernameValid && isPasswordValid;
    }

    // Khởi tạo các biến
    @SuppressLint("WrongViewCast")
    void initView() {
        tvRegister = findViewById(R.id.lg_tv_register);
        tvKhongDangNhap = findViewById(R.id.lg_tv_dns);
        tvForgotPassword = findViewById(R.id.lg_tv_forgot_password);
        iplUsername = findViewById(R.id.lg_ipl_username);
        iplPassword = findViewById(R.id.lg_ipl_password);
        ipUsername = findViewById(R.id.lg_ip_username);
        ipPassword = findViewById(R.id.lg_ip_password);
        btnLogin = findViewById(R.id.lg_btn_login);
        btnLogin.setEnabled(false);
    }
}