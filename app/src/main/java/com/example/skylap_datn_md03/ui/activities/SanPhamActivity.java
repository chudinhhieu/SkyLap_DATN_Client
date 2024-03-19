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
import com.example.skylap_datn_md03.data.models.GioHang;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.data.models.SanPhamYeuThich;
import com.example.skylap_datn_md03.retrofitController.ChatRetrofit;
import com.example.skylap_datn_md03.retrofitController.GioHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.retrofitController.SanPhamYTRetrofit;
import com.example.skylap_datn_md03.ui.activities.auth.LoginActivity;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhamActivity extends AppCompatActivity {
    private TextView tvGiaGocSP, tvSlideSP, tvTenSP, tvGiaSP, tvSaoSP, tvDaBan, tvMoTaSP, tvStarSP, tvSLDG, tvXemDG;
    private TextView tvCPU, tvManHinh, tvRam, tvRom, tvBaoHanh, btn_muangay;
    private ImageView imgSildeSP, imgBack, imgGioHang, imgFavorite ;
    private RecyclerView rcvCTDG;
    private RelativeLayout view;
    private LinearLayout btnCTSP, btnCTDG, btnChat, btnThemGH, btnMua;
    private RatingBar rbSP;
    private ViewFlipper viewFlipper;
    private ChatRetrofit chatRetrofit;
    private SanPham sanPham;
    private Handler slideHandler;

    private SanPhamRetrofit sanPhamRetrofit;
    private RetrofitService retrofitService;
    private ProgressBar progressBar;

    private SharedPreferencesManager sharedPreferencesManager;
    private MyAuth myAuth;
    public static final int start = 1;
    private ArrayList<GioHang> listSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        initView();
        sharedPreferencesManager = new SharedPreferencesManager(this);
        retrofitService = new RetrofitService();
        getSanPham();
        slideHandler = new Handler(Looper.getMainLooper());
        btn_muangay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listSend = new ArrayList<>();
                String userId = sharedPreferencesManager.getUserId();
                GioHang gioHang = new GioHang(sanPham.get_id(),userId,start);
                listSend.add(gioHang);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("listData", listSend);
                Intent intent = new Intent(SanPhamActivity.this, DatHangActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        imgGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SanPhamActivity.this, GioHangActivity.class));
            }
        });
        btnThemGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                startActivity(intent);
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logicChat();
            }
        });
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = sharedPreferencesManager.getUserId();
                SanPhamYeuThich sanPhamYeuThich = new SanPhamYeuThich(sanPham.get_id(), userId);
                Log.d("FavoriteClick", "Favorite icon clicked"+ userId +" "+sanPham.get_id() );

                SanPhamYTRetrofit sanPhamYTRetrofit = retrofitService.getRetrofit().create(SanPhamYTRetrofit.class);
                sanPhamYTRetrofit.checkSanPhamYeuThich(sanPhamYeuThich).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            boolean isFavorite = response.body();
                            if (isFavorite) {

                                sanPhamYTRetrofit.deleteSanPhamYeuThich(sanPham.get_id()).enqueue(new Callback<Void>() {

                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        imgFavorite.setImageResource(R.drawable.ic_chuathich);
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        // Xử lý lỗi
                                    }
                                });
                            } else {
                                // Sản phẩm chưa yêu thích, thực hiện thêm
                                sanPhamYTRetrofit.addSanPhamYeuThich(sanPhamYeuThich).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        imgFavorite.setImageResource(R.drawable.ic_dathich);
                                        Log.d("FavoriteCheck", "Product ID: " + sanPham.get_id() + ", User ID: " + userId);

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        // Xử lý lỗi
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        // Xử lý lỗi
                    }
                });
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
        int maxSL = sanPham.getSoLuong();

        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_sanpham, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        ImageView img_anhSP = view.findViewById(R.id.bssp_img);
        ImageView btnCong = view.findViewById(R.id.bssp_btn_congSL);
        ImageView btnTru = view.findViewById(R.id.bssp_btn_truSL);
        EditText ipSoLuong = view.findViewById(R.id.bssp_ip_soLuong);
        TextView tvTen = view.findViewById(R.id.bssp_ten);
        TextView tvGia = view.findViewById(R.id.bssp_gia);
        TextView tvKho = view.findViewById(R.id.bssp_kho);
        Button btnThemGioHang = view.findViewById(R.id.bssp_btn_add);


        Picasso.get().load(sanPham.getAnhSanPham()).into(img_anhSP);
        tvGia.setText(String.format("%,.0f", sanPham.getGiaTien()) + "₫");
        tvKho.setText("Kho: " + maxSL);
        tvTen.setText(sanPham.getTenSanPham());
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHangRetrofit gioHangRetrofit = retrofitService.retrofit.create(GioHangRetrofit.class);
                String userId = sharedPreferencesManager.getUserId();
                Call<MyAuth> getGioHang = gioHangRetrofit.themGioHang(new GioHang(sanPham.get_id(),
                        userId, Integer.parseInt(ipSoLuong.getText().toString().trim())));
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
        tvGiaSP.setText(String.format("%,.0f", sanPham.getGiaTien()) + "₫");
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
        imgFavorite = findViewById(R.id.asp_img_favorite);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

}
