package com.example.skylap_datn_md03.fragment.home;

import static android.util.Log.d;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.HangSanPhamAdapter;
import com.example.skylap_datn_md03.adapter.QuanLyDonHangAdapter;
import com.example.skylap_datn_md03.adapter.SanPhamAdapter;
import com.example.skylap_datn_md03.adapter.SlideAdapter;
import com.example.skylap_datn_md03.adapter.messageAdapter;
import com.example.skylap_datn_md03.data.models.HangSX;
import com.example.skylap_datn_md03.data.models.KhuyenMai;
import com.example.skylap_datn_md03.data.models.Message;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.ChatRetrofit;
import com.example.skylap_datn_md03.retrofitController.GioHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.HangSxInterface;
import com.example.skylap_datn_md03.retrofitController.KhuyenMaiRetrofit;
import com.example.skylap_datn_md03.retrofitController.MessageRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.activities.GioHangActivity;
import com.example.skylap_datn_md03.ui.activities.MessageActivity;
import com.example.skylap_datn_md03.ui.dialogs.CheckDialog;
import com.example.skylap_datn_md03.utils.MessagePreferences;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private ViewPager slidePager;
    private RecyclerView rcvHangSx, rcvSanPham;
    private HangSanPhamAdapter hangSxAdapter;
    private SanPhamAdapter sanPhamAdapter;
    private SlideAdapter slideAdapter;
    private View context;
    private Timer mTimer;
    private List<KhuyenMai> list;
    private SanPhamRetrofit sanPhamRetrofit;
    private LinearLayout btn_chat;
    private SharedPreferencesManager sharedPreferencesManager;
    private  MessagePreferences messagePreferences;
    private ChatRetrofit chatRetrofit;
    private RetrofitService retrofitService;
    private RelativeLayout btnGioHang,viewNull;
    private TextView txtNumberUnSeenMessage;
    private EditText txtSearrch;
    private String idChat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = inflater.inflate(R.layout.fragment_home, container, false);
        retrofitService = new RetrofitService();
        sharedPreferencesManager = new SharedPreferencesManager(context.getContext());
        messagePreferences = new MessagePreferences();
        initUI();

        txtSearrch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtSearrch.getText().toString().trim().equals("")){
                    return;
                }

                sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
                sanPhamRetrofit.getsearch( new SanPham(txtSearrch.getText().toString())).enqueue(new Callback<List<SanPham>>() {
                    @Override
                    public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                        List<SanPham> newData = response.body();
                        if (newData == null){
                            viewNull.setVisibility(View.VISIBLE);
                            rcvSanPham.setVisibility(View.GONE);
                            return;
                        }
                        if (newData.isEmpty() ) {
                            viewNull.setVisibility(View.VISIBLE);
                            rcvSanPham.setVisibility(View.GONE);
                        } else {
                            viewNull.setVisibility(View.GONE);
                            rcvSanPham.setVisibility(View.VISIBLE);
                            sanPhamAdapter.setList(newData);
                            rcvSanPham.setAdapter(sanPhamAdapter);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<SanPham>> call, Throwable t) {

                    }
                });


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        logicChat();
        getListSanPham();
        getListHangSx();
        getListKhuyenMai();
        /// tạo chat luôn khi mới vào màn hình ////
/////chạy sang message activi và set dã xem toàn bộ mess có id chat ////
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()){
                    CheckDialog.showCheckDialog(getContext(), "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                getChat();
            }
        });

        return context;
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
                    messagePreferences.checkChat( response.body() ,txtNumberUnSeenMessage  , userId);
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
        Intent intent = new Intent(context.getContext(), MessageActivity.class);
        intent.putExtra("conversation_key", idChat);
        messagePreferences.putSeeNAllwhenOnclick(idChat);
        startActivity(intent);
    }

    private void getListSanPham() {
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        Call<List<SanPham>> getSp = sanPhamRetrofit.getListSanPham();
        getSp.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                List<SanPham> newData = response.body();
                if (newData == null){
                    viewNull.setVisibility(View.VISIBLE);
                    rcvSanPham.setVisibility(View.GONE);
                    return;
                }
                if (newData.isEmpty() ) {
                    viewNull.setVisibility(View.VISIBLE);
                    rcvSanPham.setVisibility(View.GONE);
                } else {
                    viewNull.setVisibility(View.GONE);
                    rcvSanPham.setVisibility(View.VISIBLE);
                    sanPhamAdapter.setList(newData);
                    rcvSanPham.setAdapter(sanPhamAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
            }
        });
    }

    private void getListKhuyenMai() {
        KhuyenMaiRetrofit khuyenMaiRetrofit = retrofitService.retrofit.create(KhuyenMaiRetrofit.class);
        Call<List<KhuyenMai>> getList = khuyenMaiRetrofit.getListKhuyenMai();
        getList.enqueue(new Callback<List<KhuyenMai>>() {
            @Override
            public void onResponse(Call<List<KhuyenMai>> call, Response<List<KhuyenMai>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    slideAdapter.setList(list);
                    slidePager.setAdapter(slideAdapter);
                    autoSlideImage(list);
                } else {
                    // Xử lý khi có lỗi từ server
                }
            }

            @Override
            public void onFailure(Call<List<KhuyenMai>> call, Throwable t) {

            }
        });

    }

    private void getListHangSx() {

        HangSxInterface hangSxInterface = retrofitService.retrofit.create(HangSxInterface.class);
        Call<List<HangSX>> getList = hangSxInterface.getListHangSx();
        getList.enqueue(new Callback<List<HangSX>>() {
            @Override
            public void onResponse(Call<List<HangSX>> call, Response<List<HangSX>> response) {
                if (response.isSuccessful()) {
                    List<HangSX> newData = response.body();
                    hangSxAdapter.setList(newData);
                    rcvHangSx.setAdapter(hangSxAdapter);
                } else {
                    // Xử lý khi có lỗi từ server
                    d("ca" + "chung", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<HangSX>> call, Throwable t) {
            }
        });
    }

    private void initUI() {
        btn_chat = context.findViewById(R.id.fmh_chat);
        slidePager = context.findViewById(R.id.fragment_home_viewpager_slide);
        rcvHangSx = context.findViewById(R.id.fragment_home_rcv_listHang);
        rcvSanPham = context.findViewById(R.id.fragment_home_rcv_listProduct);
        btnGioHang = context.findViewById(R.id.fmh_gioHang);
        txtNumberUnSeenMessage = context.findViewById(R.id.txt_numberUnSeen_message);
        txtSearrch = context.findViewById(R.id.editText);
        viewNull = context.findViewById(R.id.view_null_home);
        configAdapter();
    }
    private void getSanPhamByHang(String hangId) {
        SanPhamRetrofit sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        Call<List<SanPham>> call = sanPhamRetrofit.getListSanPhamByHang(hangId);
        call.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                List<SanPham> newData = response.body();
                if (newData == null){
                    viewNull.setVisibility(View.VISIBLE);
                    rcvSanPham.setVisibility(View.GONE);
                    return;
                }
                if (newData.isEmpty() ) {
                    viewNull.setVisibility(View.VISIBLE);
                    rcvSanPham.setVisibility(View.GONE);
                } else {
                    viewNull.setVisibility(View.GONE);
                    rcvSanPham.setVisibility(View.VISIBLE);
                    sanPhamAdapter.setList(newData);
                    rcvSanPham.setAdapter(sanPhamAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                // Xử lý khi có lỗi
            }
        });
    }
    private void configAdapter() {
        hangSxAdapter = new HangSanPhamAdapter(getContext(), new HangSanPhamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String hangId) {
                // Gọi API lấy danh sách sản phẩm theo ID Hãng
                getSanPhamByHang(hangId);
            }
        });
        sanPhamAdapter = new SanPhamAdapter(getContext());
        slideAdapter = new SlideAdapter(getContext());
    }

    private void autoSlideImage(List<KhuyenMai> list) {
        if (list == null || list.isEmpty() || slidePager == null) {
            d("ca" + "chung", "autoSlideImage: " + "0");
            return;
        }
        if (mTimer == null) {
            d("ca" + "chung", "autoSlideImage: " + "121");
            mTimer = new Timer();
        }
        d("ca" + "chung", "autoSlideImage: ");
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new TimerTask() {
                    @Override
                    public void run() {
                        int currentItem = slidePager.getCurrentItem();
                        int totalItem = list.size() - 1;
                        if (currentItem < totalItem) {
                            currentItem++;
                            slidePager.setCurrentItem(currentItem);
                        } else {
                            slidePager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
