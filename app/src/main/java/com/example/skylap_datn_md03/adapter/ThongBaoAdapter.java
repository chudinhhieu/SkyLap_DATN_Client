package com.example.skylap_datn_md03.adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.MyAuth;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.data.models.ThongBao;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.retrofitController.ThongBaoRetrofit;
import com.example.skylap_datn_md03.ui.activities.ChiTietDonHangActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.ThongBaoViewHolder> {
    private View view;
    private List<ThongBao> list;
    private Context context;
    private SanPhamRetrofit sanPhamRetrofit;
    private RetrofitService retrofitService;
    private SanPham sanPham;
    public ThongBaoAdapter(List<ThongBao> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_bao, parent, false);
        return new ThongBaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongBaoViewHolder holder, int position) {
        ThongBao thongBao = list.get(position);
        if (thongBao.isDaXem()){
            view.setBackgroundResource(R.color.white);
        }else{
            view.setBackgroundResource(R.color.light_blue);
        }
        int index = position;

        holder.tieuDe.setText(thongBao.getTieuDe());
        holder.noiDung.setText(thongBao.getNoiDung());
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
        holder.thoiGian.setText(dateFormat.format(thongBao.getThoiGian()));
        retrofitService = new RetrofitService();
        sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
        Call<SanPham> getSanPham = sanPhamRetrofit.getSanPhamByID(thongBao.getIdSanPham());
        getSanPham.enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                sanPham = response.body();
                Picasso.get().load(sanPham.getAnhSanPham()).into(holder.anh);
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thongBao.isDaXem()){
                    Intent intent = new Intent(context, ChiTietDonHangActivity.class);
                    intent.putExtra("DonHang", list.get(index).getIdDonHang());
                    context.startActivity(intent);
                    return;
                }
                ThongBaoRetrofit thongBaoRetrofit = retrofitService.retrofit.create(ThongBaoRetrofit.class);
                thongBaoRetrofit.daXem(list.get(index).get_id()).enqueue(new Callback<MyAuth>() {
                    @Override
                    public void onResponse(Call<MyAuth> call, Response<MyAuth> response) {
                        Intent intent = new Intent(context, ChiTietDonHangActivity.class);
                        intent.putExtra("DonHang", list.get(index).getIdDonHang());
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<MyAuth> call, Throwable t) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public class ThongBaoViewHolder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView tieuDe,noiDung,thoiGian;
        public ThongBaoViewHolder(@NonNull View itemView) {
            super(itemView);
            anh = itemView.findViewById(R.id.ittb_anh);
            tieuDe = itemView.findViewById(R.id.ittb_tieu_de);
            noiDung = itemView.findViewById(R.id.ittb_noi_dung);
            thoiGian = itemView.findViewById(R.id.ittb_thoi_gian);
        }
    }
}
