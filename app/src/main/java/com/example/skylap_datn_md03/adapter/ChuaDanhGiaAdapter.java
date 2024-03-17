package com.example.skylap_datn_md03.adapter;

import android.content.Context;
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

        // Fetch product name using SanPhamRetrofit
        sanPhamRetrofit.getSanPhamByID(donHang.getIdSanPham()).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SanPham sanPham = response.body();
                    holder.textViewLaptopModel.setText(sanPham.getTenSanPham());
                    // Load product image if available
                    if (!sanPham.getAnhSanPham().isEmpty()) {
                        Picasso.get().load(sanPham.getAnhSanPham()).into(holder.imageViewLaptop);
                    }
                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                holder.textViewLaptopModel.setText("Product info not available");
            }
        });

        if (!donHang.getTrangThai().isEmpty()) {
            TrangThai trangThai = donHang.getTrangThai().get(donHang.getTrangThai().size() - 1);
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = inputFormat.parse(trangThai.getThoiGian());

                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = outputFormat.format(date);

                holder.textViewBoughtDate.setText(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
                holder.textViewBoughtDate.setText("Date parsing error");
            }
        }

        // Handle the review button click event
        holder.buttonReview.setOnClickListener(v -> {
            // Navigate to review submission screen/activity

        });
    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewLaptop;
        TextView textViewLaptopModel, textViewBoughtDate;
        Button buttonReview;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewLaptop = itemView.findViewById(R.id.imageViewLaptop);
            textViewLaptopModel = itemView.findViewById(R.id.textViewLaptopModel);
            textViewBoughtDate = itemView.findViewById(R.id.textViewBoughtDate);
            buttonReview = itemView.findViewById(R.id.button_review);
        }
    }
}


