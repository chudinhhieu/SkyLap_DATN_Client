package com.example.skylap_datn_md03.adapter;

import android.app.Dialog;
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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class KhuyenMaiAdapter extends RecyclerView.Adapter<KhuyenMaiAdapter.KhuyenMaiViewHolder>{
    private List<KhuyenMai> list;
    private Context context;
    private OnKhuyenMaiClickListener mListener;
    private Boolean isDatHang;

    public KhuyenMaiAdapter(List<KhuyenMai> list, Context context,Boolean isDatHang){
        this.list = list;
        this.context = context;
        this.isDatHang  = isDatHang;
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
        if (!isDatHang){
            holder.btnDungNgay.setVisibility(View.GONE);
        }
        KhuyenMai khuyenMai = list.get(position);
        holder.item_giamgia_mota.setText(khuyenMai.getMoTa());
        holder.item_giamgia_code.setText(khuyenMai.getCode());

        Picasso.get().load(khuyenMai.getAnh()).into(holder.item_giamgia_image);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.item_giamgia_thoigian.setText("Đến ngày: "+dateFormat.format(khuyenMai.getThoiGianKetThuc()));
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
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_chi_tiet_khuyen_mai);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ImageView imageView = dialog.findViewById(R.id.imgChiTietKhuyenMai);
                TextView moTa =  dialog.findViewById(R.id.tvMoTaChiTiet);
                TextView code =  dialog.findViewById(R.id.tvCodeChiTiet);
                TextView thoiGian =  dialog.findViewById(R.id.tvThoiGianBatDau);
                TextView tienGiam =  dialog.findViewById(R.id.tvSoTienGiam);
                TextView soLuong =  dialog.findViewById(R.id.tvSoLuong);
                Picasso.get().load(khuyenMai.getAnh()).into(imageView);
                moTa.setText(khuyenMai.getMoTa());
                code.setText(khuyenMai.getCode());
                soLuong.setText(khuyenMai.getSoLuong()+"");
                thoiGian.setText(dateFormat.format(khuyenMai.getThoiGianBatDau())+" - "+dateFormat.format(khuyenMai.getThoiGianKetThuc()));
                tienGiam.setText(String.format("%,.0f", khuyenMai.getSoTienGiam()) + "₫");
                dialog.show();
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
