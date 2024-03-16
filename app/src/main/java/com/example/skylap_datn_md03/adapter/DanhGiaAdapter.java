package com.example.skylap_datn_md03.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.DonHang;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.ReviewViewHolder> {

    private final List<DonHang> reviews;

    public DanhGiaAdapter(List<DonHang> reviews) {
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
        DonHang donHang = reviews.get(position);

        if (donHang.getDanhGia() != null) {
            holder.ratingBar.setRating(donHang.getDanhGia().getSoSao());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            holder.textViewReviewDate.setText(dateFormat.format(donHang.getDanhGia().getThoiGianDG()));
            // Assuming you want to display the first image if available
            if (!donHang.getDanhGia().getAnhDG().isEmpty()) {
                Picasso.get().load(donHang.getDanhGia().getAnhDG().get(0)).into(holder.imageViewReview);
            }
            holder.textViewDanhGia.setText(donHang.getDanhGia().get());
        }

        // Here, you might also want to set other information like the product name and user name.
        // This will likely involve additional Retrofit calls unless this information is already included in your DonHang model.
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewReview;
        TextView textViewReviewerName;
        RatingBar ratingBar;
        TextView textViewReviewDate;
        TextView textViewDanhGia;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            imageViewReview = itemView.findViewById(R.id.imageViewReview);
            textViewReviewerName = itemView.findViewById(R.id.textViewReviewerName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            textViewReviewDate = itemView.findViewById(R.id.textViewReviewDate);
            textViewDanhGia = itemView.findViewById(R.id.textViewDanhGia);
        }
    }
}
