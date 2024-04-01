package com.example.skylap_datn_md03.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.utils.RealPathUtil;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountManagementActivity extends AppCompatActivity {

    private AccountRetrofit accountService;
    private EditText editTextHoTen, editTextSdt, editTextEmail, editTextMatKhau, editTextDiaChi;
    private ImageView imgAvatar;
    private Button btnSave;
    private ImageButton btnTogglePassword;
    private SharedPreferencesManager sharedPreferencesManager;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        initUI();
        setupRetrofit();
        loadUserInfo();
    }
    private void setupRetrofit() {
        RetrofitService retrofitService = new RetrofitService();
        accountService = retrofitService.getRetrofit().create(AccountRetrofit.class);
        sharedPreferencesManager = new SharedPreferencesManager(this);
    }

    private void initUI() {
        editTextHoTen = findViewById(R.id.editTextHoTen);
        editTextSdt = findViewById(R.id.editTextSdt);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMatKhau = findViewById(R.id.editTextMatKhau);
        editTextDiaChi = findViewById(R.id.editTextDiaChi);
        imgAvatar = findViewById(R.id.imgAvatar);
        btnSave = findViewById(R.id.btnSave);
        btnTogglePassword = findViewById(R.id.btnTogglePassword);

        btnSave.setOnClickListener(v -> updateUserInfo());
        btnTogglePassword.setOnClickListener(v -> togglePasswordVisibility());
        imgAvatar.setOnClickListener(view -> {
            openImageChooser();
        });
    }

    private void loadUserInfo() {
        String userId = sharedPreferencesManager.getUserId();
        Log.d("AccountManagement", "Loading user info for user ID: " + userId);
        accountService.getAccountById(userId).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Account account = response.body();
                    Log.d("AccountManagement", "Account loaded: " + account.toString());
                    displayUserInfo(account);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                showToast("Lỗi khi tải thông tin người dùng");
            }
        });
    }
    private void updateUserInfo() {
        String userId = sharedPreferencesManager.getUserId();

        // Lấy thông tin từ EditText
        String hoTen = editTextHoTen.getText().toString();
        String sdt = editTextSdt.getText().toString();
        String email = editTextEmail.getText().toString();
        String diaChi = editTextDiaChi.getText().toString();
        String matKhau = editTextMatKhau.getText().toString();

        // Kiểm tra và cập nhật họ tên
        if (!hoTen.isEmpty()) {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{\"hoTen\":\"" + hoTen + "\"}");
            accountService.updateAccountName(userId, body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        showToast("Cập nhật họ tên thành công");
                    } else {
                        showToast("Lỗi cập nhật họ tên");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    showToast("Lỗi kết nối mạng");
                }
            });
        }

        // Kiểm tra và cập nhật số điện thoại
        if (!sdt.isEmpty()) {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{\"sdt\":\"" + sdt + "\"}");
            accountService.updateAccountPhone(userId, body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        showToast("Cập nhật số điện thoại thành công");
                    } else {
                        showToast("Lỗi cập nhật số điện thoại");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    showToast("Lỗi kết nối mạng");
                }
            });
        }

        // Kiểm tra và cập nhật email
        if (!email.isEmpty()) {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{\"email\":\"" + email + "\"}");
            accountService.updateAccountEmail(userId, body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        showToast("Cập nhật email thành công");
                    } else {
                        showToast("Lỗi cập nhật email");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    showToast("Lỗi kết nối mạng");
                }
            });
        }

        // Kiểm tra và cập nhật mật khẩu
        if (!matKhau.isEmpty()) {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{\"matKhau\":\"" + matKhau + "\"}");
            accountService.updateAccountPassword(userId, body).enqueue(new Callback<ResponseBody>() {                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        showToast("Cập nhật mật khẩu thành công");
                    } else {
                        showToast("Lỗi cập nhật mật khẩu");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    showToast("Lỗi kết nối mạng");
                }
            });
        }

        // Kiểm tra và cập nhật địa chỉ
        if (!diaChi.isEmpty()) {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{\"diaChi\":\"" + diaChi + "\"}");
            accountService.addAddress(userId, body).enqueue(new Callback<ResponseBody>() {                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        showToast("Cập nhật địa chỉ thành công");
                    } else {
                        showToast("Lỗi cập nhật địa chỉ");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    showToast("Lỗi kết nối mạng");
                }
            });
        }


    }
    private void displayUserInfo(Account account) {
        editTextHoTen.setText(account.getHoTen());
        editTextSdt.setText(account.getSdt());
        editTextEmail.setText(account.getEmail());
        editTextMatKhau.setText(account.getMatKhau());
        editTextDiaChi.setText(account.getDiaChi() != null ? account.getDiaChi().getDiaChi() : "");
        if (account.getAvatar() != null && !account.getAvatar().isEmpty()) {
            Picasso.get().load(account.getAvatar()).into(imgAvatar);
        }
    }
    public void togglePasswordVisibility() {
        if (editTextMatKhau.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            editTextMatKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            btnTogglePassword.setImageResource(R.drawable.ic_visibility_off);
        } else {
            editTextMatKhau.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            btnTogglePassword.setImageResource(R.drawable.ic_visibility);
        }
        editTextMatKhau.setSelection(editTextMatKhau.getText().length());
    }
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        mGetContent.launch("image/*");
    }

    private ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri uri) {
            if (uri != null) {
                selectedImageUri = uri;
                imgAvatar.setImageURI(uri);
                uploadImageToServer(uri);
            }
        }
    });

    private void uploadImageToServer(Uri imageUri) {
        String filePath = RealPathUtil.getRealPath(getApplicationContext(), imageUri);
        File file = new File(filePath);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

        String userId = sharedPreferencesManager.getUserId();

        accountService.updateAccountAvatar(userId, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    showToast("Avatar updated successfully");
                } else {
                    try {
                        // Read the error body to get the actual error message
                        String errorString = response.errorBody().string();
                        Log.d("AvatarUpload", "Response error: " + errorString);
                        showToast("Failed to update avatar: " + errorString);
                    } catch (IOException e) {
                        Log.e("AvatarUpload", "Error reading error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showToast("Error connecting to the server: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
