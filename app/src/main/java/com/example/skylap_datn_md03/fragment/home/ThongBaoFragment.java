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
import android.widget.TextView;
import android.widget.Toast;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.adapter.SanPhamYeuThichAdapter;
import com.example.skylap_datn_md03.adapter.ThongBaoAdapter;
import com.example.skylap_datn_md03.data.models.MyAuth;
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
    private TextView tvDocTatCa;
    private ThongBaoAdapter thongBaoAdapter;
    private SharedPreferencesManager sharedPreferencesManager;
    private  String userId;
    private RetrofitService retrofitService;
    private ThongBaoRetrofit apiTB;
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
        recyclerView = view.findViewById(R.id.fmtb_recyclerView);
        tvDocTatCa = view.findViewById(R.id.tvDocTatCa);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         userId = sharedPreferencesManager.getUserId();
        retrofitService= new RetrofitService();
        apiTB = retrofitService.getRetrofit().create(ThongBaoRetrofit.class);
        tvDocTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiTB.daXemAll(userId).enqueue(new Callback<MyAuth>() {
                    @Override
                    public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
                        getList();
                    }

                    @Override
                    public void onFailure(Call<MyAuth> call, Throwable t) {

                    }
                });
            }
        });
        getList();
    }

    private void getList() {
        apiTB.getListThongBaoByIDAccount(userId).enqueue(new Callback<List<ThongBao>>() {
            @Override
            public void onResponse(Call<List<ThongBao>> call, Response<List<ThongBao>> response) {
                List<ThongBao> thongBaoList = response.body();
                thongBaoAdapter = new ThongBaoAdapter(thongBaoList, getContext());
                recyclerView.setAdapter(thongBaoAdapter);

                // Kiểm tra xem có thông báo chưa đọc không
                boolean hasUnreadNotification = false;
                for (ThongBao thongBao : thongBaoList) {
                    if (!thongBao.isDaXem()) {
                        hasUnreadNotification = true;
                        break;
                    }
                }

                // Nếu có thông báo chưa đọc, hiển thị nút "Đọc tất cả", ngược lại, ẩn nút "Đọc tất cả"
                if (hasUnreadNotification) {
                    tvDocTatCa.setVisibility(View.VISIBLE);
                } else {
                    tvDocTatCa.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ThongBao>> call, Throwable t) {
                Toast.makeText(getContext(), "" + t, Toast.LENGTH_SHORT).show();
            }
        });
}

    @Override
    public void onResume() {
        super.onResume();
        getList();
    }
}
