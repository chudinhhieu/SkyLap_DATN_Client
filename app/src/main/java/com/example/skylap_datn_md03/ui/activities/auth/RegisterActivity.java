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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private LinearLayout btnLoginGoogle;
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
    private static final int RQ_CODE_GG = 20;
    private static final String TAG_ERROR_AUTH = "Error Auth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

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
        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleGoogleSignIn();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomToast.showToast(RegisterActivity.this, "Tài khoản đã tồn tại. Vui lòng nhập tài khoản khác.");
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    private void handleGoogleSignIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RQ_CODE_GG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RQ_CODE_GG) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthGoogle(account.getIdToken());
            } catch (Exception e) {
                Log.e(TAG_ERROR_AUTH, "Google: " + e);
            }
        }
    }

    private void firebaseAuthGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                    if (firebaseUser != null) {
                        String uid = firebaseUser.getUid();
                        saveUserIdToSharedPreferences(uid);
                    }
                    Toast.makeText(RegisterActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    gotoHomeScreen();
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void gotoHomeScreen() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void saveUserIdToSharedPreferences(String userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("id_user_auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid", userId);
        editor.apply();
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
        btnLoginGoogle = findViewById(R.id.rg_btn_login_google);
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
