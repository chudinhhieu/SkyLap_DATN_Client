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

import com.bumptech.glide.Glide;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.ui.activities.SanPhamActivity;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder>{
    private List<SanPham> list;
    private Context context;

    private  View view;
    public SanPhamAdapter(Context context) {
        this.context = context;
    }
    public void setList(List<SanPham> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_product,parent,false);
        view = LayoutInflater.from(context).inflate(R.layout.item_product,parent,false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
            SanPham sanPham = list.get(position);
            if (sanPham == null) return;
            //set value
            Picasso.get().load(sanPham.getAnhSanPham()).into(holder.img_anh);
            holder.txt_display.setText(sanPham.getDisplay());
            holder.txt_cpu.setText("CPU: "+sanPham.getCpu());
            holder.txt_card.setText("Card: "+sanPham.getGpu());
            holder.txt_name.setText("Display: "+sanPham.getTenSanPham());
            holder.txt_price.setText(formatPrice(sanPham.getGiaTien())+"₫");
            holder.txt_sold.setText("Đã bán ");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SanPhamActivity.class);
                intent.putExtra("idSanPham", sanPham.get_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public final class SanPhamViewHolder extends RecyclerView.ViewHolder{
        ImageView img_anh;
        TextView txt_display, txt_cpu, txt_card, txt_name,txt_price, txt_sold;

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            img_anh = itemView.findViewById(R.id.item_product_img_avatar);
            txt_display = itemView.findViewById(R.id.item_product_txtDisplay);
            txt_cpu = itemView.findViewById(R.id.item_product_txtCPU);
            txt_card = itemView.findViewById(R.id.item_product_txtCard);
            txt_name = itemView.findViewById(R.id.item_product_txtName);
            txt_price = itemView.findViewById(R.id.item_product_txtPrice);
            txt_sold = itemView.findViewById(R.id.item_product_txtSold);

        }
    }
    private String formatPrice(Double price){
        NumberFormat numberFormat = NumberFormat.getInstance();
        String fPrice = numberFormat.format(price);
        return fPrice;
    }
}
