package com.example.skylap_datn_md03.fragment.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.retrofitController.ChatRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.ui.activities.GioHangActivity;
import com.example.skylap_datn_md03.ui.activities.MessageActivity;
import com.example.skylap_datn_md03.ui.activities.QuanLyDanhGiaActivity;
import com.example.skylap_datn_md03.ui.activities.QuanLyDonHangActivity;
import com.example.skylap_datn_md03.ui.activities.SetingActivity;
import com.example.skylap_datn_md03.ui.activities.auth.LoginActivity;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment {
    private ImageView btnCaiDat, btnGioHang;
    private FrameLayout btnChat;
    private ChatRetrofit chatRetrofit;
    private SharedPreferencesManager sharedPreferencesManager;
    private RetrofitService retrofitService;
    private LinearLayout btnQLDH, btnCXN, btnCGH, btnDGH, btnDG, btnQLDG, btnTroTruyen, btnDangXuat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnChat = view.findViewById(R.id.fmu_chat);
        btnGioHang = view.findViewById(R.id.fmu_gioHang);
        btnCaiDat = view.findViewById(R.id.fmu_setting);
        btnQLDH = view.findViewById(R.id.fmu_qldh);
        btnCXN = view.findViewById(R.id.fmu_cxn);
        btnCGH = view.findViewById(R.id.fmu_cgh);
        btnDGH = view.findViewById(R.id.fmu_dgh);
        btnDG = view.findViewById(R.id.fmu_dg);
        btnQLDG = view.findViewById(R.id.fmu_qldg);
        btnTroTruyen = view.findViewById(R.id.fmu_troTruyen);
        btnDangXuat = view.findViewById(R.id.fmu_dangXuat);
        sharedPreferencesManager = new SharedPreferencesManager(getContext());
        retrofitService = new RetrofitService();

        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GioHangActivity.class));
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logicChat();
            }
        });
        btnTroTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logicChat();
            }
        });
        btnQLDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuanLyDonHangActivity.class));
            }
        });
        btnCXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuanLyDonHangActivity.class));
            }
        });
        btnDGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuanLyDonHangActivity.class));
            }
        });
        btnCGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuanLyDonHangActivity.class));
            }
        });
        btnDG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuanLyDanhGiaActivity.class));
            }
        });
        btnQLDG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuanLyDanhGiaActivity.class));
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
                sharedPreferencesManager.clearUserId();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }

    private void logicChat() {
        chatRetrofit = retrofitService.retrofit.create(ChatRetrofit.class);
        String userId = sharedPreferencesManager.getUserId();
        Call<String> addChat = chatRetrofit.CreateConverSation(userId);
        addChat.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 206) {
                    Intent intent = new Intent(getContext(), MessageActivity.class);
                    intent.putExtra("conversation_key", response.body());
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}