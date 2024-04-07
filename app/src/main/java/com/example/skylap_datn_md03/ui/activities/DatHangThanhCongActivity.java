package com.example.skylap_datn_md03.ui.activities;

import static android.util.Log.d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.skylap_datn_md03.MainActivity;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.SanPhamAdapter;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.DonHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatHangThanhCongActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView txtTrangChu, txtDonMua;
    private ImageButton imgBack, imgCart;
    private RecyclerView listProduct;
    private SanPhamAdapter productAdapter;
    private RetrofitService retrofitService;
    private Intent intent;
    private SanPhamRetrofit sanPhamRetrofit;
    private ProgressBar loading;
    private LinearLayout view;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang_thanh_cong);
        initUI();

    }
    void initUI(){
        txtTrangChu = findViewById(R.id.datHangThanhCong_txt_trangChu);
        txtDonMua = findViewById(R.id.datHangThanhCong_txt_donMua);
        imgBack = findViewById(R.id.datHangThanhCong_img_back);
        imgCart = findViewById(R.id.datHangThanhCong_img_cart);
        listProduct = findViewById(R.id.datHangThanhCong_rcv_list);
        imgBack.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        txtTrangChu.setOnClickListener(this);
        txtDonMua.setOnClickListener(this);
        view = findViewById(R.id.datHangTC_view);
        loading = findViewById(R.id.datHangTC_loading);
        retrofitService = new RetrofitService();
        getListProduct();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.datHangThanhCong_img_back){
            finish();
        }
        else if(v.getId() == R.id.datHangThanhCong_img_cart){
            cartOnClickListener();
        }
        else if(v.getId() == R.id.datHangThanhCong_txt_trangChu){
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.datHangThanhCong_txt_donMua) {
            intent = new Intent(this, QuanLyDonHangActivity.class);
            startActivity(intent);
            finish();
        }
    }
    void getListProduct (){
        Intent intent = getIntent();
        String cpu = intent.getStringExtra("cpu");
        showLoading();
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        Call<List<SanPham>> getList = sanPhamRetrofit.getListSanPhamByCPU(cpu);
                getList.enqueue(new Callback<List<SanPham>>() {
                    @Override
                    public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                        productAdapter = new SanPhamAdapter(DatHangThanhCongActivity.this);
                        productAdapter.setList(response.body());
                        listProduct.setAdapter(productAdapter);
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Call<List<SanPham>> call, Throwable t) {
                        CustomToast.showToast(DatHangThanhCongActivity.this, "Có gì đó sai sai ");
                    }
                });
    }
    void cartOnClickListener(){
        intent = new Intent(this, QuanLyDonHangActivity.class);
        startActivity(intent);
        finish();
    }
    void showLoading(){
        view.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }
    void hideLoading(){
        view.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }
}