package com.example.skylap_datn_md03.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.KhuyenMaiAdapter;
import com.example.skylap_datn_md03.data.models.KhuyenMai;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.KhuyenMaiRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.utils.DateUtils;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhuyenMaiActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private KhuyenMaiAdapter adapter;
    private ImageView btnBack;
    private Boolean isDatHang;
    private List<KhuyenMai> list;
    SharedPreferencesManager sharedPreferencesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khuyen_mai);
        btnBack = findViewById(R.id.akm_img_back);
        recyclerView = findViewById(R.id.akm_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        isDatHang = getIntent().getBooleanExtra("isDatHang", true);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getKhuyenMaiList();

    }

    private void getKhuyenMaiList() {
        RetrofitService retrofitService = new RetrofitService();
        KhuyenMaiRetrofit khuyenMaiRetrofit = retrofitService.retrofit.create(KhuyenMaiRetrofit.class);
        Call<List<KhuyenMai>> getKhuyenMai = khuyenMaiRetrofit.getListKhuyenMai();
        getKhuyenMai.enqueue(new Callback<List<KhuyenMai>>() {
            @Override
            public void onResponse(Call<List<KhuyenMai>> call, Response<List<KhuyenMai>> response) {
                if (response.isSuccessful()) {
                    List<KhuyenMai> khuyenMaiList = response.body();
                    for (int i = 0; i < khuyenMaiList.size(); i++) {
                        int conLai = DateUtils.getDaysDifference(khuyenMaiList.get(i).getThoiGianKetThuc());
                        if(conLai > 0){
                            list.add(khuyenMaiList.get(i));
                        }
                    }
                    displayKhuyenMaiList(list);
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<KhuyenMai>> call, Throwable t) {
                // Xử lý lỗi khi không thể kết nối với máy chủ
            }
        });
    }

    private void displayKhuyenMaiList(List<KhuyenMai> khuyenMaiList) {
        adapter = new KhuyenMaiAdapter(khuyenMaiList, this,isDatHang);
        adapter.setOnKhuyenMaiClickListener(new KhuyenMaiAdapter.OnKhuyenMaiClickListener() {
            @Override
            public void onKhuyenMaiClick(KhuyenMai khuyenMai) {
                Intent intent = new Intent();
                intent.putExtra("KhuyenMaiObject", khuyenMai);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        adapter.reverseList();
        recyclerView.setAdapter(adapter);
    }

}