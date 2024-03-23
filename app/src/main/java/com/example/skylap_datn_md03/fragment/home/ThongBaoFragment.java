package com.example.skylap_datn_md03.fragment.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.SanPhamYeuThichAdapter;
import com.example.skylap_datn_md03.adapter.ThongBaoAdapter;
import com.example.skylap_datn_md03.data.models.ThongBao;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.retrofitController.SanPhamYTRetrofit;
import com.example.skylap_datn_md03.retrofitController.ThongBaoRetrofit;
import com.example.skylap_datn_md03.ui.activities.auth.LoginActivity;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongBaoFragment extends Fragment {
    private RecyclerView recyclerView;
    private ThongBaoAdapter thongBaoAdapter;
    private SharedPreferencesManager sharedPreferencesManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_bao, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferencesManager = new SharedPreferencesManager(getContext());
        String userId = sharedPreferencesManager.getUserId();
        recyclerView = view.findViewById(R.id.fmtb_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RetrofitService retrofitService = new RetrofitService();
        ThongBaoRetrofit apiTB = retrofitService.getRetrofit().create(ThongBaoRetrofit.class);
        apiTB.getListThongBaoByIDAccount(userId).enqueue(new Callback<List<ThongBao>>() {
            @Override
            public void onResponse(Call<List<ThongBao>> call, Response<List<ThongBao>> response) {
                Toast.makeText(getContext(), ""+response.body().size(), Toast.LENGTH_SHORT).show();
                thongBaoAdapter = new ThongBaoAdapter(response.body(),getContext());
                recyclerView.setAdapter(thongBaoAdapter);
            }

            @Override
            public void onFailure(Call<List<ThongBao>> call, Throwable t) {
                Toast.makeText(getContext(), ""+t, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
