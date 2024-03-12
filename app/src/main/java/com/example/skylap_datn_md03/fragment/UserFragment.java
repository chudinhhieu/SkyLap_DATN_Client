package com.example.skylap_datn_md03.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.userPagerAdapter;
import com.example.skylap_datn_md03.ui.activities.SetingActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class UserFragment extends Fragment {
    TabLayout frament_user_tabLayout;
    ViewPager2 frament_user_ViewPager;
    userPagerAdapter pagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // You can initialize the btn_dangXuat here if needed
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

            /////////// init ui /////////////////////
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        frament_user_tabLayout = view.findViewById(R.id.frament_user_tabLayout);
        frament_user_ViewPager = view.findViewById(R.id.frament_user_ViewPager);


        /////////////////////////////////////////////

        pagerAdapter = new userPagerAdapter((FragmentActivity) view.getContext());
        frament_user_ViewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(frament_user_tabLayout, frament_user_ViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int i) {
            switch (i){
                case 0:
                    tab.setText("Service");

                    break;

                case 1:
                    tab.setText("Profile");
                    break;
            }
            }
        }).attach();



        return view;
    }



}