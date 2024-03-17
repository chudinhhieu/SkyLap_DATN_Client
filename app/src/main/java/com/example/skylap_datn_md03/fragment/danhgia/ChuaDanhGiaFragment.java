package com.example.skylap_datn_md03.fragment.danhgia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.ChuaDanhGiaAdapter;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.DanhGiaRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChuaDanhGiaFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChuaDanhGiaAdapter chuaDanhGiaAdapter;
    private RetrofitService retrofitService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chua_danh_gia, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_chua_danh_gia);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        retrofitService = new RetrofitService();
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
        String userId = sharedPreferencesManager.getUserId();

        AccountRetrofit accountRetrofit = retrofitService.getRetrofit().create(AccountRetrofit.class);
        SanPhamRetrofit sanPhamRetrofit = retrofitService.getRetrofit().create(SanPhamRetrofit.class);
        DanhGiaRetrofit danhGiaRetrofit = retrofitService.getRetrofit().create(DanhGiaRetrofit.class);

        // Fetch unreviewed orders
        Call<List<DonHang>> call = danhGiaRetrofit.getChuaDanhGia(userId);
        call.enqueue(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<DonHang> donHangs = response.body();
                    chuaDanhGiaAdapter = new ChuaDanhGiaAdapter(getContext(), donHangs, accountRetrofit, sanPhamRetrofit);
                    recyclerView.setAdapter(chuaDanhGiaAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {
                // Handle failure
            }
        });

        return view;
    }
}
