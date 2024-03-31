package com.example.skylap_datn_md03.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.SanPham;
import com.example.skylap_datn_md03.retrofitController.DonHangRetrofit;
import com.example.skylap_datn_md03.retrofitController.RetrofitService;
import com.example.skylap_datn_md03.ui.activities.SanPhamActivity;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
            SanPham sanPham = list.get(position);
            if (sanPham == null) return;
            //set value
            Picasso.get().load(sanPham.getAnhSanPham()).into(holder.img_anh);
            holder.txt_display.setText(sanPham.getDisplay());
            holder.txt_cpu.setText(sanPham.getCpu());
            holder.txt_card.setText(sanPham.getGpu());
        holder.txt_name.setText(sanPham.getTenSanPham());
        holder.txtBaoHanh.setText(sanPham.getBaohanh());
        holder.txtRam.setText(sanPham.getRam());
        holder.txtRom.setText(sanPham.getRom());
            holder.txt_price.setText(formatPrice(sanPham.getGiaTien())+"₫");
        RetrofitService retrofitService = new RetrofitService();
        DonHangRetrofit donHangRetrofit = retrofitService.retrofit.create(DonHangRetrofit.class);
        donHangRetrofit.layDaBan(sanPham.get_id()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                holder.txtDaBan.setText("Đã bán "+response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        donHangRetrofit.laySaoTrungBinh(sanPham.get_id()).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                holder.txtSao.setText(""+response.body());
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.d("zzzzzzz", "onFailure: "+t);

            }
        });
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
        TextView txt_display, txt_cpu, txt_card, txt_name,txt_price, txtBaoHanh,txtRam,txtRom,txtDaBan,txtSao;

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
            txtDaBan = itemView.findViewById(R.id.item_product_txtDaBan);
            txtSao= itemView.findViewById(R.id.txt_sao);

        }
    }
    private String formatPrice(Double price){
        NumberFormat numberFormat = NumberFormat.getInstance();
        String fPrice = numberFormat.format(price);
        return fPrice;
    }
}
