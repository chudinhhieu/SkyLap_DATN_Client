package com.example.skylap_datn_md03.ui.activities;

import static android.util.Log.d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.GioHangAdapter2;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.GioHang;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatHangActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<GioHang> listData;
    private RecyclerView rcv_datHang;
    private GioHangAdapter2 gioHangAdapter2;
    private Spinner spVanChuyen, spThanhToan;
    private ArrayAdapter<String> adaperVanChuyen, adaperThanhToan;
    private TextView tienVanChuyen, tongTienVanChuyen,
    txt_tongTienHang, txt_tongTienPhiVanChuyen, txt_tongThanhToan
            , txt_totalPay;
    private static final int VAN_CHUYEN_THUONG = 0;
    private static final int VAN_CHUYEN_NHANH = 1;
    private static final int THANH_TOAN_KHI_NHAN_HANG = 1;
    private static final int THANH_TOAN_MOMO = 2;
    private static final double PHAN_TRAM_THANH_TOAN_NHANH =  0.3;
    private static final double PHAN_TRAM_THANH_TOAN_THUONG =  0.15;
    private GioHang gioHang;
    private RetrofitService retrofitService;
    private SanPhamRetrofit sanPhamRetrofit;
    private ProgressBar loadingGiaTien, loadingChiTietThanhToan;
    private TableLayout viewChiTietThanhToan;
    private double giaVC = 0;
    private ImageView img_back;
    private Button btn_datHang;
    private DonHang donHang;
    private String idUser;
    private SharedPreferencesManager sharedPreferencesManager;
    private long tongTien;
    private boolean trangThaiThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang);
        unitUI();
        donHang = new DonHang();
        adapterConfig();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
           return;
        }
        listData = bundle.getParcelableArrayList("listData");
        gioHang = listData.get(0);
        gioHangAdapter2.setList(listData);
        rcv_datHang.setAdapter(gioHangAdapter2);
        spVanChuyen.setAdapter(adaperVanChuyen);
        spThanhToan.setAdapter(adaperThanhToan);
        //

        //
        spVanChuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tinhTienVanChuyen(position, gioHang);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tinhTienVanChuyen(VAN_CHUYEN_THUONG, gioHang);
            }
        });
        spThanhToan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) trangThaiThanhToan = false;
                else  trangThaiThanhToan = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                trangThaiThanhToan = false;
            }
        });


    }
    void unitUI (){
        retrofitService = new RetrofitService();
        rcv_datHang = findViewById(R.id.datHang_rcv_listProduct);
        spVanChuyen = findViewById(R.id.datHang_spinner_vanChuyen);
        spThanhToan = findViewById(R.id.datHang_spinner_thanhToan);
        //
        tienVanChuyen = findViewById(R.id.datHang_txt_giaVanChuyen);
        tongTienVanChuyen = findViewById(R.id.datHang_tongTienVanChuyen);
        loadingGiaTien = findViewById(R.id.datHang_loading_giaTien);
        //
        txt_tongTienHang = findViewById(R.id.datHang_txt_tongTienHang);
        txt_tongTienPhiVanChuyen = findViewById(R.id.datHang_tongTienPhiVanChuyen);
        txt_tongThanhToan = findViewById(R.id.datHang_txtTongThanhToan_1);
        //
        loadingChiTietThanhToan = findViewById(R.id.datHang_Loading_chiTietThanhToan);
        viewChiTietThanhToan = findViewById(R.id.datHang_view_chiTietThanhToan);
        txt_totalPay = findViewById(R.id.datHang_txtTongThanhToan_2);
        img_back = findViewById(R.id.datHang_imgBack);
        img_back.setOnClickListener(this);
        btn_datHang = findViewById(R.id.datHang_btn_datHang);
        btn_datHang.setOnClickListener(this);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        idUser = sharedPreferencesManager.getUserId();


    }
    void adapterConfig (){
        gioHangAdapter2 = new GioHangAdapter2(this);
        adaperVanChuyen = new ArrayAdapter<>(this, R.layout.item_spinner_choose,
                getResources().getStringArray(R.array.listSpinnerVanChuyen));
        adaperVanChuyen.setDropDownViewResource(R.layout.item_spinner_drop);

        adaperThanhToan = new ArrayAdapter<>(this, R.layout.item_spinner_choose,
                getResources().getStringArray(R.array.listSpinnerThanhToan));
        adaperThanhToan.setDropDownViewResource(R.layout.item_spinner_drop);
    }
    void tinhTienVanChuyen (int position, GioHang gioHang){
        showLoadingGiaTien();
        showLoadingChiTietThanhToan();
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        Call<SanPham> getSanPham = sanPhamRetrofit.getSanPhamByID(gioHang.getIdSanPham());
        getSanPham.enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {

                SanPham sanPham = response.body();
                double match1;
                if (position == VAN_CHUYEN_NHANH){
                    match1 = PHAN_TRAM_THANH_TOAN_NHANH * sanPham.getGiaTien();

                }
               else{
                    match1 = PHAN_TRAM_THANH_TOAN_THUONG * sanPham.getGiaTien();
                }
                giaVC = match1 / 100;
                tienVanChuyen.setText(String.format("%,.0f", giaVC)+ "₫");
                tongTienVanChuyen.setText(tienVanChuyen.getText().toString());
                updateUIChiTietThanhToan(sanPham,giaVC);
               hideLoadingGiaTien();
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {

            }
        });
    }
    void showLoadingGiaTien(){
        tienVanChuyen.setVisibility(View.GONE);
        loadingGiaTien.setVisibility(View.VISIBLE);
    }
    void hideLoadingGiaTien (){
        tienVanChuyen.setVisibility(View.VISIBLE);
        loadingGiaTien.setVisibility(View.GONE);
    }
    void showLoadingChiTietThanhToan(){
        viewChiTietThanhToan.setVisibility(View.GONE);
        loadingChiTietThanhToan.setVisibility(View.VISIBLE);
    }
    void hideLoadingChiTietThanhToan (){
        viewChiTietThanhToan.setVisibility(View.VISIBLE);
        loadingChiTietThanhToan.setVisibility(View.GONE);
    }
    void updateUIChiTietThanhToan (SanPham sanPham, double tienVanChuyen){
        double tienHang = sanPham.getGiaTien() * gioHang.getSoLuong();
        txt_tongTienHang.setText(String.format("%,.0f", tienHang)+"₫");
        txt_tongTienPhiVanChuyen.setText(String.format("%,.0f", tienVanChuyen)+"₫");
        double total = tienHang + tienVanChuyen;
        txt_tongThanhToan.setText(String.format("%,.0f", total)+"₫");
        updateTotalPay(total);
        hideLoadingChiTietThanhToan();
    }
    void updateTotalPay (double totalMoney){
        txt_totalPay.setText(String.format("%,.0f", totalMoney)+"₫");
        tongTien = (long) totalMoney;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.datHang_imgBack){
           finish();
        }
        else if (v.getId() == R.id.datHang_btn_datHang){
            donHang.setIdSanPham(gioHang.getIdSanPham());
            donHang.setIdAccount(idUser);
            donHang.setSoLuong(gioHang.getSoLuong());
            donHang.setTongTien(tongTien);
            donHang.setThanhToan(trangThaiThanhToan);
            datHangListener(donHang);
        }
    }
    void datHangListener(DonHang donHang){
        Intent intent = new Intent(this, DatHangThanhCongActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("donHang", donHang);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}