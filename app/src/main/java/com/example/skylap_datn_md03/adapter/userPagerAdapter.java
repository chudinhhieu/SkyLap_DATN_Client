package com.example.skylap_datn_md03.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.skylap_datn_md03.fragment.fragment_user_pageProfile;
import com.example.skylap_datn_md03.fragment.fragment_user_pageService;

public class userPagerAdapter extends FragmentStateAdapter {
    public userPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new fragment_user_pageService();
            case 1:
                return new fragment_user_pageProfile();
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
