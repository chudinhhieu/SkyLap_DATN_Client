package com.example.skylap_datn_md03.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhamActivity extends AppCompatActivity {
    private TextView tvGiaGocSP, tvSlideSP, tvTenSP, tvGiaSP, tvSaoSP, tvDaBan, tvMoTaSP, tvStarSP, tvSLDG, tvXemDG;
    private TextView tvCPU, tvManHinh, tvRam, tvRom, tvBaoHanh,btn_muangay;
    private ImageView imgSildeSP, imgBack, imgGioHang;
    private RecyclerView rcvCTDG;
    private RelativeLayout view;
    private LinearLayout btnCTSP, btnCTDG, btnChat, btnThemGH, btnMua;
    private RatingBar rbSP;
    private ViewFlipper viewFlipper;
    private SanPham sanPham;
    private Handler slideHandler;

    private SanPhamRetrofit sanPhamRetrofit;
    private RetrofitService retrofitService;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        initView();
        retrofitService = new RetrofitService();
        getSanPham();
        slideHandler = new Handler(Looper.getMainLooper());
        btn_muangay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SanPhamActivity.this,GioHangActivity.class));
            }
        });
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
        tvTenSP.setText(sanPham.getTenSanPham());
        tvGiaSP.setText(String.format("%,.0f", sanPham.getGiaTien()));
        tvCPU.setText(sanPham.getCpu());
        tvManHinh.setText(sanPham.getDisplay());
        tvRam.setText(sanPham.getRam());
        tvRom.setText(sanPham.getRom());
        tvBaoHanh.setText(sanPham.getBaohanh());
        tvMoTaSP.setText(sanPham.getMoTa());
        startSlideshow();
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
        viewFlipper = findViewById(R.id.viewFlipper);
        btn_muangay = findViewById(R.id.btn_muangay);
        progressBar = findViewById(R.id.progressBar);
        view = findViewById(R.id.view);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

}
