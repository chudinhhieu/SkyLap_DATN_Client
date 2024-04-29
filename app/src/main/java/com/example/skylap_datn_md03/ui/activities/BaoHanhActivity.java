package com.example.skylap_datn_md03.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.ImageAdapter;
import com.example.skylap_datn_md03.data.models.BaoHanh;
import com.example.skylap_datn_md03.data.models.DVHCVN.Tinh;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.DanhGiaRetrofit;
import com.example.skylap_datn_md03.retrofitController.DonHangRetrofit;
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

public class BaoHanhActivity extends AppCompatActivity {
    private static final int PICK_IMAGES_REQUEST = 56;
    private ArrayList<Uri> selectedImages = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private int soSao;
    private EditText edtNoidung;
    private SanPham sanPham;
    private DanhGiaRetrofit danhGiaRetrofit;
    private DonHangRetrofit donHangRetrofit;
    private SanPhamRetrofit sanPhamRetrofit;
    private RetrofitService retrofitService;
    private Spinner spinner;
    private TextView  tvSanPham,tvRamRom;
    private Button btnGuiTH;
    private ImageView imgAddphoto, imgSanPham, btnExit;
    private RecyclerView rcyAnhDG;
    private LinearLayout ln_itemAnh;
    private MyAuth myAuth;
    private ProgressBar progressBar;
    private RelativeLayout viewMain;
    private String idBaoHanh;
    private List<BaoHanh> baoHanhList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_hanh);
        btnGuiTH = findViewById(R.id.btnGuiTH);
        spinner = findViewById(R.id.spn_traHang);
        imgAddphoto = findViewById(R.id.imgAddphotoTH);
        btnExit = findViewById(R.id.ath_img_back);
        tvSanPham = findViewById(R.id.tvSanPhamTH);
        tvRamRom = findViewById(R.id.tvRamRomTH);
        imgSanPham = findViewById(R.id.imgSanPhamTH);
        progressBar = findViewById(R.id.progressBar);
        ln_itemAnh = findViewById(R.id.ln_itemAnhTH);
        viewMain = findViewById(R.id.ath_view_main);
        edtNoidung = findViewById(R.id.edtLyDoTH);
        rcyAnhDG = findViewById(R.id.rcy_AnhTH);
        rcyAnhDG.setLayoutManager(new GridLayoutManager(this, 3));
        retrofitService = new RetrofitService();
        getSanPham();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String imei = spinner.getSelectedItem().toString();
                for (BaoHanh bh : baoHanhList) {
                    if (bh.getImei().equals(imei)) {
                        idBaoHanh = bh.get_id();
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnGuiTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putBaoHanh();
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
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES_REQUEST);
        imageAdapter = new ImageAdapter(selectedImages, this);
        rcyAnhDG.setAdapter(imageAdapter);
        ln_itemAnh.setVisibility(View.GONE);
    }
    private void getSanPham() {
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        donHangRetrofit = retrofitService.retrofit.create(DonHangRetrofit.class);
        String idSanPham = getIntent().getStringExtra("idSanPham");
        String idDonHang = getIntent().getStringExtra("idDonHang");
        if (idDonHang != null) {
            donHangRetrofit.GetDonHangById(idDonHang).enqueue(new Callback<DonHang>() {
                @Override
                public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                    String idBienThe = response.body().getIdBienThe();
                    baoHanhList = response.body().getBaoHanh();
                    ArrayList<String> bhList = new ArrayList<>();
                    for (BaoHanh bh : baoHanhList) {
                        bhList.add(bh.getImei());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(BaoHanhActivity.this, android.R.layout.simple_spinner_item,bhList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    if (idSanPham != null) {
                        Call<SanPham> getSanPham = sanPhamRetrofit.getSanPhamByID(idSanPham);
                        getSanPham.enqueue(new Callback<SanPham>() {
                            @Override
                            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                                sanPham = response.body();
                                for (int i = 0; i < sanPham.getBienThe().size(); i++) {
                                    if (sanPham.getBienThe().get(i).get_id().equals(idBienThe)){
                                        tvRamRom.setText(sanPham.getBienThe().get(i).getRam() + " + " + sanPham.getBienThe().get(i).getRom());
                                    }
                                }
                                Picasso.get().load(sanPham.getAnhSanPham()).into(imgSanPham);
                                tvSanPham.setText(sanPham.getTenSanPham());
                            }

                            @Override
                            public void onFailure(Call<SanPham> call, Throwable t) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<DonHang> call, Throwable t) {

                }
            });
        }

    }
    private void putBaoHanh() {
        String idDonHang = getIntent().getStringExtra("idDonHang");
        progressBar.setVisibility(View.VISIBLE);
        viewMain.setVisibility(View.GONE);
        donHangRetrofit = retrofitService.retrofit.create(DonHangRetrofit.class);
        RequestBody reqLyDo = RequestBody.create(MediaType.parse("multipart/form-data"), edtNoidung.getText().toString().trim());
        RequestBody reqTinhTrang = RequestBody.create(MediaType.parse("multipart/form-data"), "1");
        List<MultipartBody.Part> imageParts = new ArrayList<>();
        if (selectedImages.size() == 0) {
            Toast.makeText(BaoHanhActivity.this, "Chưa chọn ảnh!", Toast.LENGTH_SHORT).show();
            return;
        }
        for (Uri selectedUri : selectedImages) {
            String realPath = RealPathUtil.getRealPath(BaoHanhActivity.this, selectedUri);
            File imageFile = new File(realPath);
            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageRequestBody);
            imageParts.add(imagePart);
        }
        if (idBaoHanh != null) {
            Call<MyAuth> putBH = donHangRetrofit.putBaoHanh(idDonHang,idBaoHanh, reqLyDo, reqTinhTrang, imageParts);
            putBH.enqueue(new Callback<MyAuth>() {
                @Override
                public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
                    myAuth = response.body();
                    CustomToast.showToast(BaoHanhActivity.this, myAuth.getMessage());
                    progressBar.setVisibility(View.GONE);
                    finish();
                }
                @Override
                public void onFailure(Call<MyAuth> call, Throwable t) {
                    Log.d("zzzzzzz", "onFailure: "+t);
                }
            });
        }
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