package com.example.skylap_datn_md03.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class SanPhamActivity extends AppCompatActivity {
    private TextView tvGiaGocSP, tvSlideSP, tvTenSP, tvGiaSP, tvSaoSP, tvDaBan, tvMoTaSP, tvStarSP, tvSLDG, tvXemDG;
    private TextView tvCPU, tvManHinh, tvRam, tvRom, tvBaoHanh,btn_muangay;
    private ImageView imgSildeSP, imgBack, imgGioHang;
    private RecyclerView rcvCTDG;
    private LinearLayout btnCTSP, btnCTDG, btnChat, btnThemGH, btnMua;
    private RatingBar rbSP;
    private ViewFlipper viewFlipper;
    SanPham sanPham;
    private Handler slideHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        initView();

        sanPham = new SanPham(
                10,          // soLuong
                true,        // trangThai
                59690000,       // giaTien
                15.0,        // chieuCao
                8.0,         // chieuRong
                1.5,         // trongLuong
                "Android",   // os
                "Apple M2 Pro", // cpu
                "Adreno",    // gpu
                "14.2\" (3024 x 1964)",   // display
                "Thiết kế sang trọng - Vỏ kim loại cùng trọng lượng chỉ 1.6kg dễ dàng mang theo mọi nơi.\n" +
                        "Hiển thị chân thật - Kích thước màn hình 14 inch cùng tần số quét lên đến 120Hz.\n" +
                        "Cấu hình mạnh mẽ - Ram 16GB cùng SSD 1TB đa nhiệm mượt mà, tránh tình trạng giật lag.\n" +
                        "Xử lý tốt các tác vụ đồ hoạ - GPU 16 nhân giúp việc render video hay chỉnh sửa ảnh diễn ra một cách nhẹ nhàng.", // moTa
                Arrays.asList("https://cdn.tgdd.vn/Products/Images/44/302146/macbook-pro-14-inch-m2-pro-gray-1.jpg",
                        "https://cdn.tgdd.vn/Products/Images/44/302146/macbook-pro-14-inch-m2-pro-gray-2.jpg",
                        "https://cdn.tgdd.vn/Products/Images/44/302146/macbook-pro-14-inch-m2-pro-gray-3.jpg",
                        "https://cdn.tgdd.vn/Products/Images/44/302146/macbook-pro-14-inch-m2-pro-gray-4.jpg",
                        "https://cdn.tgdd.vn/Products/Images/44/302146/macbook-pro-14-inch-m2-pro-gray-6.jpg"), // anh
                "idShop123", // idShop
                "idHangSX456", // idHangSX
                "idLoaiSP789", // idLoaiSP
                "MacBook Pro M2 Pro 2023 14 inch (16GB/1TB SSD)"
        );
        updateUIWithSanPham(sanPham);
        slideHandler = new Handler(Looper.getMainLooper());
        startSlideshow();
        btn_muangay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SanPhamActivity.this,GioHangActivity.class));
            }
        });
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
        tvMoTaSP.setText(sanPham.getMoTa());
    }

    private void initView() {
        tvGiaGocSP = findViewById(R.id.asp_tv_giagoc);
        tvGiaGocSP.setPaintFlags(tvGiaGocSP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvSlideSP = findViewById(R.id.asp_tv_slide);
        tvTenSP = findViewById(R.id.asp_tv_tensp);
        tvGiaSP = findViewById(R.id.asp_tv_gia);
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
    }
}
