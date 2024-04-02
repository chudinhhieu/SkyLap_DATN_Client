package com.example.skylap_datn_md03.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.KhuyenMai;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ChiTietKhuyenMaiActivity extends AppCompatActivity {
    private KhuyenMai khuyenMai;
    private ImageView btnBack;
    private ImageView imgChiTietKhuyenMai;
    private TextView tvMoTaChiTiet, tvCodeChiTiet, tvThoiGianBatDau, tvThoiGianKetThuc,
                    tvSoLuong, tvSoTienGiam, tvTrangThai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_khuyen_mai);

        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("khuyenMai")){
                khuyenMai = (KhuyenMai) intent.getSerializableExtra("khuyenMai");
            }
        }
        initView();
        fillDataToView(khuyenMai);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void initView(){
        btnBack = findViewById(R.id.act_km_img_back);
        imgChiTietKhuyenMai = findViewById(R.id.imgChiTietKhuyenMai);
        tvMoTaChiTiet = findViewById(R.id.tvMoTaChiTiet);
        tvCodeChiTiet = findViewById(R.id.tvCodeChiTiet);
        tvThoiGianBatDau = findViewById(R.id.tvThoiGianBatDau);
        tvThoiGianKetThuc = findViewById(R.id.tvThoiGianKetThuc);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        tvSoTienGiam = findViewById(R.id.tvSoTienGiam);
        tvTrangThai = findViewById(R.id.tvTrangThai);
    }

    private void fillDataToView(KhuyenMai khuyenMai){
        if (khuyenMai != null){
            tvMoTaChiTiet.setText(khuyenMai.getMoTa());
            tvCodeChiTiet.setText(khuyenMai.getCode());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


            tvThoiGianBatDau.setText(dateFormat.format(khuyenMai.getThoiGianBatDau()));
            tvThoiGianKetThuc.setText(dateFormat.format(khuyenMai.getThoiGianKetThuc()));
            tvSoLuong.setText(String.valueOf(khuyenMai.getSoLuong()));
            tvSoTienGiam.setText(String.valueOf(khuyenMai.getSoTienGiam()));

            String trangThai = khuyenMai.isTrangThai() ? "true" : "false";
            tvTrangThai.setText(trangThai);

            Picasso.get().load(khuyenMai.getAnh()).into(imgChiTietKhuyenMai);

        }
    }
}