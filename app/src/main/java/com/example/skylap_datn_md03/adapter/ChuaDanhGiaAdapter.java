package com.example.skylap_datn_md03.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.activities.DanhGiaActivity;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChuaDanhGiaAdapter extends RecyclerView.Adapter<ChuaDanhGiaAdapter.CDGViewHolder> {

    private Context context;
    private List<DonHang> donHangList;

    public ChuaDanhGiaAdapter(Context context, List<DonHang> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
    }

    @NonNull
    @Override
    public ChuaDanhGiaAdapter.CDGViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_not_review, parent, false);
        return new ChuaDanhGiaAdapter.CDGViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CDGViewHolder holder, int position) {
        DonHang donHang = donHangList.get(position);
        RetrofitService retrofitService = new RetrofitService();
        SanPhamRetrofit sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        sanPhamRetrofit.getSanPhamByID(donHang.getIdSanPham()).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String productName = response.body().getTenSanPham();
                    holder.ten.setText(productName);
                    if (!response.body().getAnhSanPham().isEmpty()) {
                        Picasso.get().load(response.body().getAnhSanPham()).into(holder.anh);
                    }
                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DanhGiaActivity.class);
                intent.putExtra("idDonHang", donHang.get_id());
                intent.putExtra("idSanPham", donHang.getIdSanPham());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (donHangList != null) return donHangList.size();
        return 0;
    }

    public class CDGViewHolder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView ten;
        Button button;
        public CDGViewHolder(View itemView) {
            super(itemView);
            anh = itemView.findViewById(R.id.itnrv_anh);
            ten = itemView.findViewById(R.id.itnrv_ten);
            button = itemView.findViewById(R.id.itnrv_button);
        }
    }
    public void reverseList() {
        Collections.reverse(donHangList);
        notifyDataSetChanged();
    }
}


