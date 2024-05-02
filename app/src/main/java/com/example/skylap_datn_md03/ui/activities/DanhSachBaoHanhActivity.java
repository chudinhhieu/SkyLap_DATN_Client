package com.example.skylap_datn_md03.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.BaoHanhAdapter;
import com.example.skylap_datn_md03.adapter.SanPhamYeuThichAdapter;
import com.example.skylap_datn_md03.data.models.BaoHanh;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.data.models.SanPhamYeuThich;
import com.example.skylap_datn_md03.retrofitController.DonHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.retrofitController.SanPhamYTRetrofit;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachBaoHanhActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView btnBack;
    private BaoHanhAdapter baoHanhAdapter;
    private SharedPreferencesManager sharedPreferencesManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_bao_hanh);
        recyclerView = findViewById(R.id.rcvBH);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        String userId = sharedPreferencesManager.getUserId();
        btnBack = findViewById(R.id.adsbh_img_back);

        getList(userId);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getList(String idAccount) {
        RetrofitService retrofitService = new RetrofitService();
        DonHangRetrofit donHangRetrofit = retrofitService.getRetrofit().create(DonHangRetrofit.class);
        donHangRetrofit.layBaoHanhTheoAccount(idAccount).enqueue(new Callback<List<BaoHanh>>() {
            @Override
            public void onResponse(Call<List<BaoHanh>> call, Response<List<BaoHanh>> response) {
               List<BaoHanh> list = response.body();
               baoHanhAdapter = new BaoHanhAdapter(list,DanhSachBaoHanhActivity.this);
               baoHanhAdapter.reverseList();
               recyclerView.setAdapter(baoHanhAdapter);
            }

            @Override
            public void onFailure(Call<List<BaoHanh>> call, Throwable t) {
                Toast.makeText(DanhSachBaoHanhActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
