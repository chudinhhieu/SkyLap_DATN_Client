package com.example.skylap_datn_md03.ui.activities;

import static android.util.Log.d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private DonHang donHang;
    private SanPham sanPham;
    private ProgressBar loading;
    private LinearLayout view;
    private DonHangRetrofit donHangRetrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang_thanh_cong);
        unitUI();
        getDonHang();
        getListProduct(donHang.getIdSanPham());
        createDonHang();
    }
    void unitUI(){
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
        showLoading();
    }
    void getDonHang (){
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        donHang = (DonHang) bundle.getSerializable("donHang");
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
        }
        else if(v.getId() == R.id.datHangThanhCong_txt_donMua){
            gotoDetailOder(donHang.get_id());
        }
    }

    private void gotoDetailOder(String donHangID) {
        Bundle bundle = new Bundle();
        intent = new Intent(this,ChiTietDonDonHang.class);
        bundle.putString("donHangID", donHangID);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void getListProduct (String idSP){
        Call<SanPham> getSP = sanPhamRetrofit.getSanPhamByID(idSP);
        getSP.enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                Call<List<SanPham>> getList = sanPhamRetrofit.getListSanPhamByIdNhaSX(response.body().getCpu());
                getList.enqueue(new Callback<List<SanPham>>() {
                    @Override
                    public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                        productAdapter = new SanPhamAdapter(DatHangThanhCongActivity.this);
                        productAdapter.setList(response.body());
                        listProduct.setAdapter(productAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<SanPham>> call, Throwable t) {
                        CustomToast.showToast(DatHangThanhCongActivity.this, "Có gì đó sai sai ");
                    }
                });
            }
            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                CustomToast.showToast(DatHangThanhCongActivity.this, "Có gì đó sai sai ");
            }
        });

    }
    void cartOnClickListener(){
        intent = new Intent(this, GioHangActivity.class);
        startActivity(intent);
    }
    void showLoading(){
        view.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }
    void hideLoading(){
        view.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }
    void createDonHang (){
        retrofitService = new RetrofitService();
        donHangRetrofit = retrofitService.retrofit.create(DonHangRetrofit.class);
        Call<DonHang> addDonHang = donHangRetrofit.addDonHang(donHang);
        addDonHang.enqueue(new Callback<DonHang>() {
            @Override
            public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                hideLoading();
                d("ca" + "chung", "onResponse: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<DonHang> call, Throwable t) {
                CustomToast.showToast(DatHangThanhCongActivity.this,"Có gì đó sai sai");
                d("ca" + "chung", "onFailure: "+t.getMessage());
            }
        });

    }
}