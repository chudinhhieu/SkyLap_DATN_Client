package com.example.skylap_datn_md03.fragment.yeuthich;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.SanPhamYeuThichAdapter;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.data.models.SanPhamYeuThich;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.retrofitController.SanPhamYTRetrofit;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachYeuThichActivity extends AppCompatActivity {
    private RecyclerView rcvDanhSachYeuThich;
    private SanPhamYeuThichAdapter sanPhamYeuThichAdapter;
    private SharedPreferencesManager sharedPreferencesManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_yeu_thich);

        sharedPreferencesManager = new SharedPreferencesManager(this);
        String userId = sharedPreferencesManager.getUserId();

        rcvDanhSachYeuThich = findViewById(R.id.rcvDanhSachYeuThich);
        rcvDanhSachYeuThich.setLayoutManager(new LinearLayoutManager(this));
        sanPhamYeuThichAdapter = new SanPhamYeuThichAdapter(this, new ArrayList<>());
        rcvDanhSachYeuThich.setAdapter(sanPhamYeuThichAdapter);

        fetchSanPhamYeuThich(userId);
    }

    private void fetchSanPhamYeuThich(String idAccount) {
        RetrofitService retrofitService = new RetrofitService();
        SanPhamYTRetrofit apiYT = retrofitService.getRetrofit().create(SanPhamYTRetrofit.class);
        SanPhamRetrofit apiSP = retrofitService.getRetrofit().create(SanPhamRetrofit.class);
        Log.d("DanhSachYeuThich", "Fetching favorite products for account ID: " + idAccount);
        apiYT.getSanPhamYeuThichByAccount(idAccount).enqueue(new Callback<List<SanPhamYeuThich>>() {
            @Override
            public void onResponse(Call<List<SanPhamYeuThich>> call, Response<List<SanPhamYeuThich>> response) {
                Log.d("DanhSachYeuThich", "Received response for favorite products. Size: " + (response.body() != null ? response.body().size() : "null"));
                Log.d("DanhSachYeuThich", "Raw Response: " + response.raw().toString());

                if (response.isSuccessful() && response.body() != null) {
                    List<SanPham> fetchedProducts = new ArrayList<>();
                    List<SanPhamYeuThich> favorites = response.body();
                    for (SanPhamYeuThich favorite : favorites) {
                        Log.d("DanhSachYeuThich", "Fetching product details for ID: " + favorite.getIdSanPham());


                        apiSP.getSanPhamByID(favorite.getIdSanPham()).enqueue(new Callback<SanPham>() {

                            @Override
                            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    fetchedProducts.add(response.body());
                                    // Check if all requests have completed
                                    if (fetchedProducts.size() == favorites.size()) {
                                        sanPhamYeuThichAdapter.updateData(fetchedProducts);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<SanPham> call, Throwable t) {
                                Toast.makeText(DanhSachYeuThichActivity.this, "Error loading product details: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(DanhSachYeuThichActivity.this, "Failed to fetch favorites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamYeuThich>> call, Throwable t) {
                Toast.makeText(DanhSachYeuThichActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
