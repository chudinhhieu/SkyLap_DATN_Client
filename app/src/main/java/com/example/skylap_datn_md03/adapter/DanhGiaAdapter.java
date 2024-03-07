package com.example.skylap_datn_md03.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.DanhGia;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.ReviewViewHolder> {

    private final List<DanhGia> reviews;

    public DanhGiaAdapter(List<DanhGia> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
//        DanhGia review = reviews.get(position);
//
//        holder.textViewReviewerName.setText(review.get_idNguoiMua());
//        holder.ratingBar.setRating(review.getSoSao());
//        holder.textViewReviewDate.setText((CharSequence) review.getThoiGianDG());
//
//        Picasso.get().load((Uri) review.getAnhDG()).placeholder(R.drawable.ic_account_ip).into(holder.imageViewUserAvatar);
//        Picasso.get().load((Uri) review.getAnhDG()).placeholder(R.drawable.anh_mau).into(holder.imageViewLaptop);
//
//        holder.textViewLaptopModel.setText(review.get_idSanPham());
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

        public ReviewViewHolder(View itemView) {
            super(itemView);
            imageViewUserAvatar = itemView.findViewById(R.id.imageViewUserAvatar);
            textViewReviewerName = itemView.findViewById(R.id.textViewReviewerName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            textViewReviewDate = itemView.findViewById(R.id.textViewReviewDate);
            imageViewLaptop = itemView.findViewById(R.id.imageViewLaptop);
            textViewLaptopModel = itemView.findViewById(R.id.textViewLaptopModel);
        }
    }
}
