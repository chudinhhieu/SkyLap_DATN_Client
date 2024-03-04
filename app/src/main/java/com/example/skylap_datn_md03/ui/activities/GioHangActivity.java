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





        agh_recyclerView.setAdapter(adapter);

    }

}