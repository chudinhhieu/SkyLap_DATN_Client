package com.example.skylap_datn_md03.fragment.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.skylap_datn_md03.R;

import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.ui.activities.DanhSachYeuThichActivity;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.ChatRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.ui.activities.GioHangActivity;
import com.example.skylap_datn_md03.ui.activities.MessageActivity;
import com.example.skylap_datn_md03.ui.activities.QuanLyDanhGiaActivity;
import com.example.skylap_datn_md03.ui.activities.QuanLyDonHangActivity;
import com.example.skylap_datn_md03.ui.activities.auth.LoginActivity;
import com.example.skylap_datn_md03.utils.MessagePreferences;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment {
    public static final String TAG = UserFragment.class.getName();
    private ImageView btnCaiDat, btnGioHang;
    private FrameLayout btnChat;
    private ChatRetrofit chatRetrofit;
    private ImageView imgAvatar;
    private TextView tvHoTen;
    private SharedPreferencesManager sharedPreferencesManager;
    private MessagePreferences messagePreferences;
    private RetrofitService retrofitService;
    private TextView txt_numberUnSeen_message_UserFrag;
    private LinearLayout btnQLDH, btnCXN, btnCGH, btnDGH, btnDG, btnQLDG, btnYT, btnTroTruyen, btnDangXuat;
    private String idChat;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        LinearLayout quanLyDanhGia = view.findViewById(R.id.fmu_qldg);
        quanLyDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuanLyDanhGiaActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnChat = view.findViewById(R.id.fmu_chat);
        btnGioHang = view.findViewById(R.id.fmu_gioHang);
        btnCaiDat = view.findViewById(R.id.fmu_setting);
        imgAvatar = view.findViewById(R.id.fmu_avatar);
        tvHoTen = view.findViewById(R.id.fmu_hoTen);
        btnQLDH = view.findViewById(R.id.fmu_qldh);
        btnYT = view.findViewById(R.id.fmu_yt);
        btnQLTK = view.findViewById(R.id.fmu_taiKhoan);
        btnCXN = view.findViewById(R.id.fmu_cxn);
        btnCGH = view.findViewById(R.id.fmu_cgh);
        btnDGH = view.findViewById(R.id.fmu_dgh);
        btnDG = view.findViewById(R.id.fmu_dg);
        btnQLDG = view.findViewById(R.id.fmu_qldg);
        btnTroTruyen = view.findViewById(R.id.fmu_troTruyen);
        btnDangXuat = view.findViewById(R.id.fmu_dangXuat);
        txt_numberUnSeen_message_UserFrag = view.findViewById(R.id.txt_numberUnSeen_message_UserFrag);
        sharedPreferencesManager = new SharedPreferencesManager(getContext());
        retrofitService = new RetrofitService();
        messagePreferences = new MessagePreferences();
        getUser();
        logicChat();
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GioHangActivity.class));
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChat();
            }
        });
        btnTroTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChat();
            }
        });
        btnQLDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuanLyDonHangActivity.class));
            }
        });
        btnYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachYeuThichActivity.class);
                startActivity(intent);
            }
        });
        btnQLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccountManagementActivity.class);
                startActivity(intent);
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
                huyDangKyTopicFirebase(sharedPreferencesManager.getUserId());
                sharedPreferencesManager.clearUserId();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

    }

    private void huyDangKyTopicFirebase(String userId) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(userId)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Unsubscribed";
                        if (!task.isSuccessful()) {
                            msg = "Unsubscribe failed";
                        }
                        Log.d(TAG, msg);
                    }
                });

    }

    private void logicChat() {
        idChat = "";
        chatRetrofit = retrofitService.retrofit.create(ChatRetrofit.class);
        String userId = sharedPreferencesManager.getUserId();
        Call<String> addChat = chatRetrofit.CreateConverSation(userId);
        addChat.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 206) {
                    idChat = response.body();

                    messagePreferences.checkChat( response.body() ,txt_numberUnSeen_message_UserFrag , getContext() );

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
    private void getChat(){
        Intent intent = new Intent(getContext(), MessageActivity.class);
        intent.putExtra("conversation_key", idChat);
        messagePreferences.putSeeNAllwhenOnclick(idChat);
        startActivity(intent);
    }

    private void getUser() {
        AccountRetrofit accountRetrofit = retrofitService.retrofit.create(AccountRetrofit.class);

        accountRetrofit.getAccountById(sharedPreferencesManager.getUserId()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String hoTen = response.body().getHoTen();
                    String userName = response.body().getTaiKhoan();
                    if (hoTen == null){
                        tvHoTen.setText(userName);
                    }else {
                        tvHoTen.setText(hoTen);
                    }
                    if (response.body().getAvatar() == null) {
                        Picasso.get().load(R.drawable.avatar_main).into(imgAvatar);
                    } else {
                        Picasso.get().load(response.body().getAvatar()).into(imgAvatar);
                    }
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
            }
        });
    }


}