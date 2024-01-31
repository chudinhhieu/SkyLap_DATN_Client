package com.example.skylap_datn_md03.ui.activities.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skylap_datn_md03.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class ForgotPasswordActivity extends AppCompatActivity {
    private TextView tvResult;
    private ImageView imgBack;
    private TextInputLayout iplEmail;
    private TextInputEditText ipEmail;
    private Button btnSend;
    private TextWatcher inputWatcher;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();

        firebaseAuth = FirebaseAuth.getInstance();

        inputWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnSend.setEnabled(validateForm());
                tvResult.setVisibility(View.GONE);
            }
        };

        if (ipEmail != null) {
            ipEmail.addTextChangedListener(inputWatcher);
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ipEmail.getText().toString().trim();

                if (validateForm()) {
                    checkIfEmailExists(email);
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean validateForm() {
        String email = ipEmail.getText().toString().trim();

        boolean isEmailValid = true;

        if (email.isEmpty()) {
            iplEmail.setError("Vui lòng nhập email");
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            iplEmail.setError("Email không hợp lệ");
            isEmailValid = false;
        } else {
            iplEmail.setErrorEnabled(false);
        }

        return isEmailValid;
    }

    private void checkIfEmailExists(String email) {
        firebaseAuth.createUserWithEmailAndPassword(email, "password")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            tvResult.setVisibility(View.VISIBLE);
                            tvResult.setTextColor(getResources().getColor(R.color.red));
                            tvResult.setText("Người dùng với email này không tồn tại.");
                            firebaseAuth.getCurrentUser().delete();
                        } else {
                            sendPasswordResetEmail(email);
                        }
                    }
                });
    }

    private void sendPasswordResetEmail(String email) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                tvResult.setVisibility(View.VISIBLE);

                if (task.isSuccessful()) {
                    tvResult.setTextColor(getResources().getColor(R.color.black500));
                    tvResult.setText("Đã gửi email đặt lại mật khẩu. Vui lòng kiểm tra hòm thư của bạn.");
                    btnSend.setVisibility(View.GONE);
                } else {
                    tvResult.setTextColor(getResources().getColor(R.color.red));
                    Exception exception = task.getException();
                    tvResult.setText("Email không hợp lệ.Vui lòng nhập lại.");
                }
            }
        });
    }

    private void initView() {
        tvResult = findViewById(R.id.fg_tv_result);
        iplEmail = findViewById(R.id.fg_ipl_email);
        ipEmail = findViewById(R.id.fg_ip_email);
        btnSend = findViewById(R.id.fg_btn_send);
        imgBack = findViewById(R.id.fg_img_back);

        btnSend.setEnabled(false);
    }
}
