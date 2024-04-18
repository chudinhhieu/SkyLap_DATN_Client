package com.example.skylap_datn_md03.ui.activities.auth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.skylap_datn_md03.MainActivity;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView tvLogin;
    private TextInputLayout iplUsername;
    private TextInputLayout iplPassword;
    private TextInputLayout iplRePassword;
    private TextInputEditText ipUsername;
    private TextInputEditText ipPassword;
    private TextInputEditText ipRePassword;
    private Button btnRegister;
    private ImageView imgBack;
    private TextWatcher inputWatcher;
    private AccountRetrofit accountRetrofit;
    private RetrofitService retrofitService;
    private MyAuth myAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        retrofitService = new RetrofitService();
        inputWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                btnRegister.setEnabled(validateForm());
            }
        };

        if (ipUsername != null) {
            ipUsername.addTextChangedListener(inputWatcher);
        }

        if (ipPassword != null) {
            ipPassword.addTextChangedListener(inputWatcher);
        }

        if (ipRePassword != null) {
            ipRePassword.addTextChangedListener(inputWatcher);
        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = new Account(ipUsername.getText().toString().trim(),ipPassword.getText().toString().trim());
                accountRetrofit = retrofitService.retrofit.create(AccountRetrofit.class);
                Call<MyAuth> register = accountRetrofit.signUp(account);
                register.enqueue(new Callback<MyAuth>() {
                    @Override
                    public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
                        myAuth = response.body();
                       if (myAuth != null){
                           if (myAuth.isSuccess()){
                               CustomToast.showToast(RegisterActivity.this, myAuth.getMessage());
                               Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                               intent.putExtra("username", ipUsername.getText().toString().trim());
                               intent.putExtra("password", ipPassword.getText().toString().trim());
                               startActivity(intent);
                           }else {
                               CustomToast.showToast(RegisterActivity.this, myAuth.getMessage());
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
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validateForm() {
        String username = ipUsername.getText().toString().trim();
        String password = ipPassword.getText().toString().trim();
        String rePassword = ipRePassword.getText().toString().trim();

        boolean isUsernameValid = true;
        boolean isPasswordValid = true;
        boolean isRePasswordValid = true;

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

        if (!password.equals(rePassword)) {
            iplRePassword.setError("Mật khẩu không khớp");
            isRePasswordValid = false;
        } else {
            if (isRePasswordValid) {
                iplRePassword.setErrorEnabled(false);
            }
        }

        return isUsernameValid && isPasswordValid && isRePasswordValid;
    }

    void initView() {
        tvLogin = findViewById(R.id.rg_btn_login);
        iplUsername = findViewById(R.id.rg_ipl_username);
        iplPassword = findViewById(R.id.rg_ipl_password);
        iplRePassword = findViewById(R.id.rg_ipl_repassword);
        ipUsername = findViewById(R.id.rg_ip_username);
        ipPassword = findViewById(R.id.rg_ip_password);
        ipRePassword = findViewById(R.id.rg_ip_repassword);
        btnRegister = findViewById(R.id.rg_btn_register);
        imgBack = findViewById(R.id.rg_img_back);
        btnRegister.setEnabled(false);
    }
}
