package com.example.skylap_datn_md03.adapter;

import static android.util.Log.d;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.HangSX;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HangSanPhamAdapter extends RecyclerView.Adapter<HangSanPhamAdapter.LoaiSanPhamViewHolder>{
    private List<HangSX> list;
    private Context context;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(String hangId);
    }
    public HangSanPhamAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }
    public void setList(List<HangSX> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public LoaiSanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loai_sanpham,parent,false);
        return new LoaiSanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSanPhamViewHolder holder, int position) {
        HangSX hangSX = list.get(position);
        if (hangSX == null) return;
        Picasso.get().load(hangSX.getLogo()).into(holder.img_anh);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(hangSX.get_id());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public final class LoaiSanPhamViewHolder extends RecyclerView.ViewHolder{
        ImageView img_anh;
        public LoaiSanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            img_anh = itemView.findViewById(R.id.item_loai_sanPham_img);
        }
    }
}
