package com.example.skylap_datn_md03.fragment.donhang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.QuanLyDonHangAdapter;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.retrofitController.DonHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DaGiaoHangFragment extends Fragment {

    private RecyclerView recyclerView;
    private RelativeLayout viewNull;
    private QuanLyDonHangAdapter adapter;
    private RetrofitService retrofitService;
    private DonHangRetrofit donHangRetrofit;
    private SharedPreferencesManager sharedPreferencesManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_da_giao_hang, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewNull = view.findViewById(R.id.view_null_dgh);
        recyclerView = view.findViewById(R.id.rcv_DGH);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sharedPreferencesManager = new SharedPreferencesManager(getContext());
        retrofitService = new RetrofitService();
        donHangRetrofit = retrofitService.retrofit.create(DonHangRetrofit.class);
        getList();

    }
    @Override
    public void onResume() {
        super.onResume();
       getList();
    }

    private void getList() {
        String idAccount = sharedPreferencesManager.getUserId();
        Call<List<DonHang>> getListDonHang = donHangRetrofit.layDonHangDaGiaoHang(idAccount);
        getListDonHang.enqueue(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                if (response.body().isEmpty()) {
                    viewNull.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    adapter = new QuanLyDonHangAdapter(response.body(), getContext());
                    adapter.reverseList();
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {

            }
        });
    }
}