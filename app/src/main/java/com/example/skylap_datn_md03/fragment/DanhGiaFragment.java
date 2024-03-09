package com.example.skylap_datn_md03.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.skylap_datn_md03.data.models.DanhGia;
import com.example.skylap_datn_md03.retrofitController.DanhGiaRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhGiaFragment extends Fragment {
    private RecyclerView recyclerView;
    private DanhGiaAdapter danhGiaAdapter;
    private DanhGiaRetrofit danhGiaRetrofit;
    private RetrofitService retrofitService;
    private String idAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_gia, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_danh_gia);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        retrofitService = new RetrofitService();

        loadDanhGia();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("id_user_auth", Context.MODE_PRIVATE);
        idAccount = sharedPreferences.getString("uid", null);
    }

    private void loadDanhGia() {
        // Check if idAccount is set
        if (idAccount == null || idAccount.isEmpty()) {
            Log.e("DanhGiaFragment", "Error: idAccount not set");
            return;
        } else {
            Log.d("DanhGiaFragment", "ID Account: " + idAccount);
        }

        // Initialize retrofitService
        retrofitService = new RetrofitService();

        // Initialize danhGiaRetrofit
        danhGiaRetrofit = retrofitService.retrofit.create(DanhGiaRetrofit.class);

        // Make API call to get danh gia
        Call<List<DanhGia>> call = danhGiaRetrofit.getDaDanhGia(idAccount);
        call.enqueue(new Callback<List<DanhGia>>() {
            @Override
            public void onResponse(Call<List<DanhGia>> call, Response<List<DanhGia>> response) {
                if (response.isSuccessful()) {
                    List<DanhGia> danhGiaList = response.body();
                    danhGiaAdapter = new DanhGiaAdapter(danhGiaList);
                    recyclerView.setAdapter(danhGiaAdapter);
                } else {
                    // Handle error when getting data from server
                    Log.e("DanhGiaFragment", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<DanhGia>> call, Throwable t) {
                // Handle error when calling API
                Log.e("DanhGiaFragment", "Error: " + t.getMessage());
            }
        });
    }

}
