package com.example.skylap_datn_md03.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.example.skylap_datn_md03.utils.RealPathUtil;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.google.android.material.textfield.TextInputEditText;
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
    private TextInputEditText editTextHoTen, editTextSdt, editTextEmail,editTextDiaChi,editTextTaiKhoan;
    private ImageView imgAvatar,btnExit,btnAddAvatar;
    private Button btnSave;
    private TextView tvIdUser;
    private Account account;
    private Boolean isUpdate = false;

    private SharedPreferencesManager sharedPreferencesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        initUI();
        setupRetrofit();
        loadUserInfo();
        editTextDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountManagementActivity.this, ThemDiaChiActivity.class));
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setupRetrofit() {
        RetrofitService retrofitService = new RetrofitService();
        accountService = retrofitService.getRetrofit().create(AccountRetrofit.class);
        sharedPreferencesManager = new SharedPreferencesManager(this);
    }

    private void initUI() {
        editTextTaiKhoan = findViewById(R.id.editTextTaiKhoan);
        editTextHoTen = findViewById(R.id.editTextHoTen);
        editTextSdt = findViewById(R.id.editTextSdt);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDiaChi = findViewById(R.id.editTextDiaChi);
        imgAvatar = findViewById(R.id.imgAvatar);
        btnAddAvatar = findViewById(R.id.addAvatar);
        btnExit = findViewById(R.id.aam_img_back);
        btnSave = findViewById(R.id.btnSave);
        tvIdUser = findViewById(R.id.tv_idUser);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });
        btnAddAvatar.setOnClickListener(view -> {
            openImageChooser();
        });
    }

    private void loadUserInfo() {
        String userId = sharedPreferencesManager.getUserId();
        accountService.getAccountById(userId).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful() && response.body() != null) {
                    account = response.body();
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
        accountService.editAccount(sharedPreferencesManager.getUserId(),new Account(hoTen,sdt,email)).enqueue(new Callback<MyAuth>() {
            @Override
            public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
                CustomToast.showToast(AccountManagementActivity.this,response.body().getMessage());
                finish();
            }

            @Override
            public void onFailure(Call<MyAuth> call, Throwable t) {

            }
        });

    }
    private void displayUserInfo(Account account) {
        tvIdUser.setText("ID: "+account.get_id());
        editTextTaiKhoan.setText(account.getTaiKhoan());
        if (account.getHoTen() != null){
            editTextHoTen.setText(account.getHoTen());
        }
        if (account.getSdt() != null){
            editTextSdt.setText(account.getSdt());
        }
        if (account.getDiaChi() != null){
            editTextDiaChi.setText(account.getDiaChi().getDiaChi());

        }
        if (account.getHoTen() != null){
            editTextEmail.setText(account.getEmail());
        }
        if (account.getAvatar() != null && !account.getAvatar().isEmpty()) {
            Picasso.get().load(account.getAvatar()).into(imgAvatar);
        }
        // Thêm TextWatcher cho các EditText
        editTextHoTen.addTextChangedListener(textWatcher);
        editTextSdt.addTextChangedListener(textWatcher);
        editTextEmail.addTextChangedListener(textWatcher);
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
                    showToast("Cập nhật ảnh đại diện thành công!");
                } else {
                    try {
                        // Read the error body to get the actual error message
                        String errorString = response.errorBody().string();
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
    // TextWatcher để kiểm tra sự thay đổi trong EditText
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Không cần thực hiện gì trước khi văn bản thay đổi
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Kích hoạt nút btnSave khi có sự thay đổi trong EditText
            btnSave.setEnabled(isUserInfoChanged());
        }
    };

    // Kiểm tra xem có sự thay đổi nào trong thông tin người dùng so với thông tin hiện tại không
    private boolean isUserInfoChanged() {
        String hoTen = editTextHoTen.getText().toString();
        String sdt = editTextSdt.getText().toString();
        String email = editTextEmail.getText().toString();

        // So sánh với thông tin hiện tại của tài khoản
        return !hoTen.equals(account.getHoTen()) || !sdt.equals(account.getSdt()) || !email.equals(account.getEmail());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserInfo();
    }
}
