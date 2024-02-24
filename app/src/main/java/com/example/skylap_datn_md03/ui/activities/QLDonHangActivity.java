package com.example.skylap_datn_md03.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.ViewPaperQLDHAdaper;
import com.example.skylap_datn_md03.fragment.DaGiaoDonHangFragment;
import com.example.skylap_datn_md03.fragment.DangGiaoDonHangFragment;
import com.example.skylap_datn_md03.fragment.HuyDonHangFragment;
import com.example.skylap_datn_md03.fragment.XacNhanDonHangFragment;

public class QLDonHangActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qldon_hang);

        viewPager = findViewById(R.id.viewPager);



        ViewPaperQLDHAdaper viewPaperQLDHAdaper = new ViewPaperQLDHAdaper(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPaperQLDHAdaper.addFragment(new XacNhanDonHangFragment(), "A");
        viewPaperQLDHAdaper.addFragment(new DangGiaoDonHangFragment(), "B");
        viewPaperQLDHAdaper.addFragment(new DaGiaoDonHangFragment(), "C");
        viewPaperQLDHAdaper.addFragment(new HuyDonHangFragment(), "D");
        viewPager.setAdapter(viewPaperQLDHAdaper);
    }
}