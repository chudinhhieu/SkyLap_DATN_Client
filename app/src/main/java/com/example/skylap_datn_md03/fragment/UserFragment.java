package com.example.skylap_datn_md03.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.ui.activities.DanhGiaActivity;
import com.example.skylap_datn_md03.ui.activities.QuanLyDanhGiaActivity;
import com.example.skylap_datn_md03.ui.activities.SetingActivity;


public class UserFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // You can initialize the btn_dangXuat here if needed
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        ImageView img_seting = view.findViewById(R.id.user_img_seting);
        TextView tv_danhgia  = view.findViewById(R.id.user_tv_danhgia);

        img_seting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent( getContext(), SetingActivity.class));
            }
        });
        tv_danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getContext(), QuanLyDanhGiaActivity.class));
            }
        });

        return view;
    }
}