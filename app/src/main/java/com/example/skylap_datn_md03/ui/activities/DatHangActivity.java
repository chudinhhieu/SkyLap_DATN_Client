package com.example.skylap_datn_md03.ui.activities;

import static android.util.Log.d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.ZaloPay.CreateOrder;
import com.example.skylap_datn_md03.adapter.GioHangAdapter2;
import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.GioHang;
import com.example.skylap_datn_md03.data.models.KhuyenMai;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.DonHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class DatHangActivity extends AppCompatActivity {
    private SanPham sanPham;
    private KhuyenMai khuyenMai;
    private ImageView anhSP, btnTru, btnCong,btnBack;
    private TextView tenSP, giaSP, tvTongTienSP, tvTongTienHang,
            hoTen, sdt, diaChi,tvTienVanChuyen, tvTienKhuyenMai,tvTongTien,tvTongCTTT,tvKM;
    private Spinner spinner;
    private Button btnDatHang;
    private EditText ipSoLuong,ipGhiChu;
    private LinearLayout btnKhuyenMai,btnThemDiaChi;
    private RetrofitService retrofitService;
    private DonHangRetrofit donHangRetrofit;
    private View line;
    private RelativeLayout viewMain;
    private ProgressBar progressBar;
    private SharedPreferencesManager sharedPreferencesManager;
    private double tongTienSP = 0;
    private double tienVanChuyen = 0;
    private double tienKhuyenMai = 0;
    private double tongThanhToan = 0;
    private Account account;
    String[] phuongThucThanhToan = {"Khi nhận hàng", "ZaloPay"};
    Boolean isZaloPay = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang);
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("SanPham")) {
                sanPham = (SanPham) intent.getSerializableExtra("SanPham");
            }
        }
        initZaloPay();
        retrofitService = new RetrofitService();
        sharedPreferencesManager = new SharedPreferencesManager(this);
        initView();
        fillDataToView();
        getUser();
        int maxSL = sanPham.getSoLuong();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        Sự kiện ấn thêm địa chỉ
        btnThemDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DatHangActivity.this,ThemDiaChiActivity.class));
            }
        });

//        Sự kiện ấn đặt hàng
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account.getSdt() == null || account.getDiaChi() == null){
                    CustomToast.showToast(DatHangActivity.this,"Vui lòng chọn địa chỉ giao hàng!");
                    return;
                }
                if(isZaloPay){
                    thanhToanZaloPay();
                }else{
                    callDatHang();
                }
            }
        });
        btnKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatHangActivity.this, KhuyenMaiActivity.class);
                startActivityForResult(intent, 69);
            }
        });

//        Sự kiên spinner thay đổi
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy phương thức thanh toán được chọn
                String phuongThuc = phuongThucThanhToan[position];
                if (phuongThuc.equals("Khi nhận hàng")) {
                    isZaloPay = false;
                } else if (phuongThuc.equals("ZaloPay")) {
                    isZaloPay = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                isZaloPay = false;
            }
        });
//        Sự kiện input được thay đổi
        ipSoLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Không cần xử lý trước sự thay đổi văn bản
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Kiểm tra xem EditText có trống không
                boolean isEditTextEmpty = TextUtils.isEmpty(charSequence);
                // Kiểm tra xem EditText chỉ chứa số hay không
                boolean isNumeric = !TextUtils.isEmpty(charSequence) && TextUtils.isDigitsOnly(charSequence);

                // Disable hoặc enable nút dựa trên trạng thái của EditText
                btnDatHang.setEnabled(!isEditTextEmpty && isNumeric);
                if (!isEditTextEmpty && isNumeric) {
                    updateViewTongTienSP();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Không cần xử lý sau sự thay đổi văn bản
            }
        });
//        Sự kiện cộng số lượng sản phẩm

        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ipSoLuong.getText().toString().isEmpty()) {
                    ipSoLuong.setText("1");
                }
                // Lấy số lượng hiện tại từ EditText
                int currentSL = Integer.parseInt(ipSoLuong.getText().toString());

                // Kiểm tra xem số lượng hiện tại có bé hơn maxSL không
                if (currentSL < maxSL) {
                    // Tăng số lượng lên 1
                    currentSL++;

                    // Cập nhật số lượng mới vào EditText
                    ipSoLuong.setText(String.valueOf(currentSL));
                    updateViewTongTienSP();
                } else {
                    CustomToast.showToast(DatHangActivity.this, "Số lượng tối đa là " + maxSL);
                }
            }
        });

//        Sự kiện trừ số lượng sản phẩm
        btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ipSoLuong.getText().toString().isEmpty()) {
                    ipSoLuong.setText("1");
                }
                // Lấy số lượng hiện tại từ EditText
                int currentSL = Integer.parseInt(ipSoLuong.getText().toString());
                // Kiểm tra xem số lượng hiện tại có lớn hơn 1 không
                if (currentSL > 1) {
                    // Giảm số lượng đi 1
                    currentSL--;

                    // Cập nhật số lượng mới vào EditText
                    ipSoLuong.setText(String.valueOf(currentSL));
                    updateViewTongTienSP();
                } else {
                    CustomToast.showToast(DatHangActivity.this, "Số lượng tối thiểu là 1");
                }
            }
        });
    }

    private void callDatHang() {
        if (sanPham == null){
            return;
        }
        String idKhuyenMai = khuyenMai == null ? "":khuyenMai.get_id();
        DonHang donHang = new DonHang(sanPham.get_id(),tienVanChuyen, sharedPreferencesManager.getUserId(),
                idKhuyenMai, Integer.parseInt(ipSoLuong.getText().toString().trim()), tongThanhToan, ipGhiChu.getText().toString().trim(), isZaloPay);
        donHangRetrofit = retrofitService.retrofit.create(DonHangRetrofit.class);
        donHangRetrofit.addDonHang(donHang).enqueue(new Callback<DonHang>() {
            @Override
            public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                Intent intent = new Intent(DatHangActivity.this, DatHangThanhCongActivity.class);
                intent.putExtra("cpu", sanPham.getCpu());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<DonHang> call, Throwable t) {
                Toast.makeText(DatHangActivity.this, "That bai", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initZaloPay() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2554 , Environment.SANDBOX);
    }
    private void thanhToanZaloPay(){
        showLoading();
// Chuyển số double thành chuỗi nguyên
        String tien = String.valueOf((long) tongThanhToan);
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder(tien);
            String code = data.getString("return_code");

            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(DatHangActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        callDatHang();
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {

                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getUser() {
        if (sanPham == null){
            return;
        }
        AccountRetrofit accountRetrofit = retrofitService.retrofit.create(AccountRetrofit.class);
        accountRetrofit.getAccountById(sharedPreferencesManager.getUserId()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                 account = response.body();
                if (account.getDiaChi() == null || account.getSdt() == null){
                    hoTen.setText("Vui lòng chọn địa chỉ nhận hàng!");
                    hoTen.setTextColor(Color.RED);
                    line.setVisibility(View.GONE);
                }else{
                    String hoVaTen = account.getHoTen() == null ? account.getTaiKhoan(): account.getHoTen();
                    hoTen.setText(hoVaTen);
                    diaChi.setText(account.getDiaChi().getDiaChi());
                    line.setVisibility(View.VISIBLE);
                    sdt.setText(account.getSdt());
                    tienVanChuyen = account.getDiaChi().getIdTinh().toString().equals("01") ? 21000 : 49000;
                    updateViewTongTienSP();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });
    }

    private void updateViewTongTienSP() {
        if (sanPham != null){
            tongTienSP = Integer.parseInt(ipSoLuong.getText().toString()) * sanPham.getGiaTien();
            tongThanhToan = tongTienSP + tienVanChuyen - tienKhuyenMai;
            tvTongTienSP.setText(String.format("%,.0f", tongTienSP) + "₫");
            tvTongTienHang.setText(String.format("%,.0f", tongTienSP) + "₫");
            tvTongCTTT.setText(String.format("%,.0f", tongThanhToan) + "₫");
            tvTongTien.setText(String.format("%,.0f", tongThanhToan) + "₫");
            tvTienVanChuyen.setText(String.format("%,.0f", tienVanChuyen) + "₫");
            tvTienKhuyenMai.setText(String.format("-"+"%,.0f", tienKhuyenMai) + "₫");
        }
    }

    private void fillDataToView() {
        Picasso.get().load(sanPham.getAnhSanPham()).into(anhSP);
        tenSP.setText(sanPham.getTenSanPham());
        giaSP.setText(String.format("%,.0f", sanPham.getGiaTien()) + "₫");
        updateViewTongTienSP();
    }

    private void initView() {
        progressBar = findViewById(R.id.adh_loading);
        viewMain = findViewById(R.id.adh_view);
        btnBack = findViewById(R.id.adh_img_back);
        ipGhiChu = findViewById(R.id.adh_ipGhiChu);
        btnThemDiaChi = findViewById(R.id.adh_addDicChi);
        line = findViewById(R.id.view_line_dh);
        anhSP = findViewById(R.id.adh_anhSP);
        tenSP = findViewById(R.id.adh_tenSP);
        giaSP = findViewById(R.id.adh_giaSP);
        tvTongTienSP = findViewById(R.id.adh_tvTongTienSP);
        tvTongTienHang = findViewById(R.id.adh_tvTongTienHang);
        ipSoLuong = findViewById(R.id.adh_SLSP);
        btnDatHang = findViewById(R.id.adh_btnDatHang);
        btnCong = findViewById(R.id.adh_themSP);
        btnTru = findViewById(R.id.adh_truSP);
        hoTen = findViewById(R.id.adh_hoTen);
        sdt = findViewById(R.id.adh_sdt);
        diaChi = findViewById(R.id.adh_diaChi);
        tvTienKhuyenMai = findViewById(R.id.adh_tvTienKhuyenMai);
        tvTienVanChuyen = findViewById(R.id.adh_tvTienVanChuyen);
        tvTongCTTT = findViewById(R.id.adh_tvTongTienThanhToan);
        tvTongTien = findViewById(R.id.adh_tvTongTien);
        spinner = findViewById(R.id.adh_spnPTTT);
        btnKhuyenMai = findViewById(R.id.adh_km);
        tvKM = findViewById(R.id.adh_tvKM);
        // Khởi tạo Adapter với mảng lựa chọn thanh toán
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spn, phuongThucThanhToan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Thiết lập Adapter cho Spinner
        spinner.setAdapter(adapter);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 69 && resultCode == RESULT_OK && data != null) {
            khuyenMai = (KhuyenMai) data.getSerializableExtra("KhuyenMaiObject");
            tvKM.setText(khuyenMai.getCode());
            tienKhuyenMai = khuyenMai.getSoTienGiam();
            updateViewTongTienSP();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    getUser();
    }
    void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
        viewMain.setVisibility(View.GONE);
    }
    void hideLoading(){
    }
}