package com.example.skylap_datn_md03.ui.activities;

import static android.util.Log.d;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.GioHangAdapter;
import com.example.skylap_datn_md03.data.models.GioHang;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.GioHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangActivity extends AppCompatActivity {
    private RecyclerView agh_recyclerView;
    private GioHangAdapter adapter ;
    private ImageView imgBack;
    RetrofitService retrofitService;
    SharedPreferencesManager sharedPreferencesManager;
    List<GioHang> listGioHang;
    private GioHang gioHangThanhToan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        unitUI();
        agh_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retrofitService = new RetrofitService();
        sharedPreferencesManager = new SharedPreferencesManager(this);
        getGioHang();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void clickMuaHang(GioHang gioHang) {
        if (gioHang == null){
           CustomToast.showToast(this,"Bạn cần chọn 1 sản phẩm trước khi mua hàng !");
        }
        else{
            Intent intent = new Intent(this, DatHangActivity.class);
            Bundle bundle = new Bundle();
            ArrayList<GioHang> listData = new ArrayList<>();
            listData.add(gioHang);
            bundle.putParcelableArrayList("listData", listData);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    void unitUI (){
        agh_recyclerView = findViewById(R.id.agh_recyclerView);
        imgBack = findViewById(R.id.agh_img_back);
    }
    private void getGioHang() {
        GioHangRetrofit gioHangRetrofit = retrofitService.retrofit.create(GioHangRetrofit.class);
        String userId = sharedPreferencesManager.getUserId();
        Call<List<GioHang>> getGioHang = gioHangRetrofit.getGioHangByIDAccount(userId);
        getGioHang.enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                listGioHang = response.body();
                adapter = new GioHangAdapter(listGioHang , getApplicationContext());
                agh_recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {
            }
        });
    }
}