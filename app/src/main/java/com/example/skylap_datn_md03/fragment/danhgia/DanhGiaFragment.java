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
import com.example.skylap_datn_md03.adapter.DanhGiaAdapter;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.retrofitController.DanhGiaRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhGiaFragment extends Fragment {

    private RecyclerView recyclerView;
    private DanhGiaAdapter danhGiaAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_gia, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_danh_gia);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(getContext());
        String userId = sharedPreferencesManager.getUserId();
        RetrofitService retrofitService = new RetrofitService();
        DanhGiaRetrofit danhGiaRetrofit = retrofitService.getRetrofit().create(DanhGiaRetrofit.class);

        Call<List<DonHang>> call = danhGiaRetrofit.getDaDanhGia(userId);
        call.enqueue(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                if(response.isSuccessful()) {
                    List<DonHang> donHangs = response.body();
                    danhGiaAdapter = new DanhGiaAdapter(donHangs);
                    recyclerView.setAdapter(danhGiaAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {
                // Log error or handle failure
            }
        });

        return view;
    }
}
