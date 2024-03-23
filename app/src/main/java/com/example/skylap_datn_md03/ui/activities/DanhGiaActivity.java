package com.example.skylap_datn_md03.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.ImageAdapter;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.DanhGiaRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.dialogs.CustomToast;
import com.example.skylap_datn_md03.utils.RealPathUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhGiaActivity extends AppCompatActivity {
    private static final int PICK_IMAGES_REQUEST = 1;
    private ArrayList<Uri> selectedImages = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private int soSao;
    private EditText edtNoidung;
    private SanPham sanPham;
    private DanhGiaRetrofit danhGiaRetrofit;
    private SanPhamRetrofit sanPhamRetrofit;
    private RetrofitService retrofitService;
    private RatingBar rtbRate;
    private TextView tvDanhgia, tvSanPhamDg;
    private Button btnGuidg;
    private ImageView imgAddphoto, imgSanPhamDg, btnExit;
    private RecyclerView rcyAnhDG;
    private LinearLayout ln_itemAnh;
    private MyAuth myAuth;
    private ProgressBar progressBar;
    private RelativeLayout viewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);

        rtbRate = findViewById(R.id.rtbRate);
        tvDanhgia = findViewById(R.id.tvDanhgia);
        btnGuidg = findViewById(R.id.btnGuidg);
        imgAddphoto = findViewById(R.id.imgAddphoto);
        btnExit = findViewById(R.id.adg_img_back);
        tvSanPhamDg = findViewById(R.id.tvSanPhamDG);
        imgSanPhamDg = findViewById(R.id.imgSanPhamDG);
        progressBar = findViewById(R.id.progressBar);
        ln_itemAnh = findViewById(R.id.ln_itemAnh);
        viewMain = findViewById(R.id.adg_view_main);
        edtNoidung = findViewById(R.id.edtNoidung);
        rcyAnhDG = findViewById(R.id.rcy_AnhDG);
        rcyAnhDG.setLayoutManager(new GridLayoutManager(this, 3));

        retrofitService = new RetrofitService();

        getSanPham();
        rtbRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                switch ((int) rating) {
                    case 1:
                        tvDanhgia.setText("Rất tệ");
                        soSao = 1;
                        break;
                    case 2:
                        tvDanhgia.setText("Tệ");
                        soSao = 2;
                        break;
                    case 3:
                        tvDanhgia.setText("Bình thường");
                        soSao = 3;
                        break;
                    case 4:
                        tvDanhgia.setText("Tốt");
                        soSao = 4;
                        break;
                    case 5:
                        tvDanhgia.setText("Tuyệt vời");
                        soSao = 5;
                        break;
                }
            }
        });

        btnGuidg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDanhGia();
            }
        });

        imgAddphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void getSanPham() {
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        String idSanPham = getIntent().getStringExtra("idSanPham");
        if (idSanPham != null) {
            Call<SanPham> getSanPham = sanPhamRetrofit.getSanPhamByID(idSanPham);
            getSanPham.enqueue(new Callback<SanPham>() {
                @Override
                public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                    sanPham = response.body();
                    tvSanPhamDg.setText(sanPham.getTenSanPham());
                    Picasso.get().load(sanPham.getAnhSanPham()).into(imgSanPhamDg);
                }

                @Override
                public void onFailure(Call<SanPham> call, Throwable t) {

                }
            });
        }
    }

    private void postDanhGia() {
        progressBar.setVisibility(View.VISIBLE);
        viewMain.setVisibility(View.GONE);
        danhGiaRetrofit = retrofitService.retrofit.create(DanhGiaRetrofit.class);
        String idDonHang = getIntent().getStringExtra("idDonHang");
        RequestBody reqSoSao = RequestBody.create(MediaType.parse("multipart/form-data"), soSao + "");
        RequestBody reqNoiDung = RequestBody.create(MediaType.parse("multipart/form-data"), edtNoidung.getText().toString().trim());
        List<MultipartBody.Part> imageParts = new ArrayList<>();
        if (selectedImages.size() == 0) {
            Toast.makeText(DanhGiaActivity.this, "Chưa chọn ảnh!", Toast.LENGTH_SHORT).show();
            return;
        }
        for (Uri selectedUri : selectedImages) {
            String realPath = RealPathUtil.getRealPath(DanhGiaActivity.this, selectedUri);
            File imageFile = new File(realPath);
            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageRequestBody);
            imageParts.add(imagePart);
        }
        if (idDonHang != null) {
            Call<MyAuth> postDanhGia = danhGiaRetrofit.postDanhGia(idDonHang, reqSoSao, reqNoiDung, imageParts);
            postDanhGia.enqueue(new Callback<MyAuth>() {
                @Override
                public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
                    myAuth = response.body();
                    CustomToast.showToast(DanhGiaActivity.this, myAuth.getMessage());
                    progressBar.setVisibility(View.GONE);
                    finish();
                }

                @Override
                public void onFailure(Call<MyAuth> call, Throwable t) {
                    CustomToast.showToast(DanhGiaActivity.this, myAuth.getMessage());
                }
            });
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES_REQUEST);
        imageAdapter = new ImageAdapter(selectedImages, this);
        rcyAnhDG.setAdapter(imageAdapter);

        ln_itemAnh.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    selectedImages.add(imageUri);
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                selectedImages.add(imageUri);
            }
            imageAdapter.notifyDataSetChanged();
        }
    }
}