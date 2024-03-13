package com.example.skylap_datn_md03.fragment.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.skylap_datn_md03.R;
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

        img_seting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent( getContext(), SetingActivity.class));
            }
        });
        return view;
    }
}