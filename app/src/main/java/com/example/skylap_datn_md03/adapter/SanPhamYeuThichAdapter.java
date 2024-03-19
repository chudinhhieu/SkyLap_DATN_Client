package com.example.skylap_datn_md03.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.ui.activities.SanPhamActivity;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class SanPhamYeuThichAdapter extends RecyclerView.Adapter<SanPhamYeuThichAdapter.SanPhamViewHolder> {
    private List<SanPham> list;
    private Context context;

    public SanPhamYeuThichAdapter(Context context, List<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sanPham = list.get(position);
        if (sanPham == null) {
            return;
        }
        if (sanPham.getAnhSanPham() != null && !sanPham.getAnhSanPham().isEmpty()) {
            Picasso.get().load(sanPham.getAnhSanPham()).into(holder.img_anh);
        }
        holder.txt_display.setText(sanPham.getDisplay());
        holder.txt_cpu.setText(sanPham.getCpu());
        holder.txt_card.setText(sanPham.getGpu());
        holder.txt_name.setText(sanPham.getTenSanPham());
        holder.txtBaoHanh.setText(sanPham.getBaohanh());
        holder.txtRam.setText(sanPham.getRam());
        holder.txtRom.setText(sanPham.getRom());
        holder.txt_price.setText(formatPrice(sanPham.getGiaTien()) + "₫");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SanPhamActivity.class);
            intent.putExtra("idSanPham", sanPham.get_id());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    static class SanPhamViewHolder extends RecyclerView.ViewHolder {
        ImageView img_anh;
        TextView txt_display, txt_cpu, txt_card, txt_name, txt_price, txtBaoHanh, txtRam, txtRom;

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            img_anh = itemView.findViewById(R.id.item_product_img_avatar);
            txt_display = itemView.findViewById(R.id.item_product_txtDisplay);
            txt_cpu = itemView.findViewById(R.id.item_product_txtCPU);
            txt_card = itemView.findViewById(R.id.item_product_txtCard);
            txt_name = itemView.findViewById(R.id.item_product_txtName);
            txt_price = itemView.findViewById(R.id.item_product_txtPrice);
            txtBaoHanh = itemView.findViewById(R.id.item_product_txtBaoHanh);
            txtRam = itemView.findViewById(R.id.item_product_txtRam);
            txtRom = itemView.findViewById(R.id.item_product_txtRom);
        }
    }

    private String formatPrice(Double price) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(price);
    }
}
