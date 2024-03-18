package com.example.skylap_datn_md03.adapter;

import android.content.Context;
import android.net.http.UrlRequest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.ReviewViewHolder> {

    private final List<DonHang> reviews;
    private final Context context;
    private final AccountRetrofit accountRetrofit;
    private final SanPhamRetrofit sanPhamRetrofit;

    public DanhGiaAdapter(List<DonHang> reviews, Context context, AccountRetrofit accountRetrofit, SanPhamRetrofit sanPhamRetrofit) {
        this.reviews = reviews;
        this.context = context;
        this.accountRetrofit = accountRetrofit;
        this.sanPhamRetrofit = sanPhamRetrofit;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        DonHang donHang = reviews.get(position);

        // Fetch and display user name
        accountRetrofit.getAccountById(donHang.getIdAccount()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String userName = response.body().getTaiKhoan();
                    holder.textViewReviewerName.setText(userName);
                    if (response.body().getAvatar() == null) {
                        Picasso.get().load(R.drawable.avatar_main).into(holder.imageViewUserAvatar);
                    } else {
                        Picasso.get().load(response.body().getAvatar()).into(holder.imageViewUserAvatar);
                    }
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                holder.textViewReviewerName.setText("Unknown User");
            }
        });

        // Fetch and display product details
        sanPhamRetrofit.getSanPhamByID(donHang.getIdSanPham()).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String productName = response.body().getTenSanPham();
                    holder.textViewLaptopModel.setText(productName);
                    if (!response.body().getAnhSanPham().isEmpty()) {
                        Picasso.get().load(response.body().getAnhSanPham()).into(holder.imageViewLaptop);
                    }
                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                holder.textViewLaptopModel.setText("Unknown Product");
            }
        });

        // Set review details
        holder.ratingBar.setRating(donHang.getDanhGia().getSoSao());
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
        holder.textViewReviewDate.setText(dateFormat.format(donHang.getDanhGia().getThoiGian()));
        holder.textViewDanhGia.setText(donHang.getDanhGia().getNoiDung());
        if (!donHang.getDanhGia().getAnh().isEmpty()) {
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUserAvatar;
        TextView textViewReviewerName, textViewReviewDate, textViewDanhGia, textViewLaptopModel;
        RatingBar ratingBar;
        ImageView imageViewLaptop;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            imageViewUserAvatar = itemView.findViewById(R.id.imageViewUserAvatar);
            textViewReviewerName = itemView.findViewById(R.id.textViewReviewerName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            textViewReviewDate = itemView.findViewById(R.id.textViewReviewDate);
            textViewDanhGia = itemView.findViewById(R.id.textViewDanhGia);
            imageViewLaptop = itemView.findViewById(R.id.imageViewLaptop);
            textViewLaptopModel = itemView.findViewById(R.id.textViewLaptopModel);
        }
    }
}

