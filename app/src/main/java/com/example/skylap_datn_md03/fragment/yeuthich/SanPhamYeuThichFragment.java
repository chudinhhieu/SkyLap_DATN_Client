package com.example.skylap_datn_md03.fragment.yeuthich;

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

public class SanPhamYeuThichFragment extends Fragment {
    private RecyclerView recyclerView;
    private SanPhamYeuThichAdapter adapter;
    private RetrofitService retrofitService;
    private SanPhamRetrofit sanPhamRetrofit;
    private SharedPreferencesManager sharedPreferencesManager;
    private List<SanPham> sanPhamList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_san_pham_yeu_thich, container, false);
        recyclerView = view.findViewById(R.id.rvSanPhamYeuThich);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        sharedPreferencesManager = new SharedPreferencesManager(getActivity());
        retrofitService = new RetrofitService();
        sanPhamRetrofit = retrofitService.getRetrofit().create(SanPhamRetrofit.class);

        // Khởi tạo Adapter và gán vào RecyclerView
        adapter = new SanPhamYeuThichAdapter(getActivity(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        loadSanPhamYeuThich();

        return view;
    }

    private void loadSanPhamYeuThich() {
        String userId = sharedPreferencesManager.getUserId();
        SanPhamYTRetrofit sanPhamYTRetrofit = retrofitService.getRetrofit().create(SanPhamYTRetrofit.class);
        Call<List<SanPhamYeuThich>> call = sanPhamYTRetrofit.getSanPhamYeuThichByAccount(userId);

        call.enqueue(new Callback<List<SanPhamYeuThich>>() {
            @Override
            public void onResponse(Call<List<SanPhamYeuThich>> call, Response<List<SanPhamYeuThich>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SanPhamYeuThich> sanPhamYeuThiches = response.body();
                    final int[] counter = {0};
                    for (SanPhamYeuThich sanPhamYeuThich : sanPhamYeuThiches) {
                        sanPhamRetrofit.getSanPhamByID(sanPhamYeuThich.getIdSanPham()).enqueue(new Callback<SanPham>() {
                            @Override
                            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    sanPhamList.add(response.body());
                                    counter[0]++;
                                    // Kiểm tra nếu tất cả lời gọi API đã hoàn thành
                                    if (counter[0] == sanPhamYeuThiches.size()) {
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<SanPham> call, Throwable t) {
                                // Xử lý lỗi
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamYeuThich>> call, Throwable t) {
                // Xử lý lỗi
            }
        });
    }

}

