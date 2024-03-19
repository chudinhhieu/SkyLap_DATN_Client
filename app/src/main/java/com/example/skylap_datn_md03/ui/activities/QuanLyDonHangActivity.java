package com.example.skylap_datn_md03.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.skylap_datn_md03.MainActivity;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.ViewPagerQLDHAdapter;
import com.google.android.material.tabs.TabLayout;

public class QuanLyDonHangActivity extends AppCompatActivity {
    private ImageView btnExit;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_hang);
        viewPager = findViewById(R.id.viewPager);
        btnExit = findViewById(R.id.acqldh_img_back);
        tabLayout = findViewById(R.id.tabLayout);

        ViewPagerQLDHAdapter adapter = new ViewPagerQLDHAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}