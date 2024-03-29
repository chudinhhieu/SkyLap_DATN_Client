package com.example.skylap_datn_md03.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.skylap_datn_md03.fragment.danhgia.ChuaDanhGiaFragment;
import com.example.skylap_datn_md03.fragment.danhgia.DanhGiaFragment;

public class DanhGiaTabsAdapter extends FragmentStateAdapter {

    public DanhGiaTabsAdapter(FragmentActivity fa) {
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {
        // Return a new instance of your fragment based on the position
        switch (position) {
            case 0:
                return new DanhGiaFragment();
            default:
                return new ChuaDanhGiaFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
