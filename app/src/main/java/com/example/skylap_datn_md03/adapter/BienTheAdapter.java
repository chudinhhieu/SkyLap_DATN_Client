package com.example.skylap_datn_md03.adapter;



import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.BienThe;

import java.util.ArrayList;
import java.util.List;

public class BienTheAdapter extends RecyclerView.Adapter<BienTheAdapter.ImageViewHolder> {

    private List<BienThe> list;
    private Context context;
    private int selectedPosition = 0;
    private OnBienTheSelectedListener listener;

    public BienTheAdapter(List<BienThe> list, Context context, OnBienTheSelectedListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }
    public interface OnBienTheSelectedListener {
        void onBienTheSelected(BienThe bienThe);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bien_the, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        BienThe bienThe = list.get(position);
        int index = position;
        holder.value.setText(bienThe.getRam() +" + "+ bienThe.getRom());
        // Xác định xem mục này có phải là mục được chọn hay không
        if (position == selectedPosition) {
            holder.viewMain.setBackgroundResource(R.drawable.border_width_button_main);
            bienThe.setSelected(true);
        } else {
            holder.viewMain.setBackgroundResource(R.drawable.border_width_button);
            bienThe.setSelected(false);
        }
        // Thiết lập sự kiện click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
                listener.onBienTheSelected(bienThe);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout viewMain;
        TextView value;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            viewMain = itemView.findViewById(R.id.item_bienThe);
            value = itemView.findViewById(R.id.value_bien_the);
        }
    }
}
