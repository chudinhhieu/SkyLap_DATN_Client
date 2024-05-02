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
import com.example.skylap_datn_md03.data.models.TrangThai;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.DanhGiaRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.utils.DateUtils;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChuaDanhGiaFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChuaDanhGiaAdapter chuaDanhGiaAdapter;
    private RetrofitService retrofitService;
    private DanhGiaRetrofit danhGiaRetrofit;
    private SharedPreferencesManager sharedPreferencesManager;
    private String userId;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chua_danh_gia, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view_chua_danh_gia);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        retrofitService = new RetrofitService();
        sharedPreferencesManager = new SharedPreferencesManager(getContext());
        userId = sharedPreferencesManager.getUserId();
        danhGiaRetrofit = retrofitService.getRetrofit().create(DanhGiaRetrofit.class);
        callChuaDanhGia();
    }

    private void callChuaDanhGia() {

        List<DonHang> list = new ArrayList<>();

        Call<List<DonHang>> call = danhGiaRetrofit.getChuaDanhGia(userId);
        call.enqueue(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DonHang> donHangs = response.body();
                    for (int i = 0; i < donHangs.size(); i++) {
                        DonHang donHang = donHangs.get(i);
                        for (int j = 0; j < donHang.getTrangThai().size(); j++) {
                            TrangThai trangThai = donHang.getTrangThai().get(j);
                            if (trangThai.getIsNow() == true) {
                                if (DateUtils.getDaysDifference(trangThai.getThoiGian()) >= -15 && DateUtils.getDaysDifference(trangThai.getThoiGian()) <= 0) {
                                    list.add(donHang);
                                }
                            }
                        }
                    }
                    chuaDanhGiaAdapter = new ChuaDanhGiaAdapter(getContext(), list);
                    chuaDanhGiaAdapter.reverseList();
                    recyclerView.setAdapter(chuaDanhGiaAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        callChuaDanhGia();
    }
}
