package com.example.skylap_datn_md03.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.data.models.TrangThai;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.ui.activities.DanhGiaActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChuaDanhGiaAdapter extends RecyclerView.Adapter<ChuaDanhGiaAdapter.ViewHolder> {

    private Context context;
    private List<DonHang> donHangList;
    private LayoutInflater inflater;

    private AccountRetrofit accountRetrofit;
    private SanPhamRetrofit sanPhamRetrofit;

    public ChuaDanhGiaAdapter(Context context, List<DonHang> donHangList, AccountRetrofit accountRetrofit, SanPhamRetrofit sanPhamRetrofit) {
        this.context = context;
        this.donHangList = donHangList;
        this.inflater = LayoutInflater.from(context);
        this.accountRetrofit = accountRetrofit;
        this.sanPhamRetrofit = sanPhamRetrofit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_not_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang donHang = donHangList.get(position);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView ten;
        Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            anh = itemView.findViewById(R.id.itnrv_anh);
            ten = itemView.findViewById(R.id.itnrv_ten);
            button = itemView.findViewById(R.id.itnrv_button);
        }
    }
}


