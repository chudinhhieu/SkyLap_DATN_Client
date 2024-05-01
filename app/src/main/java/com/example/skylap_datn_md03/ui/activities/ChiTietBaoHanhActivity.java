package com.example.skylap_datn_md03.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.ImageAdapter;
import com.example.skylap_datn_md03.adapter.ImageAdapter2;
import com.example.skylap_datn_md03.data.models.BaoHanh;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.ChatRetrofit;
import com.example.skylap_datn_md03.retrofitController.DonHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.utils.MessagePreferences;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietBaoHanhActivity extends AppCompatActivity {
    private RetrofitService retrofitService;
    private DonHangRetrofit donHangRetrofit;
    private SanPhamRetrofit sanPhamRetrofit;
    private ImageView imgBack, imgSP;
    private ChatRetrofit chatRetrofit;
    private TextView tvTenSP, tvEmei, tvLDBH, tvTime, tvPhanHoi, tvTinhTrang;
    private Button button;
    private RecyclerView recyclerView;
    private ImageAdapter2 imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bao_hanh);
        String id = getIntent().getStringExtra("idbh");
        imgBack = findViewById(R.id.actbh_img_back);
        imgSP = findViewById(R.id.actbh_anhSP);
        tvTenSP = findViewById(R.id.actbh_tenSP);
        tvEmei = findViewById(R.id.actbh_imei);
        tvLDBH = findViewById(R.id.tv_ldbh);
        tvTime = findViewById(R.id.tv_time);
        tvPhanHoi = findViewById(R.id.tv_phch);
        tvTinhTrang = findViewById(R.id.tv_tt);
        button = findViewById(R.id.actbh_btn);
        recyclerView = findViewById(R.id.rcv_anhBH);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        retrofitService = new RetrofitService();
        donHangRetrofit = retrofitService.retrofit.create(DonHangRetrofit.class);
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(ChiTietBaoHanhActivity.this);
                chatRetrofit = retrofitService.retrofit.create(ChatRetrofit.class);
                String userId = sharedPreferencesManager.getUserId();
                Call<String> addChat = chatRetrofit.CreateConverSation(userId);
                addChat.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code() == 206) {
                            Intent intent = new Intent(ChiTietBaoHanhActivity.this, MessageActivity.class);
                            intent.putExtra("conversation_key", response.body());
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
        donHangRetrofit.layBaoHanhTheoID(id).enqueue(new Callback<BaoHanh>() {
            @Override
            public void onResponse(Call<BaoHanh> call, Response<BaoHanh> response) {
                BaoHanh baoHanh = response.body();
                sanPhamRetrofit.getSanPhamByID(baoHanh.getIdSanPham()).enqueue(new Callback<SanPham>() {
                    @Override
                    public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                        tvTenSP.setText(response.body().getTenSanPham());
                        Picasso.get().load(response.body().getAnhSanPham()).into(imgSP);

                    }

                    @Override
                    public void onFailure(Call<SanPham> call, Throwable t) {

                    }
                });
                imageAdapter = new ImageAdapter2(baoHanh.getAnh(), ChiTietBaoHanhActivity.this);
                recyclerView.setAdapter(imageAdapter);
                tvEmei.setText(baoHanh.getImei());
                tvLDBH.setText(baoHanh.getLyDo());
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
                tvTime.setText(dateFormat.format(baoHanh.getThoiGian()));
                if (baoHanh.getLyDoTuChoi() == null) {
                    tvPhanHoi.setText("Chờ phản hồi từ cửa hàng!");
                } else {
                    tvPhanHoi.setText(baoHanh.getLyDoTuChoi());
                }
                switch (baoHanh.getTinhTrang()) {
                    case 1:
                        tvTinhTrang.setText("Chờ xác nhận");
                        tvTinhTrang.setTextColor(getResources().getColor(R.color.main)); // Màu blue
                        break;
                    case 2:
                        tvTinhTrang.setText("Đồng ý");
                        tvTinhTrang.setTextColor(getResources().getColor(R.color.green)); // Màu green
                        break;
                    case 3:
                        tvTinhTrang.setText("Từ chối");
                        tvTinhTrang.setTextColor(getResources().getColor(R.color.red)); // Màu red
                        break;
                }

            }

            @Override
            public void onFailure(Call<BaoHanh> call, Throwable t) {

            }
        });
    }

}