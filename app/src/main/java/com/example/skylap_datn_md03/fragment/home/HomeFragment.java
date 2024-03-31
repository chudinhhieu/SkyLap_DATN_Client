package com.example.skylap_datn_md03.fragment.home;

import static android.util.Log.d;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private DatabaseReference mDatabase;
    private SanPhamRetrofit sanPhamRetrofit;
    private LinearLayout btn_chat;
    private SharedPreferencesManager sharedPreferencesManager;
    private  MessagePreferences messagePreferences;
    private MessageRetrofit messageRetrofit;
    private ChatRetrofit chatRetrofit;
    private RetrofitService retrofitService;
    private RelativeLayout btnGioHang;
    private TextView txtNumberUnSeenMessage;

    private List<SanPham> dataList = new ArrayList<>();
    private List<HangSX> dataHangSx = new ArrayList<>();
    private int limit = 10; // Số lượng dữ liệu muốn tải trong mỗi lần
    private boolean isLoadingSanPham = false; // Biến để kiểm tra xem đang tải dữ liệu hay không
    private boolean isLoadingHangSx = false; // Biến để kiểm tra xem đang tải dữ liệu hay không
    private String idChat;
    private int numberChat_notSeen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = inflater.inflate(R.layout.fragment_home, container, false);
        retrofitService = new RetrofitService();
        sharedPreferencesManager = new SharedPreferencesManager(context.getContext());
        messagePreferences = new MessagePreferences();


        unitUI();
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                getChat();
            }
        });

        return context;
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
//                  checkChat(response.body());
                    messagePreferences.checkChat( response.body() ,txtNumberUnSeenMessage  , getContext());

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void getChat(){
        Intent intent = new Intent(context.getContext(), MessageActivity.class);
        intent.putExtra("conversation_key", idChat);
        messagePreferences.putSeeNAllwhenOnclick(idChat);
        startActivity(intent);
    }
    private void checkChat(String idChat){
        if (idChat.length() >  0){
            mDatabase = FirebaseDatabase.getInstance().getReference("messages");
            mDatabase.orderByChild("idChat").equalTo(idChat).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    numberChat_notSeen = 0;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Message mess = data.getValue(Message.class);
                        if (mess.isDaxem() == false) {
                            numberChat_notSeen += 1;
                            d("check", "onDataChange: " +  numberChat_notSeen);
                        }


                        txtNumberUnSeenMessage.setText(""+numberChat_notSeen);

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("err", "onCancelled: " + error);
                }
            });
        }


    }


    private void getListSanPham() {


        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        Call<List<SanPham>> getSp = sanPhamRetrofit.getListSanPham();
        getSp.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if (response.isSuccessful()) {
                    List<SanPham> newData = response.body();
                    // Cập nhật dữ liệu lên RecyclerView
                    sanPhamAdapter.setList(newData);
                    rcvSanPham.setAdapter(sanPhamAdapter);

                } else {
                    d("ca" + "chung", "onResponse: " + response.message());
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

    private void unitUI() {
        btn_chat = context.findViewById(R.id.fmh_chat);
        slidePager = context.findViewById(R.id.fragment_home_viewpager_slide);
        rcvHangSx = context.findViewById(R.id.fragment_home_rcv_listHang);
        rcvSanPham = context.findViewById(R.id.fragment_home_rcv_listProduct);
        btnGioHang = context.findViewById(R.id.fmh_gioHang);
        txtNumberUnSeenMessage = context.findViewById(R.id.txt_numberUnSeen_message);
        configAdapter();
    }

    private void configAdapter() {
        hangSxAdapter = new HangSanPhamAdapter(getContext());
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
