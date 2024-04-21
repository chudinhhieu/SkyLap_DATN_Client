package com.example.skylap_datn_md03.adapter;

import android.content.Context;
import android.net.http.UrlRequest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.Account;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.example.skylap_datn_md03.utils.SharedPreferencesManager;
import com.google.android.play.integrity.internal.c;
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

    public DanhGiaAdapter(List<DonHang> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
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
        RetrofitService retrofitService = new RetrofitService();
        AccountRetrofit accountRetrofit = retrofitService.retrofit.create(AccountRetrofit.class);
        SanPhamRetrofit sanPhamRetrofit = retrofitService.retrofit.create(SanPhamRetrofit.class);
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
        if ((donHang.getDanhGia().getAnh()) != null) {
            holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            ImageAdapter2 imageAdapter = new ImageAdapter2(donHang.getDanhGia().getAnh(), context);
            holder.recyclerView.setAdapter(imageAdapter);
        }
    }

    @Override
    public int getItemCount() {
        if (reviews != null) return reviews.size();
        return 0;
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUserAvatar;
        TextView textViewReviewerName, textViewReviewDate, textViewDanhGia, textViewLaptopModel;
        RatingBar ratingBar;
        ImageView imageViewLaptop;
        RecyclerView recyclerView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            imageViewUserAvatar = itemView.findViewById(R.id.imageViewUserAvatar);
            textViewReviewerName = itemView.findViewById(R.id.textViewReviewerName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            textViewReviewDate = itemView.findViewById(R.id.textViewReviewDate);
            textViewDanhGia = itemView.findViewById(R.id.textViewDanhGia);
            imageViewLaptop = itemView.findViewById(R.id.imageViewLaptop);
            textViewLaptopModel = itemView.findViewById(R.id.textViewLaptopModel);
            recyclerView = itemView.findViewById(R.id.itrv_anhDG);
        }
    }
}

