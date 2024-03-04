package com.example.skylap_datn_md03.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.GioHang;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder> {
    private List<GioHang> list;
    private Context context;


    public GioHangAdapter(List<GioHang> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public GioHangAdapter.GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham_gio_hang, parent, false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.GioHangViewHolder holder, int position) {
        GioHang gioHang = list.get(position);

        if (gioHang == null) return;
//        Picasso.get().load(gioHang.getAnhSanPham().get(0)).into(holder.img_sanpham_giohang);
//        holder.tv_TenSP_giohang.setText("" + gioHang.getTenSanPham());
//        holder.tv_GiaSP_giohang.setText(formatPrice(gioHang.getGiaSanPham()));
        holder.tv_SoLuong_giohang.setText("" + gioHang.getSoLuong());

    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder{
        ImageView img_sanpham_giohang;
        CheckBox chbGiohang;
        TextView tv_TenSP_giohang, tv_GiaSP_giohang, tv_GiamSL_giohang, tv_TangSL_giohang, tv_SoLuong_giohang;
        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);

            chbGiohang = itemView.findViewById(R.id.chb_giohang);
            tv_TenSP_giohang = itemView.findViewById(R.id.tv_TenSP_giohang);
            tv_GiaSP_giohang = itemView.findViewById(R.id.tv_GiaSP_giohang);
            tv_GiamSL_giohang = itemView.findViewById(R.id.tv_GiamSL_giohang);
            tv_TangSL_giohang = itemView.findViewById(R.id.tv_TenSP_giohang);
            tv_SoLuong_giohang = itemView.findViewById(R.id.tv_SoLuong_giohang);
            img_sanpham_giohang = itemView.findViewById(R.id.img_sanpham_giohang);
        }
    }
    private String formatPrice(Double price){
        NumberFormat numberFormat = NumberFormat.getInstance();
        String fPrice = numberFormat.format(price);
        return fPrice;
    }
}
