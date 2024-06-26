package com.example.skylap_datn_md03.fragment.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.skylap_datn_md03.R;

import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.retrofitController.DanhGiaRetrofit;
import com.example.skylap_datn_md03.retrofitController.DonHangRetrofit;
import com.example.skylap_datn_md03.ui.activities.AccountManagementActivity;
import com.example.skylap_datn_md03.ui.activities.BaoHanhActivity;
import com.example.skylap_datn_md03.ui.activities.ChangePasswordActivity;
import com.example.skylap_datn_md03.ui.activities.ChiTietBaoHanhActivity;
import com.example.skylap_datn_md03.ui.activities.DanhSachBaoHanhActivity;
import com.example.skylap_datn_md03.ui.activities.DanhSachYeuThichActivity;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.ChatRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.ui.activities.DatHangActivity;
import com.example.skylap_datn_md03.ui.activities.GioHangActivity;
import com.example.skylap_datn_md03.ui.activities.KhuyenMaiActivity;
import com.example.skylap_datn_md03.ui.activities.MessageActivity;
import com.example.skylap_datn_md03.ui.activities.QuanLyDanhGiaActivity;
import com.example.skylap_datn_md03.ui.activities.QuanLyDonHangActivity;
import com.example.skylap_datn_md03.ui.activities.SetingActivity;
import com.example.skylap_datn_md03.ui.activities.TrungTamTroGiupActivity;
import com.example.skylap_datn_md03.ui.activities.auth.LoginActivity;
import com.example.skylap_datn_md03.ui.dialogs.CheckDialog;
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
    private TextView tvHoTen,tvSLCXN,tvSLCGH,tvSLDangGH,tvSLCDG;
    private SharedPreferencesManager sharedPreferencesManager;
    private MessagePreferences messagePreferences;
    private RetrofitService retrofitService;
    private TextView txt_numberUnSeen_message_UserFrag;
    private LinearLayout btnDKSD,btnQLDH,btnBaoHanh, btnCXN,btnDMK,btnKM, btnCGH, btnDGH, btnDG, btnQLDG, btnYT, btnTroTruyen, btnDangXuat,btnQLTK;
    private String idChat;
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
        initView(view);
        sharedPreferencesManager = new SharedPreferencesManager(getContext());
        retrofitService = new RetrofitService();
        messagePreferences = new MessagePreferences();

        getUser();
        logicChat();
        laySoLuongDonHang();
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                startActivity(new Intent(getContext(), GioHangActivity.class));
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                getChat();
            }
        });
        btnBaoHanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                startActivity(new Intent(getContext(), DanhSachBaoHanhActivity.class));
            }
        });
        btnTroTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                getChat();
            }
        });
        btnQLDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                startActivity(new Intent(getContext(), QuanLyDonHangActivity.class));
            }
        });
        btnYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                Intent intent = new Intent(getActivity(), DanhSachYeuThichActivity.class);
                startActivity(intent);
            }
        });
        btnQLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                Intent intent = new Intent(getActivity(), AccountManagementActivity.class);
                startActivity(intent);
            }
        });
        btnCXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                startActivity(new Intent(getContext(), QuanLyDonHangActivity.class));
            }
        });
        btnDGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                startActivity(new Intent(getContext(), QuanLyDonHangActivity.class));
            }
        });
        btnCGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                startActivity(new Intent(getContext(), QuanLyDonHangActivity.class));
            }
        });
        btnDG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                startActivity(new Intent(getContext(), QuanLyDanhGiaActivity.class));
            }
        });
        btnQLDG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                startActivity(new Intent(getContext(), QuanLyDanhGiaActivity.class));
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_check);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tvNoiDung = dialog.findViewById(R.id.dal_noiDung);
                TextView tvTieuDe = dialog.findViewById(R.id.dal_tieuDe);
                Button btnHuy = dialog.findViewById(R.id.dal_btnHuy);
                Button btnOk = dialog.findViewById(R.id.dal_btnOk);
                tvTieuDe.setText("Thông báo");
                tvNoiDung.setText("Bạn chắc chắn muốn đăng xuất?");
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
                        huyDangKyTopicFirebase(sharedPreferencesManager.getUserId());
                        sharedPreferencesManager.clearUserId();
                        dialog.dismiss();
                        getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                });
                dialog.show();
            }
        });
        btnKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KhuyenMaiActivity.class);
                intent.putExtra("isDatHang", false);
                startActivity(intent);
            }
        });
        btnDMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        btnDKSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TrungTamTroGiupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        btnDKSD = view.findViewById(R.id.fmu_troGiup);
        tvSLCDG = view.findViewById(R.id.fmu_slCDG);
        btnBaoHanh = view.findViewById(R.id.fmu_baoHanh);
        tvSLCXN = view.findViewById(R.id.fmu_slCXN);
        tvSLCGH = view.findViewById(R.id.fmu_slCGH);
        tvSLDangGH = view.findViewById(R.id.fmu_slDangGN);
        btnChat = view.findViewById(R.id.fmu_chat);
        btnGioHang = view.findViewById(R.id.fmu_gioHang);
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
        btnDMK = view.findViewById(R.id.fmu_dmk);
        btnKM = view.findViewById(R.id.fmu_km);
        btnDangXuat = view.findViewById(R.id.fmu_dangXuat);
        txt_numberUnSeen_message_UserFrag = view.findViewById(R.id.txt_numberUnSeen_message_UserFrag);
    }

    private void laySoLuongDonHang() {
        if (sharedPreferencesManager.getUserId().isEmpty()){
            return;
        }
        String userID = sharedPreferencesManager.getUserId();
        DonHangRetrofit donHangRetrofit = retrofitService.retrofit.create(DonHangRetrofit.class);
        DanhGiaRetrofit danhGiaRetrofit = retrofitService.retrofit.create(DanhGiaRetrofit.class);
        donHangRetrofit.laySLDonHangChoGiaoHang(userID).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int soLuong = response.body();
                if (soLuong == 0 ){
                    tvSLCGH.setVisibility(View.GONE);
                }else{
                    tvSLCGH.setText(soLuong+"");
                    tvSLCGH.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        donHangRetrofit.laySLDonHangChoXacNhan(userID).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int soLuong = response.body();
                if (soLuong == 0 ){
                    tvSLCXN.setVisibility(View.GONE);
                }else{
                    tvSLCXN.setText(soLuong+"");
                    tvSLCXN.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        donHangRetrofit.laySLDonHangDangGiaoHang(userID).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int soLuong = response.body();
                if (soLuong == 0 ){
                    tvSLDangGH.setVisibility(View.GONE);
                }else{
                    tvSLDangGH.setText(soLuong+"");
                    tvSLDangGH.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        danhGiaRetrofit.getSoLuongChuaDanhGia(userID).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int soLuong = response.body();
                if (soLuong == 0 ){
                    tvSLCDG.setVisibility(View.GONE);
                }else{
                    tvSLCDG.setText(soLuong+"");
                    tvSLCDG.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    private void huyDangKyTopicFirebase(String userId) {
        if (sharedPreferencesManager.getUserId().isEmpty()){
            return;
        }
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
        if (sharedPreferencesManager.getUserId().isEmpty()){
            return;
        }
        idChat = "";
        chatRetrofit = retrofitService.retrofit.create(ChatRetrofit.class);
        String userId = sharedPreferencesManager.getUserId();
        Call<String> addChat = chatRetrofit.CreateConverSation(userId);
        addChat.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 206) {
                    idChat = response.body();
                    messagePreferences.checkChat( response.body() ,txt_numberUnSeen_message_UserFrag ,userId);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
    private void getChat(){
        if (sharedPreferencesManager.getUserId().isEmpty()){
            return;
        }
        Intent intent = new Intent(getContext(), MessageActivity.class);
        intent.putExtra("conversation_key", idChat);
        messagePreferences.putSeeNAllwhenOnclick(idChat);
        startActivity(intent);
    }

    private void getUser() {
        if (sharedPreferencesManager.getUserId().isEmpty()){
            CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
            btnDangXuat.setVisibility(View.GONE);
            btnDMK.setVisibility(View.GONE);
            tvHoTen.setText("");
            Picasso.get().load(R.drawable.avatar_main).into(imgAvatar);
            return;
        }
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
                    } else if(response.body().getAvatar().equals("https://cdn-icons-png.flaticon.com/128/3135/3135715.png")){
                        Picasso.get().load(response.body().getAvatar()).into(imgAvatar);
                    }else{
//                        imgAvatar.setRotation(-90);
                        Picasso.get().load(response.body().getAvatar()).into(imgAvatar);
                    }
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getUser();
    }
}