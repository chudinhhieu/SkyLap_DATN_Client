package com.example.skylap_datn_md03.adapter;

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
        DanhGia review = reviews.get(position);
//        ToDo:
//        holder.textViewReviewerName.setText(review.get_id());
//        holder.ratingBar.setRating(review.getSoSao());
//        holder.textViewReviewDate.setText(review.getThoiGianDG());
//
//        Picasso.get().load(review.getAnhDG()).placeholder(R.drawable.ic_account_ip).into(holder.imageViewUserAvatar);
//
//        Picasso.get().load(review.getAnhDG()).placeholder(R.drawable.ic_placeholder_image).into(holder.imageViewLaptop);
//        holder.textViewLaptopModel.setText(review.get_idSanPham());

        // Handle Edit Button click here, if necessary
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit action, e.g., open an edit review activity or dialog
            }
        });
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
    }
}