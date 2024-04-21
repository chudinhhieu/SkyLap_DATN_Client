package com.example.skylap_datn_md03.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.BienTheAdapter;
import com.example.skylap_datn_md03.adapter.DanhGiaAdapter;
import com.example.skylap_datn_md03.data.models.BienThe;
import com.example.skylap_datn_md03.data.models.DanhGia;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.FavoriteResponse;
import com.example.skylap_datn_md03.data.models.GioHang;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.data.models.SanPhamYeuThich;
import com.example.skylap_datn_md03.retrofitController.ChatRetrofit;
import com.example.skylap_datn_md03.retrofitController.DanhGiaRetrofit;
import com.example.skylap_datn_md03.retrofitController.DonHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.GioHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.retrofitController.SanPhamYTRetrofit;
import com.example.skylap_datn_md03.ui.dialogs.CheckDialog;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhamActivity extends AppCompatActivity {
    private TextView tvGiaGocSP, tvSlideSP, tvTenSP, tvGiaSP, tvSaoSP, tvDaBan, tvMoTaSP, tvStarSP, tvSLDG, tvXemDG;
    private TextView tvCPU, tvManHinh, tvRam, tvRom, tvBaoHanh;
    private ImageView imgBack, imgGioHang, imgFavorite;
    private RecyclerView rcvCTDG, rcvBienThe;
    private RelativeLayout view;
    private LinearLayout btnCTSP, btnCTDG, btnChat, btnThemGH, btnMua;
    private RatingBar rbSP;
    private ChatRetrofit chatRetrofit;
    private SanPham sanPham;
    private Handler slideHandler;

    private SanPhamRetrofit sanPhamRetrofit;
    private SanPhamYTRetrofit sanPhamYTRetrofit;
    private RetrofitService retrofitService;
    private ProgressBar progressBar;
    private SharedPreferencesManager sharedPreferencesManager;
    private MyAuth myAuth;
    private BienThe bienTheChon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        initView();
        sharedPreferencesManager = new SharedPreferencesManager(this);
        retrofitService = new RetrofitService();
        sanPhamYTRetrofit = retrofitService.getRetrofit().create(SanPhamYTRetrofit.class);
        getSanPham();
        slideHandler = new Handler(Looper.getMainLooper());
        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()) {
                    CheckDialog.showCheckDialog(SanPhamActivity.this, "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                Intent intent = new Intent(SanPhamActivity.this, DatHangActivity.class);
                intent.putExtra("SanPham", sanPham);
                intent.putExtra("BienThe", bienTheChon);
                startActivity(intent);
            }
        });
        imgGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()) {
                    CheckDialog.showCheckDialog(SanPhamActivity.this, "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                startActivity(new Intent(SanPhamActivity.this, GioHangActivity.class));
            }
        });
        btnThemGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()) {
                    CheckDialog.showCheckDialog(SanPhamActivity.this, "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                showBottomSheet(sanPham);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCTSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SanPhamActivity.this, ChiTietSanPhamActivity.class);
                intent.putExtra("SanPham", sanPham);
                intent.putExtra("BienThe", bienTheChon);
                startActivity(intent);
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()) {
                    CheckDialog.showCheckDialog(SanPhamActivity.this, "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                logicChat();
            }
        });
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserId().isEmpty()) {
                    CheckDialog.showCheckDialog(SanPhamActivity.this, "Thông báo", "Vui lòng đăng nhập để mua hàng!");
                    return;
                }
                performAddFavorite(new SanPhamYeuThich(sanPham.get_id(), sharedPreferencesManager.getUserId()));
            }
        });
        tvXemDG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SanPhamActivity.this, DanhSachDanhGia.class);
                intent.putExtra("idSP", sanPham.get_id());
                startActivity(intent);
            }
        });

    }

    private void HienSaoVaDaBan() {
        DonHangRetrofit donHangRetrofit = retrofitService.retrofit.create(DonHangRetrofit.class);
        donHangRetrofit.laySaoTrungBinh(sanPham.get_id()).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                tvSaoSP.setText("Đánh giá " + response.body() + "/5");
                tvStarSP.setText(response.body() + "/5");
                rbSP.setRating(Float.parseFloat(response.body().toString()));
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.d("zzzzzzz", "onFailure: " + t);

            }
        });
        donHangRetrofit.layLanDanhGia(sanPham.get_id()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                tvSLDG.setText(response.body() + " đánh giá");
                if (response.body().equals(0)) {
                    btnCTDG.setVisibility(View.GONE);
                } else {
                    tvXemDG.setText("Xem tất cả (" + response.body() + ")");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("zzzzzzz", "onFailure: " + t);

            }
        });
        donHangRetrofit.layDaBan(sanPham.get_id()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                tvDaBan.setText("Đã bán " + response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        DanhGiaRetrofit danhGiaRetrofit = retrofitService.retrofit.create(DanhGiaRetrofit.class);
        danhGiaRetrofit.getDaDanhGiaTheoSanPham(sanPham.get_id()).enqueue(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                DanhGiaAdapter danhGiaAdapter = new DanhGiaAdapter(response.body(), SanPhamActivity.this);
                rcvCTDG.setAdapter(danhGiaAdapter);
            }

            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {

            }
        });
    }

    private void logicChat() {
        chatRetrofit = retrofitService.retrofit.create(ChatRetrofit.class);
        String userId = sharedPreferencesManager.getUserId();
        Call<String> addChat = chatRetrofit.CreateConverSation(userId);
        addChat.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 206) {
                    Intent intent = new Intent(SanPhamActivity.this, MessageActivity.class);
                    intent.putExtra("conversation_key", response.body());
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void showBottomSheet(SanPham sanPham) {
        int maxSL = bienTheChon.getSoLuong();

        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_sanpham, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        ImageView img_anhSP = view.findViewById(R.id.bssp_img);
        ImageView btnCong = view.findViewById(R.id.bssp_btn_congSL);
        ImageView btnTru = view.findViewById(R.id.bssp_btn_truSL);
        EditText ipSoLuong = view.findViewById(R.id.bssp_ip_soLuong);
        TextView tvTen = view.findViewById(R.id.bssp_ten);
        TextView tvRamRom = view.findViewById(R.id.bssp_ram_rom);
        TextView tvGia = view.findViewById(R.id.bssp_gia);
        TextView tvKho = view.findViewById(R.id.bssp_kho);
        Button btnThemGioHang = view.findViewById(R.id.bssp_btn_add);


        Picasso.get().load(sanPham.getAnhSanPham()).into(img_anhSP);
        tvGia.setText(String.format("%,.0f", bienTheChon.getGiaTien()) + "₫");
        tvKho.setText("Kho: " + maxSL);
        tvTen.setText(sanPham.getTenSanPham());
        tvRamRom.setText(bienTheChon.getRam() + " + " + bienTheChon.getRom());
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHangRetrofit gioHangRetrofit = retrofitService.retrofit.create(GioHangRetrofit.class);
                String userId = sharedPreferencesManager.getUserId();
                Call<MyAuth> getGioHang = gioHangRetrofit.themGioHang(new GioHang(sanPham.get_id(), bienTheChon.get_id(), userId, Integer.parseInt(ipSoLuong.getText().toString().trim())));
                getGioHang.enqueue(new Callback<MyAuth>() {
                    @Override
                    public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
                        myAuth = response.body();
                        CustomToast.showToast(SanPhamActivity.this, myAuth.getMessage());
                        bottomSheetDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<MyAuth> call, Throwable t) {
                        CustomToast.showToast(SanPhamActivity.this, myAuth.getMessage());
                    }
                });
            }
        });
        ipSoLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Không cần xử lý trước sự thay đổi văn bản
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Kiểm tra xem EditText có trống không
                boolean isEditTextEmpty = charSequence.toString().trim().isEmpty();
                // Disable hoặc enable nút dựa trên trạng thái của EditText
                btnThemGioHang.setEnabled(!isEditTextEmpty);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Không cần xử lý sau sự thay đổi văn bản
            }
        });

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
                } else {
                    CustomToast.showToast(SanPhamActivity.this, "Số lượng tối đa là " + maxSL);
                }
            }
        });

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
                } else {
                    CustomToast.showToast(SanPhamActivity.this, "Số lượng tối thiểu là 1");
                }
            }
        });
        InputFilter filter = new InputFilter() {
            int min = 1;
            int max = maxSL;

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                try {
                    // Nếu người dùng nhập giá trị mới
                    int input = Integer.parseInt(dest.toString() + source.toString());

                    // Kiểm tra xem giá trị mới có nằm trong khoảng từ 1 đến max không
                    if (input >= min && input <= max) {
                        return null; // Giá trị hợp lệ, không có thay đổi
                    } else {
                        CustomToast.showToast(SanPhamActivity.this, "Chỉ nhập số lượng trong khoảng từ 1 đến " + max);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                // Giá trị không hợp lệ, không cho nhập
                return "";
            }
        };
// Thiết lập InputFilter cho EditText
        ipSoLuong.setFilters(new InputFilter[]{filter});
        bottomSheetDialog.show();
    }

    private void getSanPham() {
        showLoading();
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        String idSanPham = getIntent().getStringExtra("idSanPham");
        if (idSanPham != null) {
            Call<SanPham> getSanPham = sanPhamRetrofit.getSanPhamByID(idSanPham);
            getSanPham.enqueue(new Callback<SanPham>() {
                @Override
                public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                    sanPham = response.body();
                    bienTheChon = sanPham.getBienThe().get(0);
                    updateUIWithSanPham(sanPham);
                    hideLoading(); // Ẩn ProgressBar khi tải xong
                    view.setVisibility(View.VISIBLE); // Hiển thị view
                }

                @Override
                public void onFailure(Call<SanPham> call, Throwable t) {
                    hideLoading(); // Ẩn ProgressBar khi có lỗi
                }
            });
        }
    }

    private void startSlideshow() {
        ViewFlipper viewFlipper = findViewById(R.id.viewFlipper);
        tvSlideSP.setText("1/" + sanPham.getAnh().size());

        for (String imageUrl : sanPham.getAnh()) {
            ImageView imageView = new ImageView(this);
            Picasso.get().load(imageUrl).into(imageView);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);

        viewFlipper.getInAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                int displayedChild = viewFlipper.getDisplayedChild() + 1;
                int totalImages = sanPham.getAnh().size();
                if (displayedChild > 0 && displayedChild <= totalImages) {
                    tvSlideSP.setText(displayedChild + "/" + totalImages);
                } else {
                    tvSlideSP.setText("1/" + totalImages);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        slideHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewFlipper.showNext();
                slideHandler.postDelayed(this, 3000);
            }
        }, 2000);
    }


    private void updateUIWithSanPham(SanPham sanPham) {
        List<BienThe> listBienThe = sanPham.getBienThe();
        for (int i = 0; i < listBienThe.size(); i++) {
            if (listBienThe.get(i).getSoLuong() == 0) {
                listBienThe.remove(i);
            }
        }
        BienTheAdapter bienTheAdapter = new BienTheAdapter(listBienThe, SanPhamActivity.this, new BienTheAdapter.OnBienTheSelectedListener() {
            @Override
            public void onBienTheSelected(BienThe bienThe) {
                bienTheChon = bienThe;
                // Cập nhật giao diện dựa trên biến thể được chọn
                tvGiaSP.setText(String.format("%,.0f", bienThe.getGiaTien()) + "₫");
                tvRam.setText(bienThe.getRam());
                tvRom.setText(bienThe.getRom());
            }
        });
        rcvBienThe.setAdapter(bienTheAdapter);
        tvTenSP.setText(sanPham.getTenSanPham());
        tvGiaSP.setText(String.format("%,.0f", sanPham.getBienThe().get(0).getGiaTien()) + "₫");
        tvCPU.setText(sanPham.getCpu());
        tvManHinh.setText(sanPham.getDisplay());
        tvRam.setText(sanPham.getBienThe().get(0).getRam());
        tvRom.setText(sanPham.getBienThe().get(0).getRom());
        tvBaoHanh.setText(sanPham.getBaohanh());
        tvMoTaSP.setText(sanPham.getMoTa());
        startSlideshow();
        HienSaoVaDaBan();
        checkFavoriteStatus();
    }

    private void initView() {
        tvGiaGocSP = findViewById(R.id.asp_tv_giagoc);
        tvGiaGocSP.setPaintFlags(tvGiaGocSP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvSlideSP = findViewById(R.id.asp_tv_slide);
        tvTenSP = findViewById(R.id.asp_tv_tensp);
        tvGiaSP = findViewById(R.id.asp_tv_gia);
        tvGiaGocSP.setVisibility(View.GONE);
        tvSaoSP = findViewById(R.id.asp_tv_danhgia);
        tvDaBan = findViewById(R.id.asp_tv_slban);
        btnCTSP = findViewById(R.id.asp_ll_xemthem_ctsp);
        btnCTDG = findViewById(R.id.asp_ll_xemthem_dg);
        tvMoTaSP = findViewById(R.id.asp_tv_mota);
        rbSP = findViewById(R.id.asp_rb);
        tvStarSP = findViewById(R.id.asp_tv_star);
        tvSLDG = findViewById(R.id.asp_tv_sldg);
        rcvCTDG = findViewById(R.id.asp_rcv_dg);
        rcvBienThe = findViewById(R.id.rcv_bienThe);
        tvXemDG = findViewById(R.id.asp_tv_xemdg);
        imgBack = findViewById(R.id.asp_img_back);
        imgGioHang = findViewById(R.id.asp_img_cart);
        btnChat = findViewById(R.id.asp_ll_chat);
        btnMua = findViewById(R.id.asp_ll_mua);
        btnThemGH = findViewById(R.id.asp_ll_add_cart);
        tvCPU = findViewById(R.id.asp_tv_cpu);
        tvManHinh = findViewById(R.id.asp_tv_man_hinh);
        tvRam = findViewById(R.id.asp_tv_ram);
        tvRom = findViewById(R.id.asp_tv_rom);
        tvBaoHanh = findViewById(R.id.asp_tv_bao_hanh);
        progressBar = findViewById(R.id.progressBar);
        view = findViewById(R.id.view);
        imgFavorite = findViewById(R.id.asp_img_favorite);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void checkFavoriteStatus() {
        if (sharedPreferencesManager.getUserId().isEmpty()) {
            imgFavorite.setImageResource(R.drawable.ic_chuathich);
            return;
        }
        SanPhamYTRetrofit sanPhamYTRetrofit = retrofitService.getRetrofit().create(SanPhamYTRetrofit.class);
        String userId = sharedPreferencesManager.getUserId();
        Call<FavoriteResponse> call = sanPhamYTRetrofit.checkSanPhamYeuThich(new SanPhamYeuThich(sanPham.get_id(), userId));
        call.enqueue(new Callback<FavoriteResponse>() {
            @Override
            public void onResponse(Call<FavoriteResponse> call, Response<FavoriteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean isFavorite = response.body().isSuccess();
                    if (isFavorite) {
                        imgFavorite.setImageResource(R.drawable.ic_dathich);
                    } else {
                        imgFavorite.setImageResource(R.drawable.ic_chuathich);
                    }
                }
            }

            @Override
            public void onFailure(Call<FavoriteResponse> call, Throwable t) {
                imgFavorite.setImageResource(R.drawable.ic_chuathich);
            }
        });
    }


    private void performAddFavorite(SanPhamYeuThich sanPhamYeuThich) {
        if (sharedPreferencesManager.getUserId().isEmpty()) {
            return;
        }
        sanPhamYTRetrofit.addSanPhamYeuThich(sanPhamYeuThich).enqueue(new Callback<MyAuth>() {
            @Override
            public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
                myAuth = response.body();
                CustomToast.showToast(SanPhamActivity.this, myAuth.getMessage());
                if (myAuth.isSuccess()) {
                    imgFavorite.setImageResource(R.drawable.ic_dathich);
                } else {
                    imgFavorite.setImageResource(R.drawable.ic_chuathich);
                }
            }

            @Override
            public void onFailure(Call<MyAuth> call, Throwable t) {
                Log.e("performAddFavorite", "Error: " + t.getMessage());
            }
        });
    }
}
