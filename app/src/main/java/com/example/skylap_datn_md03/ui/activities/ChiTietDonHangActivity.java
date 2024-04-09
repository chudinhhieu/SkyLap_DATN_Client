package com.example.skylap_datn_md03.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.KhuyenMai;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.DonHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.KhuyenMaiRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDonHangActivity extends AppCompatActivity {
    private DonHang donHang;
    private SanPham sanPham;
    private KhuyenMai khuyenMai;
    private RetrofitService retrofitService;
    private AccountRetrofit accountRetrofit;
    private SanPhamRetrofit sanPhamRetrofit;
    private KhuyenMaiRetrofit khuyenMaiRetrofit;
    private DonHangRetrofit donHangRetrofit;
    private ImageView anhSP, btnTru, btnCong, btnBack;
    private Button btnDatHang;
    private EditText edtSLSP, edtGhiChu;
    private TextView tenSP, giaSP, tvTongTienSP, tvTongTienHang,
            hoTen, sdt, diaChi, tvTienVanChuyen, tvTienKhuyenMai, tvTongTien, tvTongCTTT, tvKM;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);
             String id = getIntent().getStringExtra("DonHang");
        hoTen = findViewById(R.id.actdh_hoTen);
        sdt = findViewById(R.id.actdh_sdt);
        diaChi = findViewById(R.id.actdh_diaChi);

        anhSP = findViewById(R.id.actdh_anhSP);
        btnBack = findViewById(R.id.actdh_img_back);
        edtSLSP = findViewById(R.id.actdh_SLSP);
        tenSP = findViewById(R.id.actdh_tenSP);
        giaSP = findViewById(R.id.actdh_giaSP);

        edtGhiChu = findViewById(R.id.actdh_ipGhiChu);
        tvTongTienSP = findViewById(R.id.actdh_tvTongTienSP);
        tvKM = findViewById(R.id.actdh_tvKM);

        tvTongTienHang = findViewById(R.id.actdh_tvTongTienHang);
        tvTienVanChuyen = findViewById(R.id.actdh_tvTienVanChuyen);
        tvTienKhuyenMai = findViewById(R.id.actdh_tvTienKhuyenMai);
        tvTongCTTT = findViewById(R.id.actdh_tvTongTienThanhToan);

        btnDatHang = findViewById(R.id.actdh_btnDatHang);


        retrofitService = new RetrofitService();
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);

        khuyenMaiRetrofit = retrofitService.retrofit.create(KhuyenMaiRetrofit.class);

        donHangRetrofit = retrofitService.retrofit.create(DonHangRetrofit.class);
        donHangRetrofit.GetDonHangById(id).enqueue(new Callback<DonHang>() {
            @Override
            public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                donHang = response.body();
                getUser();
                getSanPham();
                getKhuyenMai();
                getTrangThai();
                edtGhiChu.setText(donHang.getGhiChu());

                tvTienVanChuyen.setText(String.format("%,.0f", donHang.getTienShip()) + "₫");
                tvTongCTTT.setText(String.format("%,.0f", donHang.getTongTien()) + "₫");

                btnDatHang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<Void> donHangCall = donHangRetrofit.themTrangThai(donHang.get_id(), "Đã huỷ");
                        donHangCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                btnDatHang.setVisibility(View.GONE);
                                CustomToast.showToast(ChiTietDonHangActivity.this,"Đã huỷ đơn hàng thành công");
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                CustomToast.showToast(ChiTietDonHangActivity.this,"Đã huỷ đơn hàng thất bại");
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<DonHang> call, Throwable t) {

            }
        });
        accountRetrofit = retrofitService.retrofit.create(AccountRetrofit.class);
        if (donHang != null){

        }


    }

    private void getTrangThai() {
        for (int i = 0; i < donHang.getTrangThai().size(); i++) {
            if (donHang.getTrangThai().get(i).getIsNow() == true) {
                switch (donHang.getTrangThai().get(i).getTrangThai()) {
                    case "Chờ xác nhận":
                        btnDatHang.setText("Huỷ đơn hàng");
                        break;
                    default:
                        btnDatHang.setVisibility(View.GONE);
                        break;
                }
            }
        }
    }

    private void getKhuyenMai() {
        khuyenMaiRetrofit.getKhuyenMaiById(donHang.getIdKhuyenMai()).enqueue(new Callback<KhuyenMai>() {
            @Override
            public void onResponse(Call<KhuyenMai> call, Response<KhuyenMai> response) {
                khuyenMai = response.body();

                tvTienKhuyenMai.setText("-" +String.format("%,.0f", khuyenMai.getSoTienGiam()) + "₫");
                tvKM.setText(khuyenMai.getCode());
            }

            @Override
            public void onFailure(Call<KhuyenMai> call, Throwable t) {

            }
        });
    }

    private void getUser() {
        accountRetrofit.getAccountById(donHang.getIdAccount()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                account = response.body();
                String hoVaTen = account.getHoTen() == null ? account.getTaiKhoan() : account.getHoTen();
                hoTen.setText(hoVaTen);
                hoTen.setTextColor(Color.BLACK);
                diaChi.setText(account.getDiaChi().getDiaChi());
                sdt.setText(account.getSdt());
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });
    }
    private void getSanPham() {
        sanPhamRetrofit.getSanPhamByID(donHang.getIdSanPham()).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                sanPham = response.body();
                Picasso.get().load(sanPham.getAnhSanPham()).into(anhSP);
                tenSP.setText(sanPham.getTenSanPham());
                giaSP.setText(String.format("%,.0f", sanPham.getGiaTien()) + "₫");
                edtSLSP.setText(donHang.getSoLuong()+ "");
                edtSLSP.setEnabled(false);

                Double tongTienSP = donHang.getSoLuong() * sanPham.getGiaTien();
                tvTongTienSP.setText(String.format("%,.0f", tongTienSP) + "₫");
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {

            }
        });
    }

}