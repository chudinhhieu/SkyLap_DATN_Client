package com.example.skylap_datn_md03.adapter;



import android.content.Context;
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
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.data.models.ThongBao;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
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

    private List<ThongBao> list;
    private Context context;
    private SanPhamRetrofit sanPhamRetrofit;
    private RetrofitService retrofitService;
    private SanPham sanPham;
    public ThongBaoAdapter(List<ThongBao> list, Context context) {
        Collections.reverse(list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_bao, parent, false);
        return new ThongBaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongBaoViewHolder holder, int position) {
        ThongBao thongBao = list.get(position);
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
