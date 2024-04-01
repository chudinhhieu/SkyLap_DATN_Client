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
import com.example.skylap_datn_md03.data.models.KhuyenMai;
import com.example.skylap_datn_md03.ui.activities.ChiTietKhuyenMaiActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class KhuyenMaiAdapter extends RecyclerView.Adapter<KhuyenMaiAdapter.KhuyenMaiViewHolder>{
    private List<KhuyenMai> list;
    private Context context;
    private OnKhuyenMaiClickListener mListener;

    public KhuyenMaiAdapter(List<KhuyenMai> list, Context context){
        this.list = list;
        this.context = context;
    }

    public interface OnKhuyenMaiClickListener {
        void onKhuyenMaiClick(KhuyenMai khuyenMai);
    }

    public void setOnKhuyenMaiClickListener(OnKhuyenMaiClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public KhuyenMaiAdapter.KhuyenMaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giamgia, parent, false);
        return new KhuyenMaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhuyenMaiViewHolder holder, int position) {
        KhuyenMai khuyenMai = list.get(position);
        holder.item_giamgia_mota.setText(khuyenMai.getMoTa());
        holder.item_giamgia_code.setText(khuyenMai.getCode());

        Picasso.get().load(khuyenMai.getAnh()).into(holder.item_giamgia_image);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.item_giamgia_thoigian.setText(dateFormat.format(khuyenMai.getThoiGianKetThuc()));
        holder.btnDungNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onKhuyenMaiClick(khuyenMai);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietKhuyenMaiActivity.class);
                intent.putExtra("khuyenMai", khuyenMai);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public class KhuyenMaiViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_giamgia_image;
        private TextView item_giamgia_mota, item_giamgia_code, item_giamgia_thoigian;
        private Button btnDungNgay;

        public KhuyenMaiViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giamgia_image = itemView.findViewById(R.id.item_giamgia_image);
            item_giamgia_mota = itemView.findViewById(R.id.item_giamgia_mota);
            item_giamgia_code = itemView.findViewById(R.id.item_giamgia_code);
            item_giamgia_thoigian = itemView.findViewById(R.id.item_giamgia_thoigian);
            btnDungNgay = itemView.findViewById(R.id.btn_dungngay);
        }
    }
}