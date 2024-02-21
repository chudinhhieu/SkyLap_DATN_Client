package com.example.skylap_datn_md03.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.skylap_datn_md03.R;

public class SanPhamActivity extends AppCompatActivity {
    TextView asp_tv_giagoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        asp_tv_giagoc = findViewById(R.id.asp_tv_giagoc);

//        Gạch giữa textview
        asp_tv_giagoc.setPaintFlags(asp_tv_giagoc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
}