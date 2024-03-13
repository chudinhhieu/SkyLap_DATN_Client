package com.example.skylap_datn_md03.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.skylap_datn_md03.fragment.donhang.ChoGiaoHangFragment;
import com.example.skylap_datn_md03.fragment.donhang.ChoXacNhanFragment;
import com.example.skylap_datn_md03.fragment.donhang.DaGiaoHangFragment;
import com.example.skylap_datn_md03.fragment.donhang.DaHuyFragment;

public class ViewPagerQLDHAdapter extends FragmentPagerAdapter {

    public ViewPagerQLDHAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ChoXacNhanFragment();
            case 1:
                return new ChoGiaoHangFragment();
            case 2:
                return new DaGiaoHangFragment();
            case 3:
                return new DaHuyFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position){
            case 0:
                title = "Chờ xác nhận";
                break;
            case 1:
                title = "Chờ giao hàng";
                break;
            case 2:
                title = "Đã giao hàng";
                break;
            case 3:
                title = "Đã hủy";
                break;
        }
        return  title;
    }
}
