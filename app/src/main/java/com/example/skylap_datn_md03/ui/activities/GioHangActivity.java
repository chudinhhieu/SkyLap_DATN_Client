package com.example.skylap_datn_md03.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.GioHangAdapter;
import com.example.skylap_datn_md03.data.models.GioHang;

import java.util.ArrayList;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    private RecyclerView agh_recyclerView;
    private GioHangAdapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        agh_recyclerView = findViewById(R.id.agh_recyclerView);
        agh_recyclerView.setLayoutManager(new LinearLayoutManager(this));


        List<GioHang> list = getDummyData();

        adapter = new GioHangAdapter(list , getApplicationContext());
        agh_recyclerView.setAdapter(adapter);

    }
    private List<GioHang> getDummyData(){
        List<GioHang> gioHangList = new ArrayList<>();

        for (int i = 0; i< 10; i++){
            GioHang gioHang = new GioHang();
            List<String> listAnh = new ArrayList<>();
            listAnh.add(new String("https://cdn.tgdd.vn/Products/Images/44/231244/macbook-air-m1-2020-gold-600x600.jpg"));
//            gioHang.setAnhSanPham(listAnh);
//            gioHang.setTenSanPham("Macbook Pro");
//            gioHang.setGiaSanPham(2000000000);
            gioHang.setSoLuong(99);

            gioHangList.add(gioHang);
        }
        return gioHangList;
    }
}