package com.example.skylap_datn_md03.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.DanhGiaAdapter;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.retrofitController.DanhGiaRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachDanhGia extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RetrofitService retrofitService;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_danh_gia);
        imgBack = findViewById(R.id.adsdg_img_back);
        recyclerView = findViewById(R.id.adsdg_rcv_dg);
        retrofitService = new RetrofitService();
        String id = getIntent().getStringExtra("idSP");
        DanhGiaRetrofit danhGiaRetrofit = retrofitService.retrofit.create(DanhGiaRetrofit.class);
        danhGiaRetrofit.getDaDanhGiaTheoSanPham(id).enqueue(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                DanhGiaAdapter danhGiaAdapter = new DanhGiaAdapter(response.body(),DanhSachDanhGia.this);
                danhGiaAdapter.reverseList();
                recyclerView.setAdapter(danhGiaAdapter);
            }
            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}