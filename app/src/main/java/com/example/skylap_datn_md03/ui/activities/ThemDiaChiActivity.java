package com.example.skylap_datn_md03.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.data.models.DVHCVN.Huyen;
import com.example.skylap_datn_md03.data.models.DVHCVN.Tinh;
import com.example.skylap_datn_md03.data.models.DVHCVN.Xa;
import com.example.skylap_datn_md03.data.models.DiaChi;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.DiaChiHCVNInterface;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemDiaChiActivity extends AppCompatActivity {
    private RetrofitService retrofitService;
    Account account;
    private DiaChiHCVNInterface diaChiHCVNInterface;
    private List<Tinh> tinhList;
    private List<Huyen> huyenList;
    private List<Xa> xaList;
    private Spinner spnTinh, spnHuyen, spnXa;
    private EditText ipSDT, ipDuong;
    private Button button;
    private ImageView btnExit;
    private TextView tvDiaChi;
    private LinearLayout viewSDT;
    private String idTinh;
    private String idHuyen;
    private String tenTinh;
    private String tenHuyen;
    private String tenXa;
    private AccountRetrofit accountRetrofit;
    private SharedPreferencesManager sharedPreferencesManager;
    private String tenDuong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_dia_chi);
        spnTinh = findViewById(R.id.spinner_level1);
        spnHuyen = findViewById(R.id.spinner_level2);
        spnXa = findViewById(R.id.spinner_level3);
        tvDiaChi = findViewById(R.id.atdc_tvDiaChi);
        btnExit = findViewById(R.id.atdc_img_back);
        ipDuong = findViewById(R.id.atdc_ipDuong);
        ipSDT = findViewById(R.id.atdc_ipSdt);
        button = findViewById(R.id.atdc_btn);
        viewSDT = findViewById(R.id.view_sdt);
        retrofitService = new RetrofitService();
        sharedPreferencesManager = new SharedPreferencesManager(this);
        diaChiHCVNInterface = retrofitService.retrofit.create(DiaChiHCVNInterface.class);
        accountRetrofit = retrofitService.retrofit.create(AccountRetrofit.class);
        getUser();

        layDanhSachTinh();
        spnTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                tenTinh = spnTinh.getSelectedItem().toString();
                for (Tinh tinh : tinhList) {
                    if (tinh.getName().equals(tenTinh)) {
                        idTinh = tinh.getId();
                        layDanhSachHuyen();
                        updateTextViewDiaChi();
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spnHuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                tenHuyen = spnHuyen.getSelectedItem().toString();
                for (Huyen huyen : huyenList) {
                    if (huyen.getName().equals(tenHuyen)) {
                        idHuyen = huyen.getId();
                        layDanhSachXa();
                        updateTextViewDiaChi();
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spnXa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                tenXa = spnXa.getSelectedItem().toString();
                updateTextViewDiaChi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (account.getSdt() == null) {
                    updateSDT();
                    updateDiaChi();
                } else {
                    updateDiaChi();
                }
            }
        });
        ipDuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 updateTextViewDiaChi();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateDiaChi() {
        DiaChi diaChi = new DiaChi(idTinh,tvDiaChi.getText().toString());
        accountRetrofit.themDiaChi(sharedPreferencesManager.getUserId(),diaChi).enqueue(new Callback<MyAuth>() {
            @Override
            public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
                finish();
            }

            @Override
            public void onFailure(Call<MyAuth> call, Throwable t) {
            }
        });
    }

    private void updateSDT() {
        accountRetrofit.themSDT(new Account(ipSDT.getText().toString().trim()), sharedPreferencesManager.getUserId()).enqueue(new Callback<MyAuth>() {
            @Override
            public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
            }

            @Override
            public void onFailure(Call<MyAuth> call, Throwable t) {
            }
        });
    }

    private void layDanhSachXa() {
        diaChiHCVNInterface.getLevel3OfLevel2(idTinh, idHuyen).enqueue(new Callback<List<Xa>>() {
            @Override
            public void onResponse(Call<List<Xa>> call, Response<List<Xa>> response) {
                xaList = response.body();
                ArrayList<String> xaNames = new ArrayList<>();
                for (Xa xa : xaList) {
                    xaNames.add(xa.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ThemDiaChiActivity.this, android.R.layout.simple_spinner_item, xaNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnXa.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Xa>> call, Throwable t) {
            }
        });
    }

    private void layDanhSachHuyen() {
        diaChiHCVNInterface.getLevel2OfLevel1(idTinh).enqueue(new Callback<List<Huyen>>() {
            @Override
            public void onResponse(Call<List<Huyen>> call, Response<List<Huyen>> response) {
                huyenList = response.body();
                ArrayList<String> huyenNames = new ArrayList<>();
                for (Huyen huyen : huyenList) {
                    huyenNames.add(huyen.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ThemDiaChiActivity.this, android.R.layout.simple_spinner_item, huyenNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnHuyen.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Huyen>> call, Throwable t) {
            }
        });
    }


    private void layDanhSachTinh() {
        diaChiHCVNInterface.getAllLevel1().enqueue(new Callback<List<Tinh>>() {
            @Override
            public void onResponse(Call<List<Tinh>> call, Response<List<Tinh>> response) {
                tinhList = response.body();
                ArrayList<String> tinhNames = new ArrayList<>();
                for (Tinh tinh : tinhList) {
                    tinhNames.add(tinh.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ThemDiaChiActivity.this, android.R.layout.simple_spinner_item, tinhNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnTinh.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Tinh>> call, Throwable t) {

            }
        });
    }

    private void updateTextViewDiaChi() {
        tenDuong = ipDuong.getText().toString().trim().isEmpty() ? "" : ipDuong.getText().toString().trim() + ", ";
        tvDiaChi.setText(tenDuong + tenXa + ", " + tenHuyen + ", " + tenTinh);
    }

    private void getUser() {
        accountRetrofit.getAccountById(sharedPreferencesManager.getUserId()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                account = response.body();
                if (!(account.getSdt() == null)) {
                    viewSDT.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });
    }
}