package com.example.skylap_datn_md03.adapter;

import android.net.Uri;
import android.util.Log;
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
import com.example.skylap_datn_md03.data.models.DanhGia;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.AccountRetrofit;
import com.example.skylap_datn_md03.retrofitController.DanhGiaRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.retrofitController.SanPhamRetrofit;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
<<<<<<< Updated upstream
=======
import java.util.HashMap;
>>>>>>> Stashed changes
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

    public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.ReviewViewHolder> {

        private final List<DonHang> reviews;
        private final AccountRetrofit accountRetrofit;
        private final SanPhamRetrofit sanPhamRetrofit;

        // Constructor mới với accountRetrofit và sanPhamRetrofit
        public DanhGiaAdapter(List<DonHang> reviews, AccountRetrofit accountRetrofit, SanPhamRetrofit sanPhamRetrofit) {
            this.reviews = reviews;
            this.accountRetrofit = accountRetrofit;
            this.sanPhamRetrofit = sanPhamRetrofit;
        }

<<<<<<< Updated upstream
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
//      Todo:

//        DanhGia review = reviews.get(position)
//        holder.textViewReviewerName.setText("Tên Người Dùng");
//        holder.ratingBar.setRating(review.getSoSao());
//
//        if (review.getThoiGianDG() != null) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            String dateString = dateFormat.format(review.getThoiGianDG());
//            holder.textViewReviewDate.setText(dateString);
//        } else {
//            holder.textViewReviewDate.setText("Không xác định");
//        }
//
//        holder.textViewLaptopModel.setText("Thông Tin Sản Phẩm");
//        holder.textViewDanhGia.setText(review.getDanhGiaSanPham());
//
//        if (review.getAnhDG() != null && !review.getAnhDG().isEmpty()) {
//            Picasso.get().load(Uri.parse(review.getAnhDG().get(0))).into(holder.imageViewLaptop);
//        } else {
//            holder.imageViewLaptop.setImageResource(R.drawable.anh_mau);
//        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUserAvatar;
        TextView textViewReviewerName;
        RatingBar ratingBar;
        TextView textViewReviewDate;
        TextView textViewDanhGia;
        ImageView imageViewLaptop;
        TextView textViewLaptopModel;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            imageViewUserAvatar = itemView.findViewById(R.id.imageViewUserAvatar);
            imageViewLaptop = itemView.findViewById(R.id.imageViewLaptop);
            textViewReviewerName = itemView.findViewById(R.id.textViewReviewerName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            textViewReviewDate = itemView.findViewById(R.id.textViewReviewDate);
            textViewDanhGia = itemView.findViewById(R.id.textViewDanhGia);
            textViewLaptopModel = itemView.findViewById(R.id.textViewLaptopModel);
=======
        @NonNull
        @Override
        public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
            return new ReviewViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
            DonHang donHang = reviews.get(position);
            DanhGia danhGia = donHang.getDanhGia();

            if (donHang.getDanhGia() != null) {
                holder.ratingBar.setRating(donHang.getDanhGia().getSoSao());
            } else {
                holder.ratingBar.setRating(danhGia.getSoSao());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                holder.textViewReviewDate.setText(dateFormat.format(danhGia.getThoiGian()));

                // Hiển thị ảnh đầu tiên trong danh sách ảnh đánh giá như một ví dụ
                if (!danhGia.getAnh().isEmpty()) {
                    Picasso.get().load(danhGia.getAnh().get(0)).into(holder.imageViewLaptop);
                }
            }
        }

        @Override
        public int getItemCount() {
            return reviews.size();
        }

        public static class ReviewViewHolder extends RecyclerView.ViewHolder {
            ImageView imageViewUserAvatar;
            TextView textViewReviewerName;
            RatingBar ratingBar;
            TextView textViewReviewDate;
            ImageView imageViewLaptop;
            TextView textViewLaptopModel;
            Button editButton;

            public ReviewViewHolder(View itemView) {
                super(itemView);
                imageViewUserAvatar = itemView.findViewById(R.id.imageViewUserAvatar);
                textViewReviewerName = itemView.findViewById(R.id.textViewReviewerName);
                ratingBar = itemView.findViewById(R.id.ratingBar);
                textViewReviewDate = itemView.findViewById(R.id.textViewReviewDate);
                imageViewLaptop = itemView.findViewById(R.id.imageViewLaptop);
                textViewLaptopModel = itemView.findViewById(R.id.textViewLaptopModel);
                editButton = itemView.findViewById(R.id.editButton);
            }
>>>>>>> Stashed changes
        }
    }

